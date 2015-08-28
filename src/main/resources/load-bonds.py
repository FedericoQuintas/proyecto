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

class Link:
    ticker =""
    name=""
    url = ""
    def __init__(self, aTicker,aName, anUrl):
         self.ticker = aTicker
         self.name = aName
         self.url = anUrl

csvsToDownload = []
csvsToDownload.append(Link("AF17","BONAD 2017","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AF17&csv=1"))
csvsToDownload.append(Link("AM18","BONAD 2018","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AM18&csv=1"))
csvsToDownload.append(Link("AO16","BONAD 2016","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AO16&csv=1"))
csvsToDownload.append(Link("BD2C9","Ciudad de Bs. As. 2019 Clase 5","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BD2C9&csv=1"))
csvsToDownload.append(Link("BDC18","Ciudad de Bs. As. 2018 Clase 3","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BDC18&csv=1"))
csvsToDownload.append(Link("BDC19","Ciudad de Bs. As. 2019 Clase 4","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BDC19&csv=1"))
csvsToDownload.append(Link("BDC20","Ciudad de Bs. As. 2020 Clase 6","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BDC20&csv=1"))
csvsToDownload.append(Link("CO17","BonCor 2017","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CO17&csv=1"))
csvsToDownload.append(Link("ERG16","Entre Rios 2016 Serie 1","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=ERG16&csv=1"))
csvsToDownload.append(Link("FORM3","Formosa 2022","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=FORM3&csv=1"))
csvsToDownload.append(Link("NDG1","Neuquen 2016","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=NDG1&csv=1"))
csvsToDownload.append(Link("PMD18","Mendoza 2018 Clase 3","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PMD18&csv=1"))
csvsToDownload.append(Link("PMO18","Mendoza 2018 Clase 2","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PMO18&csv=1"))
csvsToDownload.append(Link("PMY16","Mendoza 2016","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PMY16&csv=1"))
csvsToDownload.append(Link("PUO19","Chubut 2019","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PUO19&csv=1"))
csvsToDownload.append(Link("CUAP","CUASIPAR en $","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CUAP&csv=1"))
csvsToDownload.append(Link("DICP","Discount $ Ley Arg.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=DICP&csv=1"))
csvsToDownload.append(Link("DIP0","Discount $ Ley Arg. Canje 2010","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=DIP0&csv=1"))
csvsToDownload.append(Link("NF18","BOGAR 2018","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=NF18&csv=1"))
csvsToDownload.append(Link("NO20","BOGAR 2020","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=NO20&csv=1"))
csvsToDownload.append(Link("PAP0","Par $ Ley Arg. Canje 2010","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PAP0&csv=1"))
csvsToDownload.append(Link("PARP","Par $ Ley Arg.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PARP&csv=1"))
csvsToDownload.append(Link("PR12","Consolidacion Serie 4","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PR12&csv=1"))
csvsToDownload.append(Link("PR13","Consolidacion Serie 6","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PR13&csv=1"))
csvsToDownload.append(Link("RNG21","Rio Negro Bogar 2","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=RNG21&csv=1"))
csvsToDownload.append(Link("TUCS1","Tucuman Consadep Serie 1","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=TUCS1&csv=1"))
csvsToDownload.append(Link("A2M6","BONAC MARZO 2016","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=A2M6&csv=1"))
csvsToDownload.append(Link("AS15","BONAR 2015","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AS15&csv=1"))
csvsToDownload.append(Link("AS16","BONAR 2016","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AS16&csv=1"))
csvsToDownload.append(Link("PR14","Consolidacion Serie 7","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PR14&csv=1"))
csvsToDownload.append(Link("PR15","Consolidacion Serie 8","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PR15&csv=1"))
csvsToDownload.append(Link("AA17","BONAR X","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AA17&csv=1"))
csvsToDownload.append(Link("AN18","Bonar 2018","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AN18&csv=1"))
csvsToDownload.append(Link("AY24","BONAR 2024","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AY24&csv=1"))
csvsToDownload.append(Link("BADER","BAADE","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BADER&csv=1"))
csvsToDownload.append(Link("BDED","Bs.As. Discount Largo Plazo U$S","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BDED&csv=1"))
csvsToDownload.append(Link("BP15","Buenos Aires 2015","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BP15&csv=1"))
csvsToDownload.append(Link("BP18","Buenos Aires 2018","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BP18&csv=1"))
csvsToDownload.append(Link("BP21","Buenos Aires 2021","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BP21&csv=1"))
csvsToDownload.append(Link("BP28","Buenos Aires 2028","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BP28&csv=1"))
csvsToDownload.append(Link("BPLD","Bs.As. Par Largo Plazo u$s","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BPLD&csv=1"))
csvsToDownload.append(Link("BPMD","Bs.As. Par Mediano Plazo u$s","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BPMD&csv=1"))
csvsToDownload.append(Link("DIA0","Discount U$S Ley Arg.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=DIA0&csv=1"))
csvsToDownload.append(Link("DICA","Discount U$S Ley Arg.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=DICA&csv=1"))
csvsToDownload.append(Link("DICY","Discount U$S Ley N.Y.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=DICY&csv=1"))
csvsToDownload.append(Link("DIY0","Discount U$S Ley NY Canje 2010","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=DIY0&csv=1"))
csvsToDownload.append(Link("GJ17","Global 2017","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=GJ17&csv=1"))
csvsToDownload.append(Link("PAA0","Par U$S Ley Arg. (Canje 2010)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PAA0&csv=1"))
csvsToDownload.append(Link("PARA","Par U$S Ley Arg.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PARA&csv=1"))
csvsToDownload.append(Link("PARY","Par U$S Ley N.Y.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PARY&csv=1"))
csvsToDownload.append(Link("PAY0","Par U$S Ley N.Y. (Canje 2010)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PAY0&csv=1"))
csvsToDownload.append(Link("RO15","BODEN 2015","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=RO15&csv=1"))
csvsToDownload.append(Link("TVPA","Cupones PBI U$S Ley Argentina","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=TVPA&csv=1"))
csvsToDownload.append(Link("TVPE","Cupones PBI en Euros","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=TVPE&csv=1"))
csvsToDownload.append(Link("TVPP","Cupones PBI en Pesos","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=TVPP&csv=1"))
csvsToDownload.append(Link("TVPY","Cupones PBI U$S Ley N.Y.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=TVPY&csv=1"))
csvsToDownload.append(Link("TVY0","Cupones PBI U$S 2010 Ley N.Y.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=TVY0&csv=1"))

for oneLink in csvsToDownload:
    print("Procesando:",oneLink.ticker)
    webpage = urllib.request.urlopen(oneLink.url)
    datareader = csv.reader(io.TextIOWrapper(webpage))
    for row in datareader:
        print(row)