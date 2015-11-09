from selenium import webdriver
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions
from bs4 import BeautifulSoup
import time
import requests
from datetime import *
import json

# API connection parameters
login_url = 'http://localhost:8080/login'
store_url = 'http://localhost:8080/assets/mutualFunds'
credentials = {
    "mail": "admin@invertar.com",
    "password": "admin"
}
headers = {'Content-Type': 'application/json'}

class InvertarMutualFund:
    def __init__(self):
        self.currency = "ARS"
        self.name = ""
        self.ticker = ""
        self.lastTradingPrice = 0.0
        self.tradingSessions = []
        self.description = ""

    def to_JSON(self):
        for ts in self.tradingSessions:
            ts.tradingDate = int(datetime.strptime(ts.tradingDate, "%Y-%m-%d").timestamp())
            self.lastTradingPrice = self.tradingSessions[len(self.tradingSessions) - 1].closingPrice
        return json.dumps(self, default=lambda o: o.__dict__,
            sort_keys=True, indent=4)

class TradingMutualFundSession:
    def __init__(self):
        self.tradingDate = ""
        self.closingPrice = 0.0
        self.openingPrice = 0.0
        self.volume = 0.0

#I've gotta iterate over these days and keep in mind that there are no working days as well.
base = date.today()
date_list = [base - timedelta(days=x) for x in range(0, 50)]

fund_types = []
fund_types.append(1)
fund_types.append(2)
fund_types.append(4)

mutualFunds = []
#Stocks Funds
mutualFunds.append("1810 Renta Variable Argentina")
mutualFunds.append("Alpha Acciones - Clase A")
mutualFunds.append("Alpha Acciones - Clase B")
mutualFunds.append("Alpha Acciones - Clase C")


#Bonds Funds
mutualFunds.append("1822 Raices Valores Fiduciarios")
mutualFunds.append("AL Ahorro Plus - Clase A")
mutualFunds.append("AL Ahorro Plus - Clase B")
mutualFunds.append("AL Ahorro Plus - Clase C")
mutualFunds.append("FBA Bonos Globales - Clase B")

#Hybrid Funds
mutualFunds.append("Pellegrini Agro FCI - Clase A")
mutualFunds.append("Pellegrini Agro FCI - Clase B")
mutualFunds.append("Premier Commodities Agrarios - Clase A")
mutualFunds.append("Premier Commodities Agrarios - Clase B")

driver = webdriver.Firefox()

date_list.reverse()

finalMutualFunds = []

for oneFundType in fund_types:

    fundType = ""
    if oneFundType==1:
        fundType = "Stock Fund"

    elif oneFundType==2:
        fundType = "Bond Fund"

    elif oneFundType==4:
        fundType = "Hybrid Fund"

    count = 0

    for oneDate in date_list:

        print("Procesando:",oneFundType,oneDate)

        driver.get("http://www.cafci.org.ar/scripts/cfn_Estadisticas.html")

        driver.switch_to.frame("frame_contents")

        driver.find_element_by_xpath("/html/body/div[1]/table/tbody/tr[1]/td[2]/select/option[@value=" + str(oneFundType) + "]").click()

        driver.find_element_by_xpath("/html/body/div[1]/table/tbody/tr[2]/td[2]/table/tbody/tr/td/input").send_keys(oneDate.strftime("%d/%m/%Y"))

        driver.find_element_by_xpath("/html/body/div[1]/table/tbody/tr[3]/td[2]/div[2]/a").click()

        driver.implicitly_wait(10)

        if driver.current_url != "http://www.cafci.org.ar/scripts/cfn_Estadisticas.html":

            driver.switch_to_default_content()

            wait = WebDriverWait(driver, 10)

            element = wait.until(expected_conditions.element_to_be_clickable((By.NAME,'tablaVCP')))

            html = driver.page_source

            soup = BeautifulSoup(html,"html.parser")

            table = soup.find("table",{"class":"valores"})

            rows = soup.find("table",{"name":"tablaVCP"}).find_all("tr")

            index=99999

            for row in rows:

                cells = row.find_all("td")

                for cell in cells:

                    name = cell.string.strip()

                    if index == 3:
                        newTradingMutualFundSession = TradingMutualFundSession()
                        newTradingMutualFundSession.tradingDate = datetime.strptime(cell.string.strip(),"%d/%m/%Y").date().strftime("%Y-%m-%d")
                    elif index == 4:
                        newTradingMutualFundSession.closingPrice = cell.string.strip().replace(".","").replace(",",".")
                        last_closing_price = newTradingMutualFundSession.closingPrice
                    elif index == 5:
                        newTradingMutualFundSession.volume = cell.string.strip().replace(".","")
                    elif index == 6:
                        count_2 = 0
                        found = 0

                        for aMutualFund in finalMutualFunds:
                            if aMutualFund.name == currentMutualFund.name:
                                found = 1
                                break
                            count_2 = count_2 + 1

                        if found ==1:
                            finalMutualFunds.pop(count_2)

                        newTradingMutualFundSession.closingPrice = last_closing_price

                        currentMutualFund.tradingSessions.append(newTradingMutualFundSession)

                        if len(currentMutualFund.tradingSessions)-1 != 0:
                            currentMutualFund.tradingSessions[len(currentMutualFund.tradingSessions)-1].openingPrice = currentMutualFund.tradingSessions[len(currentMutualFund.tradingSessions)-2].closingPrice

                        finalMutualFunds.append(currentMutualFund)
                    if name in mutualFunds:
                        if count==0:
                            newMutualFund = InvertarMutualFund()

                            newMutualFund.name = name

                            newMutualFund.tradingSessions = []

                            newMutualFund.fundType= fundType

                            newMutualFund.ticker = name.replace(' ', '') + '.' + fundType.replace(' ', '')
                            newMutualFund.description = name

                            finalMutualFunds.append(newMutualFund)
                            currentMutualFund = newMutualFund

                        else:
                            for aMutualFund in finalMutualFunds:
                                if aMutualFund.name == name:
                                    currentMutualFund = aMutualFund
                                    break
                        index = 1
                    index = index+1

            count = count + 1

# Open connection to API
conn = requests.request('POST', url=login_url, headers=headers, data=json.dumps(credentials))
session_cookie = conn.cookies

for oneFund in finalMutualFunds:
    jsonObject = oneFund.to_JSON()

    r =requests.request('POST', url=store_url, headers=headers, cookies=session_cookie, data=jsonObject)

    if r.status_code == 200:
        print("Cargado:", oneFund.ticker)
    else:
        print("Error en la carga del FCI {}: {}".format(oneFund.ticker, r.content))

driver.close()
