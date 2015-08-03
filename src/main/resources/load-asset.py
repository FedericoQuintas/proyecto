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
    openingPrice = 0.0
    maxPrice = 0.0
    minPrice = 0.0
    tradingDate = ""
    volume = 0

http = urllib3.PoolManager()

stocks =[]
stocks.append('AGRO.BA')
stocks.append('APBRA.BA')
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
stocks.append('STD.BA')
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
    for oneTradingSession in currentStock.get_historical('2014-01-01' , '2015-07-30'):
        currentTradingSession = InvertarTradingSession()
        currentTradingSession.closingPrice = oneTradingSession["Close"]
        currentTradingSession.openingPrice = oneTradingSession["Open"]
        currentTradingSession.maxPrice = oneTradingSession["High"]
        currentTradingSession.minPrice = oneTradingSession["Low"]
        currentTradingSession.volume = oneTradingSession["Volume"]
        currentTradingSession.tradingDate = oneTradingSession["Date"]
        myInvertarStock.tradingSessions.append(currentTradingSession)
    print(myInvertarStock.to_JSON())
    http.urlopen('POST', 'http://localhost:8080/assets', headers={'Content-Type':'application/json'},
                 body=myInvertarStock.to_JSON())

print("Fin de Carga de Acciones")
