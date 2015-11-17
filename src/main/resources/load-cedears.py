from datetime import *
import io
import csv
import json
from datetime import datetime
import time
import requests
import urllib

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
               "CEDEARC":"Citigroup (CEDEAR)"
               }

csvsToDownload = []
csvsToDownload.append(Link("CEDEARAA","Alcoa Inc. (CEDEAR)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CEDEARAA&csv=1"))
csvsToDownload.append(Link("CEDEARAAPL","Apple (CEDEAR)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CEDEARAAPL&csv=1"))
csvsToDownload.append(Link("CEDEARAIG","American Internat. Group Inc. (CEDEAR)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CEDEARAIG&csv=1"))
csvsToDownload.append(Link("CEDEARC","Citigroup (CEDEAR)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CEDEARC&csv=1"))

finalCedears = []
cedears = []

for oneLink in csvsToDownload:
    print("Descargando:",oneLink.ticker)
    webpage = urllib.request.urlopen(oneLink.url)
    datareader = csv.reader(io.TextIOWrapper(webpage))
    currentCedear = InvertarCedear(oneLink.ticker,oneLink.name)
    currentCedear.description = cedears_text[oneLink.ticker]
    currentCedear.tradingSessions=[]
    for row in datareader:
        if row[4] != "cierre":
            currentCedear.tradingSessions.append(InvertarTradingSession(row[4],row[1],row[2],row[3],row[0],row[5]))
    cedears.append(currentCedear)

for oneCedear in cedears:

    print("Procesando:",oneCedear.ticker)
    myInvertarCedear = InvertarCedear(oneCedear.ticker,oneCedear.name)
    currentCedear = oneCedear
    myInvertarCedear.description = oneCedear.description
    myInvertarCedear.currency = "ARS"
    myInvertarCedear.tradingSessions = []

    index = 0

    bonds=[]
    for oneTradingSession in oneCedear.tradingSessions:
        if str(oneTradingSession.tradingDate)!="2015-10-12":
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
                if len(myInvertarCedear.tradingSessions) >= period-1:

                    min = len(myInvertarCedear.tradingSessions) - (period-1)
                    max = len(myInvertarCedear.tradingSessions)

                    cumulativeCloses = 0.0

                    upward_movements = 0.0
                    downward_movements = 0.0

                    last_closing_price = 0.0

                    for index in range(min,max):
                        if float(myInvertarCedear.tradingSessions[index].closingPrice)>=float(myInvertarCedear.tradingSessions[index-1].closingPrice):
                            upward_movements += (float(myInvertarCedear.tradingSessions[index].closingPrice)-float(myInvertarCedear.tradingSessions[index-1].closingPrice))
                        else:
                            downward_movements += (float(myInvertarCedear.tradingSessions[index-1].closingPrice)-float(myInvertarCedear.tradingSessions[index].closingPrice))
                        last_closing_price = myInvertarCedear.tradingSessions[index].closingPrice
                        cumulativeCloses += float(myInvertarCedear.tradingSessions[index].closingPrice)

                    if float(currentTradingSession.closingPrice)>=float(last_closing_price):
                        upward_movements += (float(currentTradingSession.closingPrice)-float(last_closing_price))
                    else:
                        downward_movements += (float(last_closing_price)-float(currentTradingSession.closingPrice))

                    current_sma = (cumulativeCloses + float(currentTradingSession.closingPrice)) / period
                    current_momentum = round(float(currentTradingSession.closingPrice) - float(myInvertarCedear.tradingSessions[min].closingPrice),2)

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

            # Only inserts last 3 years but we take in consideration for the calculations the last 5 years
            if datetime.strptime(currentTradingSession.tradingDate,"%Y-%m-%d").date()>=date.today() - timedelta(days=1085):
                myInvertarCedear.tradingSessions.append(currentTradingSession)

    finalCedears.append(myInvertarCedear)

# Open connection to API
conn = requests.request('POST', url=login_url, headers=headers, data=json.dumps(credentials))
session_cookie = conn.cookies

for oneInvertarCedear in finalCedears:
    macd_macd_line = []
    last_ema_9 = 0.0
    index = 0
    for oneInvertarTradingSession in oneInvertarCedear.tradingSessions:
        oneInvertarTradingSession.tradingDate = int(time.mktime(datetime.strptime(oneInvertarTradingSession.tradingDate,"%Y-%m-%d").timetuple())) * 1000
        oneInvertarTradingSession.adjClosingPrice = 0.0
        index = index +1
        if index == len(oneInvertarCedear.tradingSessions)-1:
            oneInvertarCedear.lastTradingPrice = oneInvertarTradingSession.closingPrice
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

    #oneInvertarCedear.tradingSessions = map(lambda x: x.__dict__, oneInvertarCedear.tradingSessions)

    # La linea de arriba deberia andar pero no lo hace, por eso la horripilancia de abajo
    json_ts = []
    for ts in oneInvertarCedear.tradingSessions:
        json_ts.append(ts.__dict__)
    oneInvertarCedear.tradingSessions = json_ts

    r =requests.request('POST', url=store_url, headers=headers, cookies=session_cookie, data=json.dumps(oneInvertarCedear.__dict__))

    if r.status_code == 200:
        print("Cargado:",oneInvertarCedear.ticker)
    else:
        print("Error en la carga del cedear {}: {}".format(oneInvertarCedear.ticker, r.content))

print("Fin de Carga de Cedears")
