from yahoo_finance import Share
from datetime import *
import json
import urllib.request
import io
import csv

class InvertarBond:

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

csvsToDownload = []
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AF17&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AM18&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AO16&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BD2C9&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BDC18&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BDC19&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BDC20&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CO17&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=ERG16&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=FORM3&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=NDG1&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PMD18&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PMO18&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PMY16&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PUO19&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CUAP&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=DICP&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=DIP0&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=NF18&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=NO20&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PAP0&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PARP&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PR12&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PR13&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=RNG21&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=TUCS1&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=A2M6&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AS15&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AS16&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PR14&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PR15&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AA17&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AN18&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AY24&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BADER&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BDED&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BP15&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BP18&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BP21&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BP28&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BPLD&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BPMD&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=DIA0&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=DICA&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=DICY&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=DIY0&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=GJ17&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PAA0&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PARA&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PARY&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PAY0&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=RO15&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=TVPA&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=TVPE&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=TVPP&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=TVPY&csv=1")
csvsToDownload.append("http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=TVY0&csv=1")

for oneUrl in csvsToDownload:
    webpage = urllib.request.urlopen(oneUrl)
    datareader = csv.reader(io.TextIOWrapper(webpage))
    for row in datareader:
        print(row)