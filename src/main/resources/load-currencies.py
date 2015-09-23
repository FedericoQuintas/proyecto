import urllib.request
from bs4 import BeautifulSoup
from math import *
import json

class InvertarCurrency:
    ticker=""
    description=""
    sellValue=0.0
    buyValue=0.0
    tradingSessions = []
    def to_JSON(self):
        return json.dumps(self, default=lambda o: o.__dict__,
            sort_keys=True, indent=4)

class InvertarExcangeSession:
    exchangeDate=""
    buyPrice = 0.0
    sellPrice = 0.0

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
                newTradingSession.sellPrice =span.string[1:]
                newCurrency.tradingSessions.append(newTradingSession)
            else:
                newTradingSession = InvertarExcangeSession()
                newTradingSession.buyPrice =span.string[1:]

            index = index + 1
        print(newCurrency.to_JSON())


