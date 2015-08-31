from yahoo_finance import Share
from datetime import *
import json
import urllib3
from bs4 import BeautifulSoup
import urllib.request

class InvertarStock:

    ticker = ""
    name = ""
    description = ""
    industry = ""
    currency = "ARS"
    tradingSessions = []
    leader = False
    def to_JSON(self):
        return json.dumps(self, default=lambda o: o.__dict__,
            sort_keys=True, indent=4)

class InvertarTradingSession:

    closingPrice = 0.0
    adjClosingPrice = 0.0
    openingPrice = 0.0
    maxPrice = 0.0
    minPrice = 0.0
    tradingDate = ""
    volume = 0

    sma_7 = 0.0
    sma_21 = 0.0
    sma_50 = 0.0
    sma_200 = 0.0

    ema_7 = 0.0
    ema_21 = 0.0
    ema_50 = 0.0
    ema_200 = 0.0

    momentum_7 = 0.0
    momentum_21 = 0.0
    momentum_50 = 0.0
    momentum_200 = 0.0

    rsi_7 = 0.0
    rsi_21 = 0.0
    rsi_50 = 0.0
    rsi_200 = 0.0

    macd_macd_line = 0.0
    macd_signal_line = 0.0
    macd_histogram = 0.0

http = urllib3.PoolManager()

stocks =[]
stocks.append('AGRO.BA')
stocks.append('APBR.BA')
stocks.append('APSA.BA')
stocks.append('AUSO.BA')
stocks.append('BHIP.BA')
stocks.append('BOLT.BA')
stocks.append('BPAT.BA')
stocks.append('BRIO.BA')
stocks.append('CADO.BA')
stocks.append('CAPU.BA')
stocks.append('CAPX.BA')
stocks.append('CARC.BA')
stocks.append('CECO2.BA')
stocks.append('CELU.BA')
stocks.append('CEPU2.BA')
stocks.append('CGPA2.BA')
stocks.append('COLO.BA')
stocks.append('COUR.BA')
stocks.append('CRES.BA')
stocks.append('CTIO.BA')
stocks.append('DOME.BA')
stocks.append('DYCA.BA')
stocks.append('ESME.BA')
stocks.append('ESTR.BA')
stocks.append('FERR.BA')
stocks.append('FIPL.BA')
stocks.append('GARO.BA')
stocks.append('GBAN.BA')
stocks.append('GCLA.BA')
stocks.append('GRIM.BA')
stocks.append('INDU.BA')
stocks.append('INTR.BA')
stocks.append('INVJ.BA')
stocks.append('IRSA.BA')
stocks.append('JMIN.BA')
stocks.append('LEDE.BA')
stocks.append('LONG.BA')
stocks.append('METR.BA')
stocks.append('MIRG.BA')
stocks.append('MOLI.BA')
stocks.append('MOLI5.BA')
stocks.append('MORI.BA')
stocks.append('MORI5.BA')
stocks.append('OEST.BA')
stocks.append('PATA.BA')
stocks.append('PATY.BA')
stocks.append('PESA.BA')
stocks.append('PETR.BA')
stocks.append('POLL.BA')
stocks.append('PSUR.BA')
stocks.append('REP.BA')
stocks.append('RIGO.BA')
stocks.append('ROSE.BA')
stocks.append('SAMI.BA')
stocks.append('SEMI.BA')
stocks.append('TECO2.BA')
stocks.append('TEF.BA')
stocks.append('TGLT.BA')
stocks.append('TGNO4.BA')
stocks.append('TGSU2.BA')
stocks.append('TRAN.BA')
stocks.append('ALUA.BA')
stocks.append('APBR.BA')
stocks.append('BMA.BA')
stocks.append('COME.BA')
stocks.append('EDN.BA')
stocks.append('ERAR.BA')
stocks.append('FRAN.BA')
stocks.append('GGAL.BA')
stocks.append('PAMP.BA')
stocks.append('TS.BA')
stocks.append('YPFD.BA')

finalStocks = []

print("Inicio de Carga de Acciones")

for oneStock in stocks:

    print("Procesando: ",oneStock)
    myInvertarStock = InvertarStock()
    currentStock = Share(oneStock)
    myInvertarStock.ticker = oneStock
    myInvertarStock.description = "Description"
    myInvertarStock.industry = "Industry"
    myInvertarStock.currency = "ARS"
    myInvertarStock.tradingSessions = []
    stocksFromYahoo = currentStock.get_historical((date.today() - timedelta(days=1825)).strftime("%Y-%m-%d"),
                                                  (date.today()).strftime("%Y-%m-%d")) #Last 5 years
    stocksFromYahoo.reverse()

    #Workaround to filter holidays
    index = 0
    for oneTradingSession in stocksFromYahoo:
        if((oneTradingSession["Date"])in ["2015-01-01",
                                          "2015-02-16",
                                          "2015-02-17",
                                          "2015-03-23",
                                          "2015-03-24",
                                          "2015-04-02",
                                          "2015-04-03",
                                          "2015-05-01",
                                          "2015-05-25",
                                          "2015-06-20",
                                          "2015-07-09",
                                          "2015-08-17",
                                          "2015-10-12",
                                          "2015-11-23",
                                          "2015-12-07",
                                          "2015-12-08",
                                          "2015-12-25",
                                          "2014-01-01",
                                          "2014-03-03",
                                          "2014-03-04",
                                          "2014-03-24",
                                          "2014-04-02",
                                          "2014-04-18",
                                          "2014-05-01",
                                          "2014-05-02",
                                          "2014-05-25",
                                          "2014-06-20",
                                          "2014-07-09",
                                          "2014-08-18",
                                          "2014-10-13",
                                          "2014-11-24",
                                          "2014-12-08",
                                          "2014-12-25",
                                          "2014-12-26",
                                          "2013-01-01",
                                          "2013-01-31",
                                          "2013-02-11",
                                          "2013-02-12",
                                          "2013-02-20",
                                          "2013-03-29",
                                          "2013-04-01",
                                          "2013-04-02",
                                          "2013-05-01",
                                          "2013-06-20",
                                          "2013-06-21",
                                          "2013-07-09",
                                          "2013-08-19",
                                          "2013-10-14",
                                          "2013-11-25",
                                          "2013-12-25",
                                          "2012-02-20",
                                          "2012-02-21",
                                          "2012-02-27",
                                          "2012-03-24",
                                          "2012-04-02",
                                          "2012-04-06",
                                          "2012-04-30",
                                          "2012-05-01",
                                          "2012-05-25",
                                          "2012-06-20",
                                          "2012-07-09",
                                          "2012-08-20",
                                          "2012-09-24",
                                          "2012-10-08",
                                          "2012-11-26",
                                          "2012-12-08",
                                          "2012-12-24",
                                          "2012-12-25",
                                          "2011-03-07",
                                          "2011-03-08",
                                          "2011-03-24",
                                          "2011-03-25",
                                          "2011-04-21",
                                          "2011-04-22",
                                          "2011-05-25",
                                          "2011-06-20",
                                          "2011-08-22",
                                          "2011-10-10",
                                          "2011-11-28",
                                          "2011-12-08",
                                          "2011-12-09",
                                          "2010-01-01",
                                          "2010-03-24",
                                          "2010-04-01",
                                          "2010-04-02",
                                          "2010-05-25",
                                          "2010-06-21",
                                          "2010-07-09",
                                          "2010-08-16",
                                          "2010-10-11",
                                          "2010-10-27",
                                          "2010-11-22",
                                          "2010-12-08"
                                          ]):
            stocksFromYahoo.__delitem__(index)
        index = index + 1

    stocks=[]
    for oneTradingSession in stocksFromYahoo:
        currentTradingSession = InvertarTradingSession()
        currentTradingSession.closingPrice = oneTradingSession["Close"]
        currentTradingSession.openingPrice = oneTradingSession["Open"]
        currentTradingSession.maxPrice = oneTradingSession["High"]
        currentTradingSession.minPrice = oneTradingSession["Low"]
        currentTradingSession.volume = oneTradingSession["Volume"]
        currentTradingSession.tradingDate = oneTradingSession["Date"]
        currentTradingSession.adjClosingPrice = oneTradingSession["Adj_Close"]

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
            if len(myInvertarStock.tradingSessions) >= period-1:

                min = len(myInvertarStock.tradingSessions) - (period-1)
                max = len(myInvertarStock.tradingSessions)

                cumulativeCloses = 0.0

                upward_movements = 0.0
                downward_movements = 0.0

                last_closing_price = 0.0

                for index in range(min,max):
                    if float(myInvertarStock.tradingSessions[index].closingPrice)>=float(myInvertarStock.tradingSessions[index-1].closingPrice):
                        upward_movements += (float(myInvertarStock.tradingSessions[index].closingPrice)-float(myInvertarStock.tradingSessions[index-1].closingPrice))
                    else:
                        downward_movements += (float(myInvertarStock.tradingSessions[index-1].closingPrice)-float(myInvertarStock.tradingSessions[index].closingPrice))
                    last_closing_price = myInvertarStock.tradingSessions[index].closingPrice
                    cumulativeCloses += float(myInvertarStock.tradingSessions[index].closingPrice)

                if float(currentTradingSession.closingPrice)>=float(last_closing_price):
                    upward_movements += (float(currentTradingSession.closingPrice)-float(last_closing_price))
                else:
                    downward_movements += (float(last_closing_price)-float(currentTradingSession.closingPrice))

                current_sma = (cumulativeCloses + float(currentTradingSession.closingPrice)) / period
                current_momentum = round(float(currentTradingSession.closingPrice) - float(myInvertarStock.tradingSessions[min].closingPrice),2)

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
            myInvertarStock.tradingSessions.append(currentTradingSession)

        #Now we have to calculate the singal line for MACD


    finalStocks.append(myInvertarStock)

for oneInvertarStock in finalStocks:
    response = urllib.request.urlopen("http://www.ravaonline.com/v2/precios/panel.php?m=LID")
    soup = BeautifulSoup(response,"html.parser")
    for row in soup.findAll("td"):
      if row.get_text()+".BA" == oneInvertarStock.ticker:
        oneInvertarStock.leader = True
    macd_macd_line = []
    last_ema_9 = 0.0
    for oneInvertarTradingSession in oneInvertarStock.tradingSessions:
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
    print(oneInvertarStock.to_JSON())
    #http.urlopen('POST', 'http://localhost:8080/assets', headers={'Content-Type':'application/json'},
    #             body=myInvertarStock.to_JSON())

print("Fin de Carga de Acciones")