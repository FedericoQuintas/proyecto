from yahoo_finance import Share
from datetime import *
import json
import urllib.request
import io
import csv

class InvertarCedear:

    ticker = ""
    name = ""
    description = ""
    currency = "ARS"
    tradingSessions = []
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

class Link:
    ticker =""
    name=""
    url = ""
    def __init__(self, aTicker,aName, anUrl):
         self.ticker = aTicker
         self.name = aName
         self.url = anUrl

csvsToDownload = []
csvsToDownload.append(Link("CEDEARAA","Alcoa Inc. (CEDEAR)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CEDEARAA&csv=1"))
csvsToDownload.append(Link("CEDEARAAPL","Apple (CEDEAR)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CEDEARAAPL&csv=1"))
csvsToDownload.append(Link("CEDEARAIG","American Internat. Group Inc. (CEDEAR)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CEDEARAIG&csv=1"))
csvsToDownload.append(Link("CEDEARC","Citigroup (CEDEAR)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CEDEARC&csv=1"))
csvsToDownload.append(Link("CEDEARGE","General Electric (CEDEAR)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CEDEARGE&csv=1"))
csvsToDownload.append(Link("CEDEARIBM","IBM Corp. (CEDEAR)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CEDEARIBM&csv=1"))
csvsToDownload.append(Link("CEDEARKO","Coca-Cola (CEDEAR)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CEDEARKO&csv=1"))
csvsToDownload.append(Link("CEDEARWMT","Wal-Mart Stores Inc. (CEDEAR)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CEDEARWMT&csv=1"))

for oneLink in csvsToDownload:
    print("Procesando:",oneLink.ticker)
    webpage = urllib.request.urlopen(oneLink.url)
    datareader = csv.reader(io.TextIOWrapper(webpage))
    for row in datareader:
        print(row)