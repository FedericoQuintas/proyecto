# -*- coding: utf-8 -*-
from yahoo_finance import Share
from datetime import *
from bs4 import BeautifulSoup
import urllib3
import urllib.request
from pymongo import MongoClient
import json
import time
import requests

client = MongoClient('localhost', 27017)

db = client.invertarDB

# API connection parameters
login_url = 'http://localhost:8080/login'
store_url = 'http://localhost:8080/assets/stocks'
credentials = {
    "mail": "admin@invertar.com",
    "password": "admin"
}
headers = {'Content-Type': 'application/json'}

class StockData:
    ticker=""
    name=""
    def __init__(self,aTicker,aName,aDescription):
        self.ticker = aTicker
        self.name = aName
        self.description = aDescription


class InvertarStock:

    ticker = ""
    name = ""
    description = ""
    industry = ""
    currency = "ARS"
    tradingSessions = []
    leader = False
    lastTradingPrice = 0
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


stocks =[]
stocks.append(StockData('AGRO.BA','Agrometal S.A.I.','Agrometal S.A.I. fabrica y vende maquinaria agricola de roturacion y siembra en Argentina.'))
stocks.append(StockData('APBR.BA','Petroleo Brasileiro S.A.','Petroleo Brasileiro S.A.  Petrobras opera como una compañia de energia integrada en Brasil e internacionalmente'))
stocks.append(StockData('APSA.BA','Alto Palermo S.A.','Operadores y titulares de participaciones mayoritarias en una cartera de diez centros comerciales en Argentina, cinco de los cuales estan ubicados en la Ciudad de Buenos Aires (Abasto Shopping, Paseo Alcorta, Alto Palermo Shopping, Patio Bullrich y Buenos Aires Design), uno en el Gran Buenos Aires (Alto Avellaneda) y otro en la Ciudad de Salta (Alto NOA).'))
stocks.append(StockData('AUSO.BA','Autopistas del Sol S.A.','Autopistas del Sol S.A. particupa en la construccion, mejora, expansion, remodelacion, preservacion, mantenimiento, operacion y administracion del Acceso Norte de la Ciudad de Buenos Aires y de la Av. General Paz.'))
stocks.append(StockData('BHIP.BA','Banco Hipotecario S.A.','Banco Hipotecario S.A. provee servicios bancarios a individuos, pequeñas y medianas empresas y grandes corporaciones en Argentina.'))
stocks.append(StockData('BOLT.BA','Boldt S.A.','Boldt S.A.provee servicios de artes graficas, imprenta comercial y hardware y software para juegos de azar.'))
stocks.append(StockData('BPAT.BA','Banco Patagonia S.A.','Banco Patagonia S.A. provee productos y servicios bancarios en Argentina. Esta basada en Buenos Aires, Argentina, y es una subsidiaria de Banco do Brasil S.A.'))
stocks.append(StockData('BRIO.BA','Banco Santander Río S.A.','Banco Santander S.A. provee servicios bancarios a individuos, pequeñas y medianas empresas y grandes corporaciones en Argentina.'))
stocks.append(StockData('CADO.BA','Carlos Casado S.A.','Carlos Casado S.A. desarrolla actividades de agricultura y ganaderia en Argentina. Opera en tres segmentos: Inmobiliario, Agricultural, y Financiero.'))
stocks.append(StockData('CAPU.BA','Caputo S.A.','Caputo Sociedad Anonima, Industrial, Comercial y Financiera opera como una compañia constructora en Argentina.'))
stocks.append(StockData('CAPX.BA','Capex S.A.','Capex tiene varias lineas de negocios, en America Latina y Portugal, especialmente en Argentina. La principal fuente de recursos es la generacion de electricidad mediante la integracion vertical de produccion de petroleo y gas, obtencion de subproductos del procesamiento del gas y quemado para la generacion electrica.'))
stocks.append(StockData('CARC.BA','Carboclor S.A.','Carboclor S.A. mantiene una participacion activa en entidades industriales del sector quimico y petroquimico, integrando sus representantes comisiones directivas y comisiones de trabajo, segun el caso.'))
stocks.append(StockData('CECO2.BA','Endesa Costanera S.A.','Endesa Costanera S.A. es la mayor Compañia de energía electrica vía procesos termicos de Argentina.'))
stocks.append(StockData('CELU.BA','Celulosa Argentina S.A.','Celulosa Argentina S.A. se dedica a la produccion y comercializacion de papeles para impresion y escritura en Argentina, Uruguay y Chile.'))
stocks.append(StockData('CGPA2.BA','Camuzzi Gas Pampeana S.A.','Camuzzi Gas Pampeana S.A. distribuye gas natural en Argentina.'))
stocks.append(StockData('COLO.BA','Colorin S.A.','Colorin Industria de Materiales Sinteticos S.A. manufactura y vende pinturas, barnices, y productos relacionados a individuos y profesionales en Argentina.'))
stocks.append(StockData('COUR.BA','Continental Urbana S.A.I.','Continental Urbana S.A.I. se dedica a la inversion, el desarrollo y arrendamiento de propiedades de bienes raices en la Argentina. La compañia opera a traves de tres segmentos: Propiedades, Gestion, y Ropa.'))
stocks.append(StockData('CRES.BA','Cresud S.A.','Cresud Sociedad Anonima Comercial, Inmobiliaria, Financiera y Agropecuaria produce productos agricolas basicos en Brasil y otros paises latinoamericanos.'))
stocks.append(StockData('CTIO.BA','Consultatio S.A.','El Grupo Consultatio tiene dos grandes areas de negocios: la administracion de fondos liquidos de terceros y el desarrollo inmobiliario, este ultimo siempre ha sido llevado adelante con fondos propios. A su vez dentro de cada area el Grupo ofrece diversos servicios y modelos de negocio.'))
stocks.append(StockData('DOME.BA','Domec S.A.','DOMEC Compañia de Artefactos Domesticos Sociedad Anonima Industrial Comercial y Financiera manufactura y vende hornos, cocinas a gas, termotanques, calefones y otros productos similares.'))
stocks.append(StockData('DYCA.BA','Dycasa S.A.','Los negocios de Dycasa se basan en la construccion de obras publicas y privadas y en la explotacion de concesiones de obras y servicios publicos, como obras portuarias, gasoductos, ferroviarias, subterraneas, redes cloacales y de agua potable, entre otras.'))
stocks.append(StockData('ESME.BA','Bodegas Esmeralda S.A.','Bodegas Esmeralda S.A. participa en la elaboracion de vinos y actividades comerciales, asi como la exportacion de sus productos. Tiene sede en Cordoba, Argentina.'))
stocks.append(StockData('ESTR.BA','Angel Estrada y Cia. S.A.','Angel Estrada y Compania S.A. produce y vende una variedad de productos escolares y de oficina tanto en Argentina como internacionalmente.'))
stocks.append(StockData('FERR.BA','Ferrum S.A.','Ferrum Sociedad Anonima de Ceramica y Metalurgia produce, comercializa y distribuye productos para sanitarios.'))
stocks.append(StockData('FIPL.BA','Fiplasto S.A.','Fiplasto S.A. es un fabricante de productos en madera de revestimiento, como paneles de decoracion interior, y de muebles industriales.'))
stocks.append(StockData('GARO.BA','Garovaglio y Zorraquin S.A.','Garovaglio y Zorraquin S.A. se dedica a consignaciones, operaciones comerciales, explotacion agropecuaria y participacion en otras empresas.'))
stocks.append(StockData('GBAN.BA','Gas Natural Ban S.A.','Gas Natural BAN, S.A. ofrece servicios de distribucion de gas natural en Argentina e internacionalmente.'))
stocks.append(StockData('GCLA.BA','Grupo Clarin S.A.','El Grupo Clarin es una empresa de medios de comunicacion. Participa principalmente en las areas de television por cable y acceso a Internet, publicaciones e impresion, television, radio y programacion, contenidos digitales y otras actividades relacionadas.'))
stocks.append(StockData('GRIM.BA','Grimoldi S.A.','Grimoldi S.A. se dedica a la fabricacion y comercializacion de calzado, carteras y artículos afines.'))
stocks.append(StockData('INDU.BA','Solvay Indupa S.A.','Solvay Indupa S.A.I.C. produce y vende PVC (Policloruro de Vinilo) y Soda Caustica.'))
stocks.append(StockData('INTR.BA','Compañia Introductora De Buenos Aires S.A.','Compañia Introductora de Bs. As. y Dos Anclas S.A. conforman un holding empresario que desde 1901 se dedica a la industrializacion y comercializacion de Sal de mesa, Condimentos, Especias, Vinagres y Aceite de Oliva para ser consumidos en los hogares.'))
stocks.append(StockData('INVJ.BA','Inversora Juramento S.A.','Inversora Juramento S.A. opera como una compañia agroganadera en el noroeste Argentino e internacionalmente.'))
stocks.append(StockData('IRSA.BA','IRSA Inversiones y Representaciones S.A.','IRSA Inversiones y Representaciones S.A., adquiere, desarrolla y opera con bienes raices.'))
stocks.append(StockData('JMIN.BA','Holcim (Argentina) S.A.','Holcim (Argentina) S.A. es una productora y comercializadora de cemento de Argentina, especializada en cementos portland, clinker y hormigon elaborado.'))
stocks.append(StockData('LEDE.BA','Ledesma S.A.','Ledesma Sociedad Anonima Agricola Industrial produce y vende alimentos naturales renovables en Argentina, mas notablemente azucar.'))
stocks.append(StockData('LONG.BA','Longvie S.A.','Longvie S.A. se dedica a la fabricacion y venta de cocinas y hornos a gas.'))
stocks.append(StockData('METR.BA','Metrogas S.A.','MetroGAS S.A. es una compañia dedicada a la distrubucion de gas natural en Argentina.'))
stocks.append(StockData('MIRG.BA','Mirgor S.A.','Mirgor S.A.C.I.F.I.A. provee sistemas de climatizacion para la industria automotriz en Argentina.'))
stocks.append(StockData('MOLI.BA','Molinos Rio de la Plata S.A.','Molinos Rio de la Plata S.A. opera en la industria agricola y alimenticia Argentina.'))
stocks.append(StockData('MOLI5.BA','Molinos Rio de la Plata S.A.','Molinos Rio de la Plata S.A. opera en la industria agricola y alimenticia Argentina.'))
stocks.append(StockData('OEST.BA','Grupo Concesionario del Oeste S.A.','Grupo Concesionario del Oeste S.A. se dedica a la construccion, remodelacion, reparacion, conservacion, administracion y explotacion del Acceso Oeste de la Ciudad de Buenos Aires.'))
stocks.append(StockData('PATA.BA','Importadora y Exportadora de la Patagonia S.A.','Sociedad Anonima Importadora y Exportadora de la Patagonia compra, importa, almacena, vende y exporta mercaderia en Argentina.'))
stocks.append(StockData('PATY.BA','Quickfood S.A.','Quickfood Sociedad Anonima elabora y comercializa productos de origen vacuno y porcino.'))
stocks.append(StockData('PESA.BA','Petrobras Argentina S.A.','Petrobras Argentina S.A. opera como una compañia de energia integrada.'))
stocks.append(StockData('PETR.BA','Petrolera Pampa S.A.','Petrolera Pampa S.A. se dedica al estudio, exploracion y explotacion yacimientos hidrocarburos solidos, liquidos y/o gaseosos y sus respectivos derivados y el almacenaje, comercializacion de dichos productos y sus derivados.'))
stocks.append(StockData('POLL.BA','Polledo S.A.','Polledo S.A.I.C. y F.  es una empresa constructora que desarrolla obras de infraestrutura y servicios, especialmente para el sector publico.'))
stocks.append(StockData('PSUR.BA','Petrolera del Conosur S.A.','Petrolera del Conosur S.A. es una petrolera dedicada a la explotacion industrial y comercial de petroleo y sus derivados.'))
stocks.append(StockData('REP.BA','Repsol S.A.','Repsol S.A. es una empresa internacional integrada de petroleo y gas, con actividades en mas de 30 países y lider en España y Argentina.'))
stocks.append(StockData('RIGO.BA','Rigolleau S.A.','Rigolleau S.A. es una empresa fabricante y comercializadora de vidrios para uso domestico, farmaceutico y cosmetico.'))
stocks.append(StockData('ROSE.BA','Instituto Rosenbusch S.A.','Instituto Rosenbusch S.A. fabrica y vende productos veterinarios en Argentina.'))
stocks.append(StockData('SAMI.BA','S.A. San Miguel','S.A. San Miguel A.G.I.C.I. y F. es una empresa fruticola, productora, industrializadora y exportadora de limones.'))
stocks.append(StockData('SEMI.BA','Molinos Juan Semino S.A.','Molinos Juan Semino, S.A. es una empresa producta y vendedora de derivados de trigo.'))
stocks.append(StockData('TECO2.BA','Telecom Argentina S.A.','Telecom Argentina es una empresa privada que posee una licencia para la prestacion de los servicios de telefonia fija local, larga distancia nacional e internacional en todo el territorio nacional, licencia que tambien comprende la provision de enlaces punto a punto y el arrendamiento de enlaces de otros prestadores.'))
stocks.append(StockData('TEF.BA','Telefonica S.A.','Telefonica, S.A. provee servicios de comunicacion fijos y moviles principalmente en Europa y America Latina.'))
stocks.append(StockData('TGLT.BA','TGLT S.A.','TGLT S.A. esta involucrada en el desarrollo inmobiliario en Argentina y Uruguay.'))
stocks.append(StockData('TGNO4.BA','Transportadora de Gas del Norte S.A.','Transportadora de Gas del Norte S.A.transporta gas natural a traves de gasoductos de alta presion en el centro y norte de Argentina.'))
stocks.append(StockData('TGSU2.BA','Transportadora de Gas del Sur S.A.','Transportadora de Gas del Sur S.A. proporciona servicios de transporte de gas natural en Argentina.'))
stocks.append(StockData('TRAN.BA','Transener Compañia de Transporte de Energia Electrica de Alta Tension','Compañia de Transporte de Energia Electrica en Alta Tension Transener S.A. provee servicios de transmision de energia electrica en Argentina.'))
stocks.append(StockData('ALUA.BA','Aluminio Argentino S.A.','Aluminio Argentino SAIC es la unica productora de aluminio primario de la Republica Argentina. Es una empresa de origen y capitales argentinos que fue fundada en 1970. Su principal fuente de ingresos deriva de las operaciones de exportacion. Las acciones de Aluar cotizan solamente en la Bolsa de Comercio de Buenos Aires. Cabe destacar que este activo cuenta con ADR.'))
stocks.append(StockData('BMA.BA','Banco Macro S.A.','Banco Macro S.A. provee servicios bancarios a individuos, pequeñas y medianas empresas y grandes corporaciones en Argentina.'))
stocks.append(StockData('COME.BA','Sociedad Comercial del Plata S.A.','Sociedad Comercial del Plata S.A.invierte en empresas vinculadas a los sectores de la construccion, entretenimientos, desarrollos urbanos, fertilizantes y agroquimicos.'))
stocks.append(StockData('EDN.BA','Empresa Distribuidora y Comercializadora Norte S.A.','Empresa Distribuidora y Comercializadora Norte S.A.  es una empresa distribuidora de electricidad de la Argentina, principalmente en el noroeste del Gran Buenos Aires y en la zona norte de la Ciudad de Buenos Aires.'))
stocks.append(StockData('ERAR.BA','Siderar S.A.','Ternium Siderar es una empresa siderurgica, dedicada principalmente a la fabricacion de distintos tipos de acero en Argentina.'))
stocks.append(StockData('FRAN.BA','Banco Frances S.A.','BBVA Banco Frances S.A. provee servicios bancarios a individuos, pequeñas y medianas empresas y grandes corporaciones en Argentina.'))
stocks.append(StockData('GGAL.BA','Grupo Financiero Galicia S.A.','Grupo Financiero Galicia S.A. provee servicios bancarios a individuos, pequeñas y medianas empresas y grandes corporaciones en Argentina.'))
stocks.append(StockData('PAMP.BA','Pampa Energia S.A.','Pampa Energia S.A., una empresa de electricidad integrada, participa en la generacion, transmision y distribucion de energia electrica en Argentina.'))
stocks.append(StockData('TS.BA','Tenaris S.A.','Tenaris S.A. fabrica y provee cañerias de acero y servicios relacionados para el sector energetico y otros usos industriales.'))
stocks.append(StockData('YPFD.BA','YPF S.A.','YPF Sociedad Anonima, una compañia energetica, se dedica a la exploracion y produccion de hidrocarburos, refino y marketing, y quimica, de la Argentina.'))

finalStocks = []

print("Inicio de Carga de Acciones")

for oneStock in stocks:

    print("Procesando: ",oneStock.ticker)
    myInvertarStock = InvertarStock()
    currentStock = Share(oneStock.ticker)
    myInvertarStock.name = oneStock.name
    myInvertarStock.ticker = oneStock.ticker
    myInvertarStock.description = oneStock.description
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

# Open connection to API
conn = requests.request('POST', url=login_url, headers=headers, data=json.dumps(credentials))
session_cookie = conn.cookies

for oneInvertarStock in finalStocks:
    oneInvertarStock.leader = False
    response = urllib.request.urlopen("http://www.ravaonline.com/v2/precios/panel.php?m=LID")
    soup = BeautifulSoup(response,"html.parser")
    for row in soup.findAll("td"):
      if row.get_text()+".BA" == oneInvertarStock.ticker:
        oneInvertarStock.leader = True
    macd_macd_line = []
    last_ema_9 = 0.0
    index_2 = 0
    for oneInvertarTradingSession in oneInvertarStock.tradingSessions:
        oneInvertarTradingSession.tradingDate = \
            int(time.mktime(datetime.strptime(oneInvertarTradingSession.tradingDate,"%Y-%m-%d").timetuple())) * 1000
        index_2 = index_2 + 1
        if index_2 == len(oneInvertarStock.tradingSessions):
            oneInvertarStock.lastTradingPrice = oneInvertarTradingSession.closingPrice
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
    #oneInvertarStock.tradingSessions = map(lambda x: x.__dict__, oneInvertarStock.tradingSessions)

    # La linea de arriba deberia andar pero no lo hace, por eso la horripilancia de abajo
    json_ts = []
    for ts in oneInvertarStock.tradingSessions:
        json_ts.append(ts.__dict__)
    oneInvertarStock.tradingSessions = json_ts

    r =requests.request('POST', url=store_url, headers=headers, cookies=session_cookie, data=json.dumps(oneInvertarStock.__dict__))

    if r.status_code == 200:
        print("Cargado:", oneInvertarStock.ticker)
    else:
        print("Error en la carga de la accion {}: {}".format(oneInvertarStock.ticker, r.content))
print("Fin de Carga de Acciones")