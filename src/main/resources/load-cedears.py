from yahoo_finance import Share
from datetime import *
import json
import urllib.request
import io
import requests
import csv

class InvertarCedear:

    def __init__(self, aTicker,aName):
        self.ticker = aTicker
        self.name = aName
        self.industry = "Cedear"
        self.currency = "ARS"
        self.lastTradingPrice = 0.0
        self.description = "descripcion"
        self.tradingSessions = []

    def to_JSON(self):
        return json.dumps(self, default=lambda o: o.__dict__,
            sort_keys=True, indent=4)

class InvertarTradingSession:

    closingPrice = 0.0
    openingPrice = 0.0
    maxPrice = 0.0
    minPrice = 0.0
    tradingDate = ""
    volume = 0

    def __init__(self, aClosingPrice,anOpeningPrice, aMaxPrice,aMinPrice,aTradingDate,aVolume):
        self.closingPrice = float(aClosingPrice)
        self.openingPrice = float(anOpeningPrice)
        self.maxPrice = float(aMaxPrice)
        self.minPrice = float(aMinPrice)
        self.tradingDate = aTradingDate
        self.volume = int(aVolume)
        self.adjClosingPrice = 0.0
        self.sma_7 = 0.0
        self.sma_21 = 0.0
        self.sma_50 = 0.0
        self.sma_200 = 0.0
        self.ema_7 = 0.0
        self.ema_21 = 0.0
        self.ema_50 = 0.0
        self.ema_200 = 0.0
        self.momentum_7 = 0.0
        self.momentum_21 = 0.0
        self.momentum_50 = 0.0
        self.momentum_200 = 0.0
        self.rsi_7 = 0.0
        self.rsi_21 = 0.0
        self.rsi_50 = 0.0
        self.rsi_200 = 0.0
        self.macd_macd_line = 0.0
        self.macd_signal_line = 0.0
        self.macd_histogram = 0.0

class Link:
    ticker =""
    name=""
    url = ""
    def __init__(self, aTicker,aName, anUrl):
         self.ticker = aTicker
         self.name = aName
         self.url = anUrl

# API connection parameters
login_url = 'http://localhost:8080/login'
store_url = 'http://localhost:8080/assets/stocks'
credentials = {
    "mail": "admin@invertar.com",
    "password": "admin"
}
headers = {'Content-Type': 'application/json'}

cedears_text = {"CEDEARAA": "Alcoa Inc. (CEDEAR)",
               "CEDEARAAPL": "Apple (CEDEAR)",
               "CEDEARAIG": "American Internat. Group Inc. (CEDEAR)",
               "CEDEARC":"Citigroup (CEDEAR)",
               "CEDEARGE":"General Electric (CEDEAR)",
               "CEDEARIBM": "IBM Corp. (CEDEAR)",
               "CEDEARKO":"Coca-Cola (CEDEAR)",
               "CEDEARWMT":"Wal-Mart Stores Inc. (CEDEAR)"
               }

csvsToDownload = []
csvsToDownload.append(Link("CEDEARAA","Alcoa Inc. (CEDEAR)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CEDEARAA&csv=1"))
csvsToDownload.append(Link("CEDEARAAPL","Apple (CEDEAR)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CEDEARAAPL&csv=1"))
csvsToDownload.append(Link("CEDEARAIG","American Internat. Group Inc. (CEDEAR)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CEDEARAIG&csv=1"))
csvsToDownload.append(Link("CEDEARC","Citigroup (CEDEAR)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CEDEARC&csv=1"))
csvsToDownload.append(Link("CEDEARGE","General Electric (CEDEAR)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CEDEARGE&csv=1"))
csvsToDownload.append(Link("CEDEARIBM","IBM Corp. (CEDEAR)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CEDEARIBM&csv=1"))
csvsToDownload.append(Link("CEDEARKO","Coca-Cola (CEDEAR)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CEDEARKO&csv=1"))
csvsToDownload.append(Link("CEDEARWMT","Wal-Mart Stores Inc. (CEDEAR)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CEDEARWMT&csv=1"))

finalCedears = []

for oneLink in csvsToDownload:
    print("Procesando:",oneLink.ticker)
    webpage = urllib.request.urlopen(oneLink.url)
    datareader = csv.reader(io.TextIOWrapper(webpage))
    currentCedear = InvertarCedear(oneLink.ticker,oneLink.name)
    currentCedear.description = cedears_text[oneLink.ticker]
    currentCedear.tradingSessions=[]
    for row in datareader:
        if row[4] != "cierre":
            currentCedear.tradingSessions.append(InvertarTradingSession(row[4],row[1],row[2],row[3],row[0],row[5]))
    finalCedears.append(currentCedear)

# Open connection to API
conn = requests.request('POST', url=login_url, headers=headers, data=json.dumps(credentials))
session_cookie = conn.cookies

for aCedear in finalCedears:
    json_ts = []
    aCedear.lastTradingPrice = aCedear.tradingSessions[len(aCedear.tradingSessions) -1].closingPrice
    for ts in aCedear.tradingSessions:
        if str(ts.tradingDate) != "2015-10-12":
            ts.tradingDate = int(datetime.strptime(ts.tradingDate, "%Y-%m-%d").timestamp()) * 1000
            json_ts.append(ts.__dict__)
    aCedear.tradingSessions = json_ts

    r =requests.request('POST', url=store_url, headers=headers, cookies=session_cookie, data=json.dumps(aCedear.__dict__))

    if r.status_code == 200:
        print("Cargado:", aCedear.ticker)
    else:
        print("Error en la carga del CEDEAR {}: {}".format(aCedear.ticker, r.content))


