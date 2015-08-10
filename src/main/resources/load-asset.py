from yahoo_finance import Share
import json
import urllib3

class InvertarStock:

    ticker = ""
    name = ""
    description = ""
    industry = ""
    currency = "ARS"
    tradingSessions = []
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

    momentum_7 = 0.0
    momentum_21 = 0.0
    momentum_50 = 0.0
    momentum_200 = 0.0

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

print("Inicio de Carga de Acciones")

for oneStock in stocks:
    myInvertarStock = InvertarStock()
    currentStock = Share(oneStock)
    myInvertarStock.ticker = oneStock
    myInvertarStock.description = "Description"
    myInvertarStock.industry = "Industry"
    myInvertarStock.currency = "ARS"
    myInvertarStock.tradingSessions = []
    stocksFromYahoo = currentStock.get_historical('2014-01-01', '2015-07-30')
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
                                          "2013-03-24",
                                          "2013-03-29",
                                          "2013-04-01",
                                          "2013-04-02",
                                          "2013-05-01",
                                          "2013-05-25",
                                          "2013-06-20",
                                          "2013-06-21",
                                          "2013-07-09",
                                          "2013-08-19",
                                          "2013-10-14",
                                          "2013-11-25",
                                          "2013-12-08",
                                          "2013-12-25",
                                          "2012-01-01",
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
                                          "2012-12-25"]):
            stocksFromYahoo.__delitem__(index)
        index = index + 1

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

        currentTradingSession.momentum_7= 0.0
        currentTradingSession.momentum_21= 0.0
        currentTradingSession.momentum_50= 0.0
        currentTradingSession.momentum_200= 0.0

        if len(myInvertarStock.tradingSessions) >= 7:
            min = len(myInvertarStock.tradingSessions) - 6
            max = len(myInvertarStock.tradingSessions)
            cumulativeCloses = 0.0
            for index in range(min,max):
                cumulativeCloses = cumulativeCloses + float(myInvertarStock.tradingSessions[index].adjClosingPrice)
            current_sma_7 = (cumulativeCloses + float(currentTradingSession.adjClosingPrice)) / 7
            currentTradingSession.sma_7 = round(current_sma_7,2)
            currentTradingSession.momentum_7 = round(float(currentTradingSession.closingPrice) - float(myInvertarStock.tradingSessions[min-1].closingPrice),2)

        if len(myInvertarStock.tradingSessions) >= 21:
            min = len(myInvertarStock.tradingSessions) - 20
            max = len(myInvertarStock.tradingSessions)
            cumulativeCloses = 0.0
            for index in range(min,max):
                cumulativeCloses = cumulativeCloses + float(myInvertarStock.tradingSessions[index].adjClosingPrice)
            current_sma_21 = (cumulativeCloses + float(currentTradingSession.adjClosingPrice)) / 21
            currentTradingSession.sma_21 = round(current_sma_21,2)
            currentTradingSession.momentum_21 = round(float(currentTradingSession.closingPrice) - float(myInvertarStock.tradingSessions[min-1].closingPrice),2)

        if len(myInvertarStock.tradingSessions) >= 50:
            min = len(myInvertarStock.tradingSessions) - 49
            max = len(myInvertarStock.tradingSessions)
            cumulativeCloses = 0.0
            for index in range(min,max):
                cumulativeCloses = cumulativeCloses + float(myInvertarStock.tradingSessions[index].adjClosingPrice)
            current_sma_50 = (cumulativeCloses + float(currentTradingSession.adjClosingPrice)) / 50
            currentTradingSession.sma_50 = round(current_sma_50,2)
            currentTradingSession.momentum_50 = round(float(currentTradingSession.closingPrice) - float(myInvertarStock.tradingSessions[min-1].closingPrice),2)

        if len(myInvertarStock.tradingSessions) >= 200:
            min = len(myInvertarStock.tradingSessions) - 199
            max = len(myInvertarStock.tradingSessions)
            cumulativeCloses = 0.0
            for index in range(min,max):
                cumulativeCloses = cumulativeCloses + float(myInvertarStock.tradingSessions[index].adjClosingPrice)
            current_sma_200 = (cumulativeCloses + float(currentTradingSession.adjClosingPrice)) / 200
            currentTradingSession.sma_200 = round(current_sma_200,2)
            currentTradingSession.momentum_200 = round(float(currentTradingSession.closingPrice) - float(myInvertarStock.tradingSessions[min-1].closingPrice),2)

        myInvertarStock.tradingSessions.append(currentTradingSession)
    print(myInvertarStock.to_JSON())
    http.urlopen('POST', 'http://localhost:8080/assets', headers={'Content-Type':'application/json'},
                 body=myInvertarStock.to_JSON())

print("Fin de Carga de Acciones")