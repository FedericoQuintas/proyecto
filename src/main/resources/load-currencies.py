import urllib.request
from bs4 import BeautifulSoup
import json
from datetime import datetime
import requests

# API connection parameters
login_url = 'http://localhost:8080/login'
store_url = 'http://localhost:8080/assets/currencies'
credentials = {
    "mail": "admin@invertar.com",
    "password": "admin"
}
headers = {'Content-Type': 'application/json'}

class InvertarCurrency:
    def __init__(self):
        self.ticker = ""
        self.description = ""
        self.sellValue = 0.0
        self.buyValue = 0.0
        self.tradingSessions = []
        self.lastTradingPrice = 0.0
        self.name = ""
    def to_JSON(self):
        for ts in self.tradingSessions:
            ts.closingPrice = (ts.buyPrice + ts.sellPrice) / 2
            ts.tradingDate = int(datetime.strptime(ts.exchangeDate, "%d/%m/%Y").timestamp()) * 1000

        openingPrice = self.tradingSessions[0].closingPrice
        for ts in self.tradingSessions:
            ts.openingPrice = openingPrice
            openingPrice = ts.closingPrice

        for ts in self.tradingSessions:
            del ts.buyPrice
            del ts.sellPrice
            del ts.exchangeDate

        del self.buyValue
        del self.sellValue
        self.lastTradingPrice = self.tradingSessions[0].closingPrice
        self.name = self.ticker + ' ' + self.description
        return json.dumps(self, default=lambda o: o.__dict__,
            sort_keys=True, indent=4)

class InvertarExchangeSession:
    def __init__(self):
        self.openingPrice = 0.0
        self.closingPrice = 0.0
        self.exchangeDate = ""
        self.buyPrice = 0.0
        self.sellPrice = 0.0

class Link:
    url=""
    ticker=""
    description=""
    def __init__(self,anUrl,aTicker,aDescription):
        self.url = anUrl
        self.ticker = aTicker
        self.description = aDescription

links = []
links.append(Link("http://www.dolarargentino.com.ar/historico-completo-dolar-blue.php","USD","Blue"))
links.append(Link("http://www.dolarargentino.com.ar/historico-completo-dolar-oficial.php","USD","Oficial"))
links.append(Link("http://www.dolarargentino.com.ar/historico-completo-euro-blue.php","EUR","Blue"))
links.append(Link("http://www.dolarargentino.com.ar/historico-completo-euro-oficial.php","EUR","Oficial"))

# Open connection to API
conn = requests.request('POST', url=login_url, headers=headers, data=json.dumps(credentials))
session_cookie = conn.cookies

for oneLink in links:
    print("Procesando",oneLink.ticker,oneLink.description)

    newCurrency = InvertarCurrency()

    newCurrency.ticker = oneLink.ticker
    newCurrency.description = oneLink.description
    newCurrency.tradingSessions = []

    response = urllib.request.urlopen(oneLink.url)
    soup = BeautifulSoup(response,"html.parser")
    for row in soup.findAll("span",style="width: 480px; font-size: 13px; font-family: arial; margin-bottom: -10px; display: block;"):
        index = 0
        for span in row.findAll("span"):
            if index % 2 == 1:
                newTradingSession.exchangeDate = str(row)[111:121]
                newTradingSession.sellPrice = float(span.string[1:])
                newCurrency.tradingSessions.append(newTradingSession)
            else:
                newTradingSession = InvertarExchangeSession()
                newTradingSession.buyPrice = float(span.string[1:])

            index = index + 1

    jsonObject = newCurrency.to_JSON()
    print(jsonObject)

    r =requests.request('POST', url=store_url, headers=headers, cookies=session_cookie, data=jsonObject)

    if r.status_code == 200:
        print("Cargado:", newCurrency.ticker)
    else:
        print("Error en la carga del FCI {}: {}".format(newCurrency.ticker, r.content))


