from yahoo_finance import Share
from datetime import *
import json
import urllib.request
import io
import csv
import json
from pymongo import MongoClient

client = MongoClient('localhost', 27017)

db = client.invertarDB

class InvertarBond:

    ticker = ""
    name = ""
    description = ""
    currency = "ARS"
    tradingSessions = []
    dollar_linked = False
    def to_JSON(self):
        return json.dumps(self, default=lambda o: o.__dict__,
            sort_keys=True, indent=4)
    def __init__(self, aTicker,aName):
         self.ticker = aTicker
         self.name = aName

class InvertarTradingSession:

    closingPrice = 0.0
    openingPrice = 0.0
    maxPrice = 0.0
    minPrice = 0.0
    tradingDate = ""
    volume = 0
    def __init__(self, aClosingPrice,anOpeningPrice, aMaxPrice,aMinPrice,aTradingDate,aVolume):
        self.closingPrice = aClosingPrice
        self.openingPrice = anOpeningPrice
        self.maxPrice = aMaxPrice
        self.minPrice = aMinPrice
        self.tradingDate = aTradingDate
        self.volume = aVolume

class Link:
    ticker =""
    name=""
    url = ""
    dollar_linked=False
    def __init__(self, aTicker,aType,aName, anUrl):
         self.ticker = aTicker
         self.name = aName
         self.dollar_linked=aType
         self.url = anUrl

csvsToDownload = []
csvsToDownload.append(Link("AF17",True,"BONAD 2017","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AF17&csv=1"))
csvsToDownload.append(Link("AM18",True,"BONAD 2018","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AM18&csv=1"))
csvsToDownload.append(Link("AO16",True,"BONAD 2016","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AO16&csv=1"))
csvsToDownload.append(Link("BD2C9",True,"Ciudad de Bs. As. 2019 Clase 5","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BD2C9&csv=1"))
csvsToDownload.append(Link("BDC18",True,"Ciudad de Bs. As. 2018 Clase 3","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BDC18&csv=1"))
csvsToDownload.append(Link("BDC19",True,"Ciudad de Bs. As. 2019 Clase 4","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BDC19&csv=1"))
csvsToDownload.append(Link("BDC20",True,"Ciudad de Bs. As. 2020 Clase 6","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BDC20&csv=1"))
csvsToDownload.append(Link("CO17",True,"BonCor 2017","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CO17&csv=1"))
csvsToDownload.append(Link("ERG16",True,"Entre Rios 2016 Serie 1","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=ERG16&csv=1"))
csvsToDownload.append(Link("FORM3",True,"Formosa 2022","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=FORM3&csv=1"))
csvsToDownload.append(Link("NDG1",True,"Neuquen 2016","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=NDG1&csv=1"))
csvsToDownload.append(Link("PMD18",True,"Mendoza 2018 Clase 3","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PMD18&csv=1"))
csvsToDownload.append(Link("PMO18",True,"Mendoza 2018 Clase 2","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PMO18&csv=1"))
csvsToDownload.append(Link("PMY16",True,"Mendoza 2016","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PMY16&csv=1"))
csvsToDownload.append(Link("PUO19",True,"Chubut 2019","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PUO19&csv=1"))
csvsToDownload.append(Link("CUAP",False,"CUASIPAR en $","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CUAP&csv=1"))
csvsToDownload.append(Link("DICP",False,"Discount $ Ley Arg.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=DICP&csv=1"))
csvsToDownload.append(Link("DIP0",False,"Discount $ Ley Arg. Canje 2010","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=DIP0&csv=1"))
csvsToDownload.append(Link("NF18",False,"BOGAR 2018","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=NF18&csv=1"))
csvsToDownload.append(Link("NO20",False,"BOGAR 2020","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=NO20&csv=1"))
csvsToDownload.append(Link("PAP0",False,"Par $ Ley Arg. Canje 2010","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PAP0&csv=1"))
csvsToDownload.append(Link("PARP",False,"Par $ Ley Arg.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PARP&csv=1"))
csvsToDownload.append(Link("PR12",False,"Consolidacion Serie 4","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PR12&csv=1"))
csvsToDownload.append(Link("PR13",False,"Consolidacion Serie 6","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PR13&csv=1"))
csvsToDownload.append(Link("RNG21",False,"Rio Negro Bogar 2","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=RNG21&csv=1"))
csvsToDownload.append(Link("TUCS1",False,"Tucuman Consadep Serie 1","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=TUCS1&csv=1"))
csvsToDownload.append(Link("A2M6",False,"BONAC MARZO 2016","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=A2M6&csv=1"))
csvsToDownload.append(Link("AS15",False,"BONAR 2015","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AS15&csv=1"))
csvsToDownload.append(Link("AS16",False,"BONAR 2016","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AS16&csv=1"))
csvsToDownload.append(Link("PR14",False,"Consolidacion Serie 7","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PR14&csv=1"))
csvsToDownload.append(Link("PR15",False,"Consolidacion Serie 8","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PR15&csv=1"))
csvsToDownload.append(Link("AA17",False,"BONAR X","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AA17&csv=1"))
csvsToDownload.append(Link("AN18",False,"Bonar 2018","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AN18&csv=1"))
csvsToDownload.append(Link("AY24",False,"BONAR 2024","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AY24&csv=1"))
csvsToDownload.append(Link("BADER",False,"BAADE","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BADER&csv=1"))
csvsToDownload.append(Link("BDED",False,"Bs.As. Discount Largo Plazo U$S","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BDED&csv=1"))
csvsToDownload.append(Link("BP15",False,"Buenos Aires 2015","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BP15&csv=1"))
csvsToDownload.append(Link("BP18",False,"Buenos Aires 2018","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BP18&csv=1"))
csvsToDownload.append(Link("BP21",False,"Buenos Aires 2021","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BP21&csv=1"))
csvsToDownload.append(Link("BP28",False,"Buenos Aires 2028","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BP28&csv=1"))
csvsToDownload.append(Link("BPLD",False,"Bs.As. Par Largo Plazo u$s","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BPLD&csv=1"))
csvsToDownload.append(Link("BPMD",False,"Bs.As. Par Mediano Plazo u$s","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BPMD&csv=1"))
csvsToDownload.append(Link("DIA0",False,"Discount U$S Ley Arg.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=DIA0&csv=1"))
csvsToDownload.append(Link("DICA",False,"Discount U$S Ley Arg.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=DICA&csv=1"))
csvsToDownload.append(Link("DICY",False,"Discount U$S Ley N.Y.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=DICY&csv=1"))
csvsToDownload.append(Link("DIY0",False,"Discount U$S Ley NY Canje 2010","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=DIY0&csv=1"))
csvsToDownload.append(Link("GJ17",False,"Global 2017","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=GJ17&csv=1"))
csvsToDownload.append(Link("PAA0",False,"Par U$S Ley Arg. (Canje 2010)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PAA0&csv=1"))
csvsToDownload.append(Link("PARA",False,"Par U$S Ley Arg.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PARA&csv=1"))
csvsToDownload.append(Link("PARY",False,"Par U$S Ley N.Y.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PARY&csv=1"))
csvsToDownload.append(Link("PAY0",False,"Par U$S Ley N.Y. (Canje 2010)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PAY0&csv=1"))
csvsToDownload.append(Link("RO15",False,"BODEN 2015","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=RO15&csv=1"))
csvsToDownload.append(Link("TVPA",False,"Cupones PBI U$S Ley Argentina","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=TVPA&csv=1"))
csvsToDownload.append(Link("TVPE",False,"Cupones PBI en Euros","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=TVPE&csv=1"))
csvsToDownload.append(Link("TVPP",False,"Cupones PBI en Pesos","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=TVPP&csv=1"))
csvsToDownload.append(Link("TVPY",False,"Cupones PBI U$S Ley N.Y.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=TVPY&csv=1"))
csvsToDownload.append(Link("TVY0",False,"Cupones PBI U$S 2010 Ley N.Y.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=TVY0&csv=1"))

bonds =[]
finalBonds = []

for oneLink in csvsToDownload:
    print("Descargando:",oneLink.ticker)
    webpage = urllib.request.urlopen(oneLink.url)
    datareader = csv.reader(io.TextIOWrapper(webpage))
    currentBond = InvertarBond(oneLink.ticker,oneLink.name)
    currentBond.dollar_linked=oneLink.dollar_linked
    currentBond.tradingSessions=[]
    for row in datareader:
        if row[4] != "cierre":
            currentBond.tradingSessions.append(InvertarTradingSession(row[4],row[1],row[2],row[3],row[0],row[5]))
    bonds.append(currentBond)

for oneBond in bonds:

    print("Procesando:",oneBond.ticker)
    myInvertarBond = InvertarBond(oneBond.ticker,oneBond.name)
    currentBond = oneBond
    myInvertarBond.dollar_linked = oneBond.dollar_linked
    myInvertarBond.currency = "ARS"
    myInvertarBond.tradingSessions = []

    index = 0

    bonds=[]
    for oneTradingSession in oneBond.tradingSessions:
        currentTradingSession = InvertarTradingSession(oneTradingSession.closingPrice, oneTradingSession.openingPrice,
                                                       oneTradingSession.maxPrice,oneTradingSession.minPrice,
                                                       oneTradingSession.tradingDate,oneTradingSession.volume)

        currentTradingSession.sma_7= 0.0
        currentTradingSession.sma_21= 0.0
        currentTradingSession.sma_50= 0.0
        currentTradingSession.sma_200= 0.0

        currentTradingSession.ema_7= 0.0
        currentTradingSession.ema_21= 0.0
        currentTradingSession.ema_50= 0.0
        currentTradingSession.ema_200= 0.0

        currentTradingSession.momentum_7= 0.0
        currentTradingSession.momentum_21= 0.0
        currentTradingSession.momentum_50= 0.0
        currentTradingSession.momentum_200= 0.0

        currentTradingSession.rsi_7 = 0.0
        currentTradingSession.rsi_21 = 0.0
        currentTradingSession.rsi_50 = 0.0
        currentTradingSession.rsi_200 = 0.0

        currentTradingSession.macd_macd_line = 0.0
        currentTradingSession.macd_signal_line = 0.0
        currentTradingSession.macd_histogram = 0.0

        periods = [7,12,21,26,50,200]

        for period in periods:
            if len(myInvertarBond.tradingSessions) >= period-1:

                min = len(myInvertarBond.tradingSessions) - (period-1)
                max = len(myInvertarBond.tradingSessions)

                cumulativeCloses = 0.0

                upward_movements = 0.0
                downward_movements = 0.0

                last_closing_price = 0.0

                for index in range(min,max):
                    if float(myInvertarBond.tradingSessions[index].closingPrice)>=float(myInvertarBond.tradingSessions[index-1].closingPrice):
                        upward_movements += (float(myInvertarBond.tradingSessions[index].closingPrice)-float(myInvertarBond.tradingSessions[index-1].closingPrice))
                    else:
                        downward_movements += (float(myInvertarBond.tradingSessions[index-1].closingPrice)-float(myInvertarBond.tradingSessions[index].closingPrice))
                    last_closing_price = myInvertarBond.tradingSessions[index].closingPrice
                    cumulativeCloses += float(myInvertarBond.tradingSessions[index].closingPrice)

                if float(currentTradingSession.closingPrice)>=float(last_closing_price):
                    upward_movements += (float(currentTradingSession.closingPrice)-float(last_closing_price))
                else:
                    downward_movements += (float(last_closing_price)-float(currentTradingSession.closingPrice))

                current_sma = (cumulativeCloses + float(currentTradingSession.closingPrice)) / period
                current_momentum = round(float(currentTradingSession.closingPrice) - float(myInvertarBond.tradingSessions[min].closingPrice),2)

                if downward_movements==0.0:
                    current_rsi=100.0
                else:
                    current_rsi = 100 - (100 / ((upward_movements/period)/(downward_movements/period)+1))

                current_ema = 0.0

                if period==7:
                    currentTradingSession.sma_7 = round(current_sma,2)
                    currentTradingSession.momentum_7 = round(current_momentum,2)
                    currentTradingSession.rsi_7 = round(current_rsi,2)
                    if min== 0:
                        current_ema = current_sma
                    else:
                        current_ema = last_ema_7 * (1-(float(2) / float(period+1))) + (
                                                    float(currentTradingSession.closingPrice)
                                                    * (float(2)/float(period+1)))
                    currentTradingSession.ema_7 = round(current_ema,2)
                    last_ema_7 = current_ema
                elif period==21:
                    currentTradingSession.sma_21 = round(current_sma,2)
                    currentTradingSession.momentum_21 = round(current_momentum,2)
                    currentTradingSession.rsi_21 = round(current_rsi,2)
                    if min== 0:
                        current_ema = current_sma
                    else:
                        current_ema = last_ema_21 * (1-(float(2) / float(period+1))) + (
                                                    float(currentTradingSession.closingPrice)
                                                    * (float(2)/float(period+1)))
                    currentTradingSession.ema_21 = round(current_ema,2)
                    last_ema_21 = current_ema
                elif period==50:
                    currentTradingSession.sma_50 = round(current_sma,2)
                    currentTradingSession.momentum_50 = round(current_momentum,2)
                    currentTradingSession.rsi_50 = round(current_rsi,2)
                    if min== 0:
                        current_ema = current_sma
                    else:
                        current_ema = last_ema_50 * (1-(float(2) / float(period+1))) + (
                                                    float(currentTradingSession.closingPrice)
                                                    * (float(2)/float(period+1)))
                    currentTradingSession.ema_50 = round(current_ema,2)
                    last_ema_50 = current_ema
                elif period==200:
                    currentTradingSession.sma_200 = round(current_sma,2)
                    currentTradingSession.momentum_200 = round(current_momentum,2)
                    currentTradingSession.rsi_200 = round(current_rsi,2)
                    if min== 0:
                        current_ema = current_sma
                    else:
                        current_ema = last_ema_200 * (1-(float(2) / float(period+1))) + (
                                                    float(currentTradingSession.closingPrice)
                                                    * (float(2)/float(period+1)))
                    currentTradingSession.ema_200 = round(current_ema,2)
                    last_ema_200 = current_ema
                elif period==12:
                    if min== 0:
                        current_ema = current_sma
                    else:
                        current_ema = last_ema_12 * (1-(float(2) / float(period+1))) + (
                                                    float(currentTradingSession.closingPrice)
                                                    * (float(2)/float(period+1)))
                    last_ema_12 = current_ema
                    currentTradingSession.macd_macd_line = current_ema
                elif period==26:
                    if min== 0:
                        current_ema = current_sma
                    else:
                        current_ema = last_ema_26 * (1-(float(2) / float(period+1))) + (
                                                    float(currentTradingSession.closingPrice)
                                                    * (float(2)/float(period+1)))
                    last_ema_26 = current_ema
                    currentTradingSession.macd_macd_line -= current_ema

        #Only inserts last 3 years but we take in consideration for the calculations the last 5 years
        if datetime.strptime(currentTradingSession.tradingDate,"%Y-%m-%d").date()>=date.today() - timedelta(days=1085):
            myInvertarBond.tradingSessions.append(currentTradingSession)

    finalBonds.append(myInvertarBond)

for oneInvertarBond in finalBonds:
    macd_macd_line = []
    last_ema_9 = 0.0
    for oneInvertarTradingSession in oneInvertarBond.tradingSessions:
        macd_macd_line.append(oneInvertarTradingSession.macd_macd_line)
        if len(macd_macd_line) >= 9:
            min = len(macd_macd_line) - 9
            max = len(macd_macd_line)
            sum_macd_macd_line=0.0
            for index in range(min,max):
                sum_macd_macd_line += macd_macd_line[index]
            current_sma = sum_macd_macd_line/float(9)
            current_ema = current_sma
            if min != 0:
                current_ema = last_ema_9 * (1-(float(2) / float(10))) + (
                                            float(oneInvertarTradingSession.macd_macd_line)
                                            * (float(2)/float(10)))
            last_ema_9 = current_ema
            oneInvertarTradingSession.macd_signal_line = current_ema

    db.bonds.insert_one(json.loads(oneInvertarBond.to_JSON()))
    print("Cargado:",oneInvertarBond.ticker)

print("Fin de Carga de Bonos")