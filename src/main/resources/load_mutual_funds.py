from selenium import webdriver
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions
from bs4 import BeautifulSoup
import time
import requests
from datetime import datetime, timedelta, date
import json

# API connection parameters
login_url = 'http://localhost:8080/login'
store_url = 'http://localhost:8080/assets/mutualFunds'
credentials = {
    "mail": "admin@invertar.com",
    "password": "admin"
}
headers = {'Content-Type': 'application/json'}

class AnyEc:
    """ Use with WebDriverWait to combine expected_conditions
        in an OR.
    """
    def __init__(self, *args):
        self.ecs = args
    def __call__(self, driver):
        for fn in self.ecs:
            try:
                if fn(driver): return True
            except:
                pass

class InvertarMutualFund:
    def __init__(self):
        self.currency = "ARS"
        self.name = ""
        self.ticker = ""
        self.lastTradingPrice = 0.0
        self.tradingSessions = []
        self.description = ""

    def to_JSON(self):
        for ts in self.tradingSessions:
            ts.tradingDate = int(time.mktime(datetime.strptime(ts.tradingDate, "%Y-%m-%d").timetuple())) * 1000 # int(datetime.strptime(ts.tradingDate, "%Y-%m-%d").timestamp())
            # int(time.mktime(datetime.strptime(oneInvertarTradingSession.tradingDate,"%Y-%m-%d").timetuple())) * 1000
            self.lastTradingPrice = self.tradingSessions[len(self.tradingSessions) - 1].closingPrice
        return json.dumps(self, default=lambda o: o.__dict__,
            sort_keys=True, indent=4)

class TradingMutualFundSession:
    def __init__(self):
        self.tradingDate = ""
        self.closingPrice = 0.0
        self.openingPrice = 0.0
        self.volume = 0.0

#I've gotta iterate over these days and keep in mind that there are no working days as well.
base = date.today()
date_list = [base - timedelta(days=x) for x in range(0, 730)]

fund_types = []
fund_types.append(1)
fund_types.append(2)
fund_types.append(4)

mutualFunds = []
#Stocks Funds
mutualFunds.append("1810 Renta Variable Argentina")
mutualFunds.append("Alpha Acciones - Clase A")
mutualFunds.append("Alpha Acciones - Clase B")
mutualFunds.append("Alpha Acciones - Clase C")
mutualFunds.append("Arpenta Acciones Argentina")
mutualFunds.append("Axis Renta Variable - Clase A")
mutualFunds.append("Axis Renta Variable - Clase B")
mutualFunds.append("FBA Calificado - Clase A")
mutualFunds.append("FBA Calificado - Clase B")
mutualFunds.append("Fima Acciones")
mutualFunds.append("Fima PB Acciones - Clase A")
mutualFunds.append("Fima PB Acciones - Clase B")
mutualFunds.append("Galileo Acciones")
mutualFunds.append("HF Acciones Lideres - Clase G")
mutualFunds.append("HF Acciones Lideres - Clase I")
mutualFunds.append("Lombard Acciones Lideres")
mutualFunds.append("MAF Acciones Argentinas")
mutualFunds.append("Pellegrini Acciones - Clase A")
mutualFunds.append("Pellegrini Acciones - Clase B")
mutualFunds.append("Pionero Acciones")
mutualFunds.append("Premier Renta Variable - Clase A")
mutualFunds.append("Premier Renta Variable - Clase B")
mutualFunds.append("SBS Acciones Argentina - Clase A")
mutualFunds.append("SBS Acciones Argentina - Clase B")
mutualFunds.append("ST Performance - Clase A")
mutualFunds.append("ST Performance - Clase B")
mutualFunds.append("Superfondo Acciones - Clase A")
mutualFunds.append("Superfondo Acciones - Clase B")
mutualFunds.append("AL Renta Variable - Clase A")
mutualFunds.append("AL Renta Variable - Clase B")
mutualFunds.append("Goal Acciones Argentinas - Clase A")
mutualFunds.append("Goal Acciones Argentinas - Clase B")
mutualFunds.append("RJ Delta Acciones - Clase A")
mutualFunds.append("RJ Delta Acciones - Clase B")
mutualFunds.append("Superfondo Renta Variable - Clase A")
mutualFunds.append("Superfondo Renta Variable - Clase B")
mutualFunds.append("Alpha Mega - Clase A")
mutualFunds.append("Alpha Mega - Clase B")
mutualFunds.append("Alpha Mega - Clase C")
mutualFunds.append("Compass Crecimiento Clase A")
mutualFunds.append("Compass Crecimiento Clase B")
mutualFunds.append("Consultatio Acciones Argentina - Clase A")
mutualFunds.append("Consultatio Acciones Argentina - Clase B")
mutualFunds.append("FBA Acciones Argentinas - Clase B")
mutualFunds.append("fST Acciones - Clase A")
mutualFunds.append("fST Acciones - Clase B")
mutualFunds.append("HF Acciones Argentinas - Clase G")
mutualFunds.append("HF Acciones Argentinas - Clase I")
mutualFunds.append("IAM Renta Variable - Clase A")
mutualFunds.append("IAM Renta Variable - Clase B")
mutualFunds.append("Optimum Renta Variable III")
mutualFunds.append("Schroder Renta Variable")
mutualFunds.append("1822 Raices Valores Negociables")
mutualFunds.append("Delval")
mutualFunds.append("RJ Delta Recursos Naturales - Clase A")
mutualFunds.append("RJ Delta Recursos Naturales - Clase B")
mutualFunds.append("Tavelli Plus")
mutualFunds.append("Gainvest Renta Variable")
mutualFunds.append("Megainver Renta Variable - Clase A")
mutualFunds.append("Megainver Renta Variable - Clase B")
mutualFunds.append("Compass Crecimiento II - Clase A")
mutualFunds.append("Compass Crecimiento II - Clase B")
mutualFunds.append("FBA Acciones Latinoamericana - Clase A")
mutualFunds.append("FBA Acciones Latinoamericana - Clase B")
mutualFunds.append("Alpha Mercosur - Clase A")
mutualFunds.append("Alpha Mercosur - Clase B")
mutualFunds.append("Goal Acciones Brasil - Clase A")
mutualFunds.append("Goal Acciones Brasil - Clase B")
mutualFunds.append("Alpha Recursos Naturales - Clase A")
mutualFunds.append("Alpha Recursos Naturales - Clase B")
mutualFunds.append("Consultatio Renta Variable - Clase A")
mutualFunds.append("Consultatio Renta Variable - Clase B")


#Bonds Funds
mutualFunds.append("1822 Raices Valores Fiduciarios")
mutualFunds.append("AL Ahorro Plus - Clase A")
mutualFunds.append("AL Ahorro Plus - Clase B")
mutualFunds.append("AL Ahorro Plus - Clase C")
mutualFunds.append("Alpha Ahorro - Clase C")
mutualFunds.append("Axis Ahorro Pesos - Clase A")
mutualFunds.append("Axis Ahorro Pesos - Clase B")
mutualFunds.append("Balanz Capital Ahorro - Clase A")
mutualFunds.append("Balanz Capital Ahorro - Clase B")
mutualFunds.append("Cohen Renta Fija - Clase A - Minorista")
mutualFunds.append("Cohen Renta Fija - Clase B - Institucionales")
mutualFunds.append("Cohen Renta Fija - Clase C")
mutualFunds.append("Consultatio Ahorro Plus Argentina F.C.I. - Clase A")
mutualFunds.append("Consultatio Ahorro Plus Argentina F.C.I. - Clase B")
mutualFunds.append("Consultatio Ahorro Plus Argentina F.C.I. - Clase C")
mutualFunds.append("FBA Ahorro Pesos - Clase A")
mutualFunds.append("FBA Ahorro Pesos - Clase B")
mutualFunds.append("Fima Ahorro Pesos - Clase A")
mutualFunds.append("Fima Ahorro Pesos - Clase B")
mutualFunds.append("Fima Ahorro Pesos - Clase C")
mutualFunds.append("fST Ahorro")
mutualFunds.append("Galileo Ahorro")
mutualFunds.append("Goal Capital Plus - Clase A")
mutualFunds.append("Goal Capital Plus - Clase B")
mutualFunds.append("GPS Savings - Clase A")
mutualFunds.append("GPS Savings - Clase B")
mutualFunds.append("HF Pesos Plus - Clase G")
mutualFunds.append("HF Pesos Plus - Clase I")
mutualFunds.append("IAM Renta Plus - Clase A")
mutualFunds.append("IAM Renta Plus - Clase B")
mutualFunds.append("Lombard Capital")
mutualFunds.append("MAF Pesos Plus - Clase A")
mutualFunds.append("MAF Pesos Plus - Clase B")
mutualFunds.append("MAF Pesos Plus - Clase C")
mutualFunds.append("Megainver Ahorro - Clase A")
mutualFunds.append("Megainver Ahorro - Clase B")
mutualFunds.append("Optimum Renta Fija Argentina")
mutualFunds.append("Pellegrini Renta Fija - Clase A")
mutualFunds.append("Pellegrini Renta Fija - Clase B")
mutualFunds.append("Pionero FF")
mutualFunds.append("Premier Renta Plus en Pesos - Clase A")
mutualFunds.append("Premier Renta Plus en Pesos - Clase B")
mutualFunds.append("RJ Delta Ahorro - Clase A")
mutualFunds.append("RJ Delta Ahorro - Clase B")
mutualFunds.append("SBS Pesos Plus - Clase A")
mutualFunds.append("SBS Pesos Plus - Clase B")
mutualFunds.append("Supergestion Mix VI - Clase A")
mutualFunds.append("Supergestion Mix VI - Clase B")
mutualFunds.append("Compass Ahorro - Clase A")
mutualFunds.append("Compass Ahorro - Clase B")
mutualFunds.append("Consultatio Renta Fija Argentina F.C.I. - Clase A")
mutualFunds.append("Consultatio Renta Fija Argentina F.C.I. - Clase B")
mutualFunds.append("Convexity Pesos Plus - Clase A")
mutualFunds.append("Convexity Pesos Plus - Clase B")
mutualFunds.append("Toronto Trust Renta Fija - Clase A")
mutualFunds.append("Toronto Trust Renta Fija - Clase B")
mutualFunds.append("Toronto Trust Renta Fija - Clase C")
mutualFunds.append("Alianza de Capitales")
mutualFunds.append("Alpha Renta Plus - Clase A")
mutualFunds.append("Alpha Renta Plus - Clase B")
mutualFunds.append("CMA Proteccion - Clase A")
mutualFunds.append("CMA Proteccion - Clase B")
mutualFunds.append("Fima Ahorro Plus - Clase A")
mutualFunds.append("Fima Ahorro Plus - Clase B")
mutualFunds.append("Pionero Renta Ahorro")
mutualFunds.append("Premier Renta Fija Ahorro - Clase A")
mutualFunds.append("Premier Renta Fija Ahorro - Clase B")
mutualFunds.append("RJ Delta Ahorro Plus - Clase A")
mutualFunds.append("RJ Delta Ahorro Plus - Clase B")
mutualFunds.append("SBS Ahorro Pesos - Clase A")
mutualFunds.append("SBS Ahorro Pesos - Clase B")
mutualFunds.append("Argenfunds Renta Privada F.C.I.")
mutualFunds.append("Consultatio Deuda Argentina - Clase A")
mutualFunds.append("Consultatio Deuda Argentina - Clase B")
mutualFunds.append("Consultatio Deuda Argentina - Clase C")
mutualFunds.append("FBA Bonos Argentina - Clase A")
mutualFunds.append("FBA Bonos Argentina - Clase B")
mutualFunds.append("Fima Capital Plus - Clase A")
mutualFunds.append("Fima Capital Plus - Clase B")
mutualFunds.append("Fima Capital Plus - Clase C")
mutualFunds.append("Gainvest FF - Clase A")
mutualFunds.append("Gainvest FF - Clase B")
mutualFunds.append("Gainvest Renta Fija")
mutualFunds.append("Gainvest Renta Mixta")
mutualFunds.append("Goal Renta Pesos - Clase A")
mutualFunds.append("Goal Renta Pesos - Clase B")
mutualFunds.append("Megainver Renta Fija - Clase A")
mutualFunds.append("Megainver Renta Fija - Clase B")
mutualFunds.append("SBS Capital Plus - Clase A")
mutualFunds.append("SBS Capital Plus - Clase B")
mutualFunds.append("Schroder Corto Plazo")
mutualFunds.append("Tavelli Mix")
mutualFunds.append("Convexity Renta Plus - Clase A")
mutualFunds.append("Convexity Renta Plus - Clase B")
mutualFunds.append("fST Ahorro Plus - Clase B")
mutualFunds.append("Gainvest Renta Fija Proteccion Plus")
mutualFunds.append("RJ Delta Gestion IV - Clase A")
mutualFunds.append("RJ Delta Gestion IV - Clase B")
mutualFunds.append("Goal Ahorro Max FCI - Clase A")
mutualFunds.append("Goal Ahorro Max FCI - Clase B")
mutualFunds.append("Cima Renta Fija Nacional - Clase A")
mutualFunds.append("Cima Renta Fija Nacional - Clase B")
mutualFunds.append("Convexity Renta Fija Argentina - Clase A")
mutualFunds.append("Convexity Renta Fija Argentina - Clase B")
mutualFunds.append("ST Renta Fija - Clase A")
mutualFunds.append("ST Renta Fija - Clase B")
mutualFunds.append("1822 Raices Inversion")
mutualFunds.append("1822 Raices Renta en Pesos")
mutualFunds.append("1822 Raices Renta Global")
mutualFunds.append("Alpha Renta Capital - Clase A")
mutualFunds.append("Alpha Renta Capital - Clase B")
mutualFunds.append("Alpha Renta Capital Pesos - Clase A")
mutualFunds.append("Alpha Renta Capital Pesos - Clase B")
mutualFunds.append("Axis Renta Fija - Clase A")
mutualFunds.append("Axis Renta Fija - Clase B")
mutualFunds.append("Axis Renta Fija - Clase C")
mutualFunds.append("Axis Renta Fija Cobertura - Clase A")
mutualFunds.append("Axis Renta Fija Cobertura - Clase B")
mutualFunds.append("Cima Renta Fija Argentina Plus - Clase A")
mutualFunds.append("Cima Renta Fija Argentina Plus - Clase B")
mutualFunds.append("Cima Renta Fija Argentina Plus - Clase C")
mutualFunds.append("Cohen Cobertura - Clase A")
mutualFunds.append("Cohen Cobertura - Clase B")
mutualFunds.append("Compass Opportunity Clase A")
mutualFunds.append("Compass Opportunity Clase B")
mutualFunds.append("Compass Renta Fija III - Clase A")
mutualFunds.append("Compass Renta Fija III - Clase B")
mutualFunds.append("Fima Renta Pesos - Clase A")
mutualFunds.append("Fima Renta Pesos - Clase B")
mutualFunds.append("Fima Renta Pesos - Clase C")
mutualFunds.append("Fima Renta Plus - Clase A")
mutualFunds.append("Fima Renta Plus - Clase B")
mutualFunds.append("Fima Renta Plus - Clase C")
mutualFunds.append("fST Renta")
mutualFunds.append("GPS Fixed Income - Clase A")
mutualFunds.append("GPS Fixed Income - Clase B")
mutualFunds.append("HF Pesos Renta Fija - Clase G")
mutualFunds.append("HF Pesos Renta Fija - Clase I")
mutualFunds.append("IAM Renta Crecimiento - Clase A")
mutualFunds.append("IAM Renta Crecimiento - Clase B")
mutualFunds.append("Megainver Renta Fija Cobertura - Clase A")
mutualFunds.append("Megainver Renta Fija Cobertura - Clase B")
mutualFunds.append("Optimum FAE (Fondo de Aplicaciones Especiales)")
mutualFunds.append("Pionero Renta")
mutualFunds.append("Premier Renta Fija Crecimiento - Clase A")
mutualFunds.append("Premier Renta Fija Crecimiento - Clase B")
mutualFunds.append("RJ Delta Moneda - Clase A")
mutualFunds.append("RJ Delta Moneda - Clase B")
mutualFunds.append("RJ Delta Renta - Clase A")
mutualFunds.append("RJ Delta Renta - Clase B")
mutualFunds.append("SBS Estrategia - Clase A")
mutualFunds.append("SBS Estrategia - Clase B")
mutualFunds.append("SBS Gestion Renta Fija - Clase A")
mutualFunds.append("SBS Gestion Renta Fija - Clase B")
mutualFunds.append("SBS Renta Pesos - Clase A")
mutualFunds.append("SBS Renta Pesos - Clase B")
mutualFunds.append("Superfondo Renta $ - Clase A")
mutualFunds.append("Superfondo Renta $ - Clase B")
mutualFunds.append("Tavelli Renta")
mutualFunds.append("Argenfunds Renta Argentina")
mutualFunds.append("Argenfunds Renta Pesos")
mutualFunds.append("Chaco Fondos Money Market I - Clase A")
mutualFunds.append("Chaco Fondos Money Market I - Clase B")
mutualFunds.append("CMA Argentina - Clase A")
mutualFunds.append("CMA Argentina - Clase B")
mutualFunds.append("Consultatio Renta Local - Clase A")
mutualFunds.append("Consultatio Renta Local - Clase B")
mutualFunds.append("Consultatio Renta Local - Clase C")
mutualFunds.append("Consultatio Renta Nacional - Clase A")
mutualFunds.append("Consultatio Renta Nacional - Clase B")
mutualFunds.append("Consultatio Renta Nacional - Clase C")
mutualFunds.append("fST Performance - Clase B")
mutualFunds.append("Gainvest Capital - Clase B")
mutualFunds.append("Gainvest Regional")
mutualFunds.append("Galileo Global")
mutualFunds.append("Galileo Renta Fija")
mutualFunds.append("Goal Corp - Clase B")
mutualFunds.append("Goal Performance - Clase B")
mutualFunds.append("Lombard Abierto Plus")
mutualFunds.append("Optimum Renta Fija Estrategica - Clase A")
mutualFunds.append("Optimum Renta Fija Estrategica - Clase B")
mutualFunds.append("Optimum Renta Mixta Flexible - Clase B")
mutualFunds.append("Pellegrini Renta Fija Ahorro - Clase A")
mutualFunds.append("Premier Capital - Clase A")
mutualFunds.append("Premier Capital - Clase B")
mutualFunds.append("SBS Renta Capital - Clase B")
mutualFunds.append("SC II Renta Fija - Clase A")
mutualFunds.append("SC II Renta Fija - Clase B")
mutualFunds.append("SC II Renta Fija - Clase C")
mutualFunds.append("SC II Renta Fija - Clase D")
mutualFunds.append("Schroder Argentina")
mutualFunds.append("Schroder Renta Plus")
mutualFunds.append("Southern Trust Renta Plus")
mutualFunds.append("Toronto Trust Renta Fija Plus - Clase A")
mutualFunds.append("Toronto Trust Renta Fija Plus - Clase B")
mutualFunds.append("Toronto Trust Renta Fija Plus - Clase C")
mutualFunds.append("Argenfunds Ahorro Pesos")
mutualFunds.append("Alpha Renta Cobertura - Clase A")
mutualFunds.append("Alpha Renta Cobertura - Clase B")
mutualFunds.append("Alpha Renta Crecimiento - Clase A")
mutualFunds.append("Alpha Renta Crecimiento - Clase B")
mutualFunds.append("Cohen Renta Fija Plus - Clase A")
mutualFunds.append("Cohen Renta Fija Plus - Clase B")
mutualFunds.append("FBA Horizonte")
mutualFunds.append("Gainvest Renta Fija Plus")
mutualFunds.append("Gestionar Renta Fija - Clase A")
mutualFunds.append("Gestionar Renta Fija - Clase B")
mutualFunds.append("Goal Renta Crecimiento - Clase A")
mutualFunds.append("Goal Renta Crecimiento - Clase B")
mutualFunds.append("Investire Renta Plus")
mutualFunds.append("Lombard Renta Fija")
mutualFunds.append("MAF Renta - Clase A")
mutualFunds.append("MAF Renta - Clase B")
mutualFunds.append("MAF Renta Argentina")
mutualFunds.append("MAF Renta Argentina 2 - Clase A")
mutualFunds.append("MAF Renta Argentina 2 - Clase B")
mutualFunds.append("Pellegrini Renta Publica Mixta - Clase A")
mutualFunds.append("Pellegrini Renta Publica Mixta - Clase B")
mutualFunds.append("RJ Delta Federal I - Clase A")
mutualFunds.append("RJ Delta Federal I - Clase B")
mutualFunds.append("Superfondo 2000")
mutualFunds.append("Superfondo 2001 ex Letes Dolares 18/01/2002")
mutualFunds.append("Superfondo U$S Plus - Clase A")
mutualFunds.append("Superfondo U$S Plus - Clase B")
mutualFunds.append("AL Renta Fija - Clase A")
mutualFunds.append("AL Renta Fija - Clase B")
mutualFunds.append("AL Renta Fija - Clase C")
mutualFunds.append("Arpenta Ahorro Pesos")
mutualFunds.append("Galileo Premium - Clase A")
mutualFunds.append("Galileo Premium - Clase B")
mutualFunds.append("Pellegrini Renta Fija Publica")
mutualFunds.append("fST Estrategico - Clase A")
mutualFunds.append("fST Estrategico - Clase B")
mutualFunds.append("Schroder Renta Fija - Clase A")
mutualFunds.append("Schroder Capital Renta Fija")
mutualFunds.append("Chaco Fondos Renta Fija I - Clase A")
mutualFunds.append("Chaco Fondos Renta Fija I - Clase B")
mutualFunds.append("Megainver Renta Global - Clase A")
mutualFunds.append("Megainver Renta Global - Clase B")
mutualFunds.append("Compass Renta Fija II - Clase A")
mutualFunds.append("Compass Renta Fija II - Clase B")
mutualFunds.append("Goal Renta Global - Clase A")
mutualFunds.append("Goal Renta Global - Clase B")
mutualFunds.append("Pionero Renta Dolares")
mutualFunds.append("Balanz Capital Renta Fija - Clase A")
mutualFunds.append("Balanz Capital Renta Fija - Clase B")
mutualFunds.append("FBA Bonos Globales - Clase B")

#Hybrid Funds
mutualFunds.append("Pellegrini Agro FCI - Clase A")
mutualFunds.append("Pellegrini Agro FCI - Clase B")
mutualFunds.append("Premier Commodities Agrarios - Clase A")
mutualFunds.append("Premier Commodities Agrarios - Clase B")
mutualFunds.append("Convexity IOL Acciones - Clase A")
mutualFunds.append("Convexity IOL Acciones - Clase B")
mutualFunds.append("Arpenta (ex Mercosur)")
mutualFunds.append("GSS I - Clase A")
mutualFunds.append("GSS I - Clase B")
mutualFunds.append("GSS II - Clase A")
mutualFunds.append("GSS II - Clase B")
mutualFunds.append("GSS III - Clase A")
mutualFunds.append("GSS III - Clase B")
mutualFunds.append("Investire Renta Mixta")
mutualFunds.append("Pellegrini Integral - Clase A")
mutualFunds.append("Pellegrini Integral - Clase B")
mutualFunds.append("Probolsa")
mutualFunds.append("AL Desarrollo Argentino - Clase A")
mutualFunds.append("AL Desarrollo Argentino - Clase B")
mutualFunds.append("AL Desarrollo Argentino - Clase C")
mutualFunds.append("AL Renta Balanceada I - Clase A")
mutualFunds.append("AL Renta Balanceada I - Clase B")
mutualFunds.append("AL Renta Balanceada II - Clase A")
mutualFunds.append("AL Renta Balanceada II - Clase B")
mutualFunds.append("AL Renta Mixta - Clase A")
mutualFunds.append("AL Renta Mixta - Clase B")
mutualFunds.append("AL Renta Mixta - Clase C")
mutualFunds.append("Compass Natural Resources II - Clase")
mutualFunds.append("Compass Pacific II - Clase B")
mutualFunds.append("fST Renta Mixta - Clase A")
mutualFunds.append("fST Renta Mixta - Clase B")
mutualFunds.append("Gainvest Balanceado")
mutualFunds.append("Pionero Consumo")
mutualFunds.append("RJ Delta Gestion III - Clase B")
mutualFunds.append("RJ Delta Multimercado I - Clase A")
mutualFunds.append("RJ Delta Multimercado I - Clase B")
mutualFunds.append("RJ Delta Multimercado II - Clase B")
mutualFunds.append("Southern Trust Estrategico - Clase A")
mutualFunds.append("Southern Trust Estrategico - Clase B")
mutualFunds.append("ST Renta Mixta - Clase A")
mutualFunds.append("ST Renta Mixta - Clase B")
mutualFunds.append("Toronto Trust Multimercado - Clase A")
mutualFunds.append("Toronto Trust Multimercado - Clase B")
mutualFunds.append("Toronto Trust Multimercado - Clase C")
mutualFunds.append("Optimum Global Balanced - Clase B")
mutualFunds.append("Consultatio Renta Balanceada - Clase A")
mutualFunds.append("Consultatio Renta Balanceada - Clase B")
mutualFunds.append("Consultatio Renta Balanceada - Clase C")
mutualFunds.append("Megainver Balanceado - Clase B")
mutualFunds.append("MAF Renta Mixta")
mutualFunds.append("Gainvest Crecimiento - Clase B")
mutualFunds.append("MAF Renta Balanceada")
mutualFunds.append("Megainver Financiamiento Productivo")
mutualFunds.append("Megainver Renta Mixta - Clase A")
mutualFunds.append("Megainver Renta Mixta - Clase B")
mutualFunds.append("Optimum Renta Mixta Flexible Argentina - Clase B")
mutualFunds.append("SBS Balanceado - Clase A")
mutualFunds.append("SBS Balanceado - Clase B")
mutualFunds.append("SC I Renta Mixta - Clase A")
mutualFunds.append("SC I Renta Mixta - Clase B")
mutualFunds.append("SC I Renta Mixta - Clase C")
mutualFunds.append("Schroder Retorno Absoluto")
mutualFunds.append("Toronto Trust Special Opportunities")
mutualFunds.append("Alpha Renta Balanceada Global - Clase A")
mutualFunds.append("Alpha Renta Balanceada Global - Clase B")
mutualFunds.append("Optimum Global Investment Grade")
mutualFunds.append("Optimum Global Renta Mixta")
mutualFunds.append("Super Renta Futura - Clase A")
mutualFunds.append("Super Renta Futura - Clase B")
mutualFunds.append("GPS Classic - Clase A")
mutualFunds.append("GPS Classic - Clase B")
mutualFunds.append("Superfondo Combinado - Clase A")
mutualFunds.append("Superfondo Combinado - Clase B")
mutualFunds.append("Superfondo Combinado - Clase C")
mutualFunds.append("Superfondo Combinado - Clase D")
mutualFunds.append("Superfondo Equilibrado - Clase A")
mutualFunds.append("Superfondo Equilibrado - Clase B")
mutualFunds.append("Superfondo Equilibrado - Clase C")
mutualFunds.append("Superfondo Equilibrado - Clase D")
mutualFunds.append("Superfondo Renta Mixta - Clase A")
mutualFunds.append("Superfondo Renta Mixta - Clase B")
mutualFunds.append("Superfondo Renta Mixta - Clase C")
mutualFunds.append("Superfondo Renta Mixta - Clase D")
mutualFunds.append("Supergestion Balanceado - Clase A")
mutualFunds.append("Supergestion Balanceado - Clase B")
mutualFunds.append("Supergestion Balanceado - Clase C")
mutualFunds.append("Supergestion Balanceado - Clase D")
mutualFunds.append("Supergestion Multimercado - Clase A")
mutualFunds.append("Supergestion Multimercado - Clase B")
mutualFunds.append("Supergestion Multimercado - Clase C")
mutualFunds.append("Tavelli Global")
mutualFunds.append("Consultatio Renta Mixta - Clase A")
mutualFunds.append("Consultatio Renta Mixta - Clase B")
mutualFunds.append("Consultatio Renta Mixta - Clase C")
mutualFunds.append("Invertir Global - Clase A")
mutualFunds.append("Invertir Global - Clase B")
mutualFunds.append("RJ Delta Gestion I - Clase B")
mutualFunds.append("RJ Delta Gestion II - Clase B")

driver = webdriver.Firefox()

date_list.reverse()

finalMutualFunds = []

for oneFundType in fund_types:

    fundType = ""
    if oneFundType==1:
        fundType = "Stock Fund"

    elif oneFundType==2:
        fundType = "Bond Fund"

    elif oneFundType==4:
        fundType = "Hybrid Fund"

    count = 0

    for oneDate in date_list:
        if str(oneDate) not in ["2014-12-25","2015-01-01","2013-12-25","2014-01-01"]:

            print("Procesando:",oneFundType,oneDate)

            driver.get("http://www.cafci.org.ar/scripts/cfn_Estadisticas.html")

            driver.switch_to.frame("frame_contents")

            driver.find_element_by_xpath("/html/body/div[1]/table/tbody/tr[1]/td[2]/select/option[@value=" + str(oneFundType) + "]").click()

            driver.find_element_by_xpath("/html/body/div[1]/table/tbody/tr[2]/td[2]/table/tbody/tr/td/input").send_keys(oneDate.strftime("%d/%m/%Y"))

            driver.find_element_by_xpath("/html/body/div[1]/table/tbody/tr[3]/td[2]/div[2]/a").click()

            WebDriverWait(driver, 15).until(AnyEc(expected_conditions.element_to_be_clickable((By.ID, "titlePage")),expected_conditions.element_to_be_clickable((By.ID, "errorDiv"))))

            time.sleep(1)

            if driver.current_url != "http://www.cafci.org.ar/scripts/cfn_Estadisticas.html":

                html = driver.page_source

                soup = BeautifulSoup(html,"html.parser")

                driver.switch_to_default_content()

                wait = WebDriverWait(driver, 50)

                wait.until(lambda driver: driver.find_element_by_name('tablaVCP'))

                wait.until(expected_conditions.presence_of_element_located((By.NAME,'tablaVCP')))
                wait.until(expected_conditions.presence_of_element_located((By.CLASS_NAME,'tituloTablaEstadisticas')))

                while soup.find("table",{"name":"tablaVCP"}) is None or len(soup.find("table",{"name":"tablaVCP"}).find_all("tr")) == 1:

                    driver.refresh()

                    time.sleep(3)

                    html = driver.page_source

                    soup = BeautifulSoup(html,"html.parser")

                    driver.switch_to_default_content()

                    wait = WebDriverWait(driver, 20)

                    wait.until(lambda driver: driver.find_element_by_name('tablaVCP'))

                    wait.until(expected_conditions.presence_of_element_located((By.NAME,'tablaVCP')))
                    wait.until(expected_conditions.presence_of_element_located((By.CLASS_NAME,'tituloTablaEstadisticas')))

                table = soup.find("table",{"class":"valores"})

                rows = soup.find("table",{"name":"tablaVCP"}).find_all("tr")

                index=99999

                name = ""

                for row in rows:

                    cells = row.find_all("td")

                    for cell in cells:
                        name = cell.string.strip()


                        if index == 3:
                            newTradingMutualFundSession = TradingMutualFundSession()
                            newTradingMutualFundSession.tradingDate = datetime.strptime(cell.string.strip(),"%d/%m/%Y").date().strftime("%Y-%m-%d")
                        elif index == 4:
                            newTradingMutualFundSession.closingPrice = cell.string.strip().replace(".","").replace(",",".")
                            last_closing_price = newTradingMutualFundSession.closingPrice
                        elif index == 5:
                            newTradingMutualFundSession.volume = cell.string.strip().replace(".","")
                        elif index == 6:
                            count_2 = 0
                            found = 0

                            for aMutualFund in finalMutualFunds:
                                if aMutualFund.name == currentMutualFund.name:
                                    found = 1
                                    break
                                count_2 = count_2 + 1

                            if found ==1:
                                finalMutualFunds.pop(count_2)

                            newTradingMutualFundSession.closingPrice = last_closing_price

                            currentMutualFund.tradingSessions.append(newTradingMutualFundSession)

                            if str(newTradingMutualFundSession.tradingDate) in ["2014-11-05","2015-11-05"]:

                                specialTradingMutualFundSession = TradingMutualFundSession()
                                specialTradingMutualFundSession.closingPrice = newTradingMutualFundSession.closingPrice
                                specialTradingMutualFundSession.volume= newTradingMutualFundSession.volume

                                if str(newTradingMutualFundSession.tradingDate) == "2014-11-05":
                                    specialTradingMutualFundSession.tradingDate =datetime.strptime("06/11/2014","%d/%m/%Y").date().strftime("%Y-%m-%d")
                                else:
                                    specialTradingMutualFundSession.tradingDate =datetime.strptime("06/11/2015","%d/%m/%Y").date().strftime("%Y-%m-%d")

                                currentMutualFund.tradingSessions.append(specialTradingMutualFundSession)

                            if len(currentMutualFund.tradingSessions)-1 != 0:
                                currentMutualFund.tradingSessions[len(currentMutualFund.tradingSessions)-1].openingPrice = currentMutualFund.tradingSessions[len(currentMutualFund.tradingSessions)-2].closingPrice

                            finalMutualFunds.append(currentMutualFund)
                        if name in mutualFunds:
                            if count==0:
                                newMutualFund = InvertarMutualFund()

                                newMutualFund.name = name

                                newMutualFund.tradingSessions = []

                                newMutualFund.fundType= fundType

                                newMutualFund.ticker = name.replace(' ', '') + '.' + fundType.replace(' ', '')
                                newMutualFund.description = name

                                finalMutualFunds.append(newMutualFund)
                                currentMutualFund = newMutualFund

                            else:
                                for aMutualFund in finalMutualFunds:
                                    if aMutualFund.name == name:
                                        currentMutualFund = aMutualFund
                                        break
                            index = 1
                        index = index+1

                count = count + 1

# Open connection to API
conn = requests.request('POST', url=login_url, headers=headers, data=json.dumps(credentials))
session_cookie = conn.cookies

for oneFund in finalMutualFunds:
    jsonObject = oneFund.to_JSON()

    r =requests.request('POST', url=store_url, headers=headers, cookies=session_cookie, data=jsonObject)

    if r.status_code == 200:
        print("Cargado:", oneFund.ticker)
    else:
        print("Error en la carga del FCI {}: {}".format(oneFund.ticker, r.content))

driver.close()
