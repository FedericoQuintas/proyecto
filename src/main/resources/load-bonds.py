from datetime import *
import io
import csv
import json
from datetime import datetime
import time
import requests
import urllib

# API connection parameters
login_url = 'http://localhost:8080/login'
store_url = 'http://localhost:8080/assets/bonds'
credentials = {
    "mail": "admin@invertar.com",
    "password": "admin"
}
headers = {'Content-Type': 'application/json'}

class InvertarBond:

    ticker = ""
    name = ""
    lastTradingPrice = 0.0
    currency = "ARS"
    tradingSessions = []
    dollarLinked = False
    def to_JSON(self):
        return json.dumps(self, default=lambda o: o.__dict__,
            sort_keys=True, indent=4)

    def __init__(self,aTicker,aName):
        self.ticker = aTicker
        self.name = aName
        self.description = "descripcion"

class InvertarTradingSession:

    adjClosingPrice = 0.0
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
    description= ""
    ticker = ""
    name = ""
    url = ""
    dollar_linked= False
    def __init__(self, aTicker,aDecription,aType,aName, anUrl):
         self.ticker = aTicker
         self.description = aDecription
         self.name = aName
         self.dollar_linked=aType
         self.url = anUrl

csvsToDownload = []
csvsToDownload.append(Link("AF17","Fecha de Emision: 19 de agosto de 2015. Fecha de Vencimiento: 22 de febrero de 2017. Plazo: 18 meses. Amortizacion: integra al vencimiento. Cupon: 0,75% anual. Los intereses seran pagaderos semestralmente los dias 22 de febrero y 22 de agosto de cada ano hasta el vencimiento (base 30/360). Moneda de denominacion: Dolares Estadounidenses. Moneda de suscripcion: Pesos, al Tipo de Cambio Inicial. Moneda de pago: Pesos, al Tipo de Cambio Aplicable.",True,"BONAD 2017","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AF17&csv=1"))
csvsToDownload.append(Link("AM18","Moneda de emision: Dolares. Fecha de Emision: 18/11/2014. Fecha Vencimiento: 18/03/2018. Monto nominal vigente en la moneda original de emision: 1.000.000.000,00. Interes: Tasa fija del 2.40% nominal anual, sobre la base de un ano de 360 dias, de 12 meses de 30 dias cada uno. 1er servicio cuatrimestral pagadero el 18/03/2015, luego pago semestral los dias 18 de marzo y 18 de septiembre de cada ano hasta su vencimiento el 18/03/2018 - Moneda de pago pesos, al tipo de cambio aplicable. Forma de amortizacion: Integra al vencimiento el 18 de marzo de 2018.",True,"BONAD 2018","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AM18&csv=1"))
csvsToDownload.append(Link("AO16","Fecha de Emision: 28/10/2014. Fecha Vencimiento: 28/10/2016. Monto nominal vigente en la moneda original de emision: 983.343.153. Interes: Devengan una tasa fija del 1,75% nominal anual, calculada sobre la base de un ano de 360 dias, integrado por 12 meses de 30 dias cada uno, pagaderos semestralmente los dias 28 de abril y 28 de octubre de cada ano hasta el vencimiento. Moneda de pago: Pesos, al Tipo de Cambio Aplicable. Forma de amortizacion: En una cuota al vencimiento. Moneda de pago: Pesos, al Tipo de Cambio Aplicable.",True,"BONAD 2016","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AO16&csv=1"))
csvsToDownload.append(Link("BD2C9","Fecha de Emision: 20/12/2013. Fecha de Vencimiento: 20/12/2019. Moneda de emision: Dolares. Moneda de pago: En Pesos al tipo de cambio al cierre del tercer dia habil inmediato anterior que resulte en la mayor cantidad de pesos entre (i) el tipo de cambio vendedor 'billete' publicado por el Banco de la Nacion Argentina y (ii) el tipo de cambio denominado 'EMTA ARS industry survey rate' publicado por Emerging Markets Traders Association en su pagina web. En caso que dicho tipo de cambio no fuera publicado por EMTA, este sera reemplazado, a efectos del calculo correspondiente, por el publicado por el Banco Central de la Republica Argentina de conformidad con lo establecido por la Comunicacion 'A' 3500. Intereses: Tasa fija anual del 1,95%. Los intereses seran pagaderos semestralmente los dias 20 de junio y 20 de diciembre de cada ano, siendo el primer vencimiento el 20 de junio de 2014. Los intereses seran calculados sobre la base de un ano de 360 dias, de 12 meses de 30 dias. Amortizacion: En 6 cuotas semestrales y consecutivas equivalentes al 16,67% las cinco primeras y al 16,65% la ultima. El primer pago de amortizacion sera el 20 de junio de 2017.",True,"Ciudad de Bs. As. 2019 Clase 5","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BD2C9&csv=1"))
csvsToDownload.append(Link("BDC18","Fecha de Emision: 15/03/2013. Fecha de Vencimiento: 15/03/2018. Moneda de emision: Dolares. Moneda de pago: En Pesos al tipo de cambio publicado el tercer dia habil inmediato anterior al pago que resulte mayor entre (i) el vendedor 'billete' del Banco Nacion y (ii) el EMTA ARS Industry Survey Rate informado por EMTA en su web o, en caso que este no fuera publicado, el tipo de cambio publicado por el BCRA conforme Comunicacion 'A' 3.500. Intereses: Tasa fija anual del 3,98%. Los intereses seran pagaderos semestralmente los dias 15/03 y 15/09 de cada ano. El primer pago es el 15/09/2013. La base para el computo de los intereses es 30/360. Amortizacion: En cuatro cuotas iguales los dias 15/09/2016, 15/03/2017, 15/09/2017 y 15/03/2018.",True,"Ciudad de Bs. As. 2018 Clase 3","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BDC18&csv=1"))
csvsToDownload.append(Link("BDC19","Fecha de Emision: 17/05/2013. Fecha de Vencimiento: 17/05/2019. Moneda de emision: Dolares. Moneda de pago: En Pesos al tipo de cambio publicado el tercer dia habil inmediato anterior al pago que resulte mayor entre (i) el vendedor 'billete' del Banco Nacion y (ii) el EMTA ARS Industry Survey Rate informado por EMTA en su web o, en caso que este no fuera publicado, el tipo de cambio publicado por el BCRA conforme Comunicacion 'A' 3.500. Intereses: Tasa fija anual del 3,98%. Los intereses seran pagaderos semestralmente los dias 17/05 y 17/11 de cada ano. El primer pago es el 17/11/2013. La base para el computo de los intereses es 30/360. Amortizacion: En seis cuotas. Las cinco primeras de 16,67% y la sexta de 16,65%. El primer pago es el 17/11/2016.",True,"Ciudad de Bs. As. 2019 Clase 4","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BDC19&csv=1"))
csvsToDownload.append(Link("BDC20","Fecha de Emision: 28 de enero de 2014. Fecha de Vencimiento: 28 de enero de 2020. Moneda de emision: Dolares. Moneda de pago: En Pesos al tipo de cambio al cierre del tercer dia habil inmediato anterior que resulte en la mayor cantidad de pesos entre (i) el tipo de cambio vendedor 'billete' publicado por el Banco de la Nacion Argentina y (ii) el tipo de cambio denominado 'EMTA ARS industry survey rate' publicado por Emerging Markets Traders Association en su pagina web. En caso que dicho tipo de cambio no fuera publicado por EMTA, este sera reemplazado, a efectos del calculo correspondiente, por el publicado por el Banco Central de la Republica Argentina de conformidad con lo establecido por la Comunicacion 'A' 3500. Intereses: Tasa fija anual del 1,95%. Los intereses seran pagaderos semestralmente los dias 28 de enero y 28 de julio de cada ano, siendo el primer pago el 28 de julio de 2014. Los intereses seran calculados sobre la base de un ano de 360 dias, de 12 meses de 30 dias. Amortizacion: En 6 cuotas semestrales y consecutivas equivalentes a 16,67% las cinco primeras y a 16,65% la ultima. El primer pago de amortizacion sera el 28 de julio de 2017.",True,"Ciudad de Bs. As. 2020 Clase 6","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BDC20&csv=1"))
csvsToDownload.append(Link("CO17","Fecha de emision: 27/11/2009. Fecha de vencimiento: 07/12/2017. Moneda de emision: Dolares. Moneda de pago: El capital y los intereses se abonaran en pesos calculados al tipo de cambio aplicable en cada fecha de pago. Interes: Tasa fija del 12% anual, pagadera en forma mensual los dias 7 de cada mes, o el dia habil siguiente si este fuera un dia inhabil. Seran calculados sobre la base de un ano de 360 dias compuesto por 12 meses de 30 dias cada uno. La primer fecha de pago sera el 07/01/2010.",True,"BonCor 2017","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CO17&csv=1"))
csvsToDownload.append(Link("ERG16","Fecha de Emision: 6/08/2013. Fecha de Vencimiento: 6/08/2016. Moneda de emision: Dolares. Moneda de pago: En Pesos al tipo de cambio disponible al cierre del sexto Dia Habil anterior a la Fecha de Pago, que resulte en la mayor cantidad de pesos entre: (i) el tipo de cambio vendedor 'billete' publicado por el Banco de la Nacion Argentina, (ii) el tipo de cambio denominado 'EMTA ARS Industry Survey Rate', publicado por Emerging Markets Traders Association ('EMTA') en su pagina web 'www.emta.org', o en cualquier otro sitio web o servicio electronico de informacion financiera que EMTA utilice o decida utilizar para comunicar dicho tipo de cambio, y (iii) el tipo de cambio publicado por el Banco Central de la Republica Argentina, de conformidad con lo establecido por la Comunicacion 'A' 3.500. A los efectos del calculo del Tipo de Cambio Aplicable, 'Dia Habil' significa cualquier dia que no sea sabado o domingo, ni un dia en el que las instituciones bancarias esten autorizadas u obligadas por ley, norma o decreto a no operar en la Ciudad Autonoma de Buenos Aires y/o en la ciudad de Nueva York, Estado de Nueva York, Estados Unidos de America. Intereses: Tasa fija anual de 4,8%. Los intereses seran pagaderos trimestralmente los dias 6 de febrero, mayo, agosto y noviembre de cada ano. El primer pago es el 6/11/2013. La base para el computo de los intereses es 30 / 360. Amortizacion: En nueve cuotas. Las ocho primeras de 11% y la novena de 12%. El primer pago es el 6/08/2014.",True,"Entre Rios 2016 Serie 1","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=ERG16&csv=1"))
csvsToDownload.append(Link("FORM3","Fecha de emision 27/02/2008. Fecha de vencimiento 27/02/2022. Renta: Tasa fija=5.00%. Amortizacion: 40 trimestres.",True,"Formosa 2022","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=FORM3&csv=1"))
csvsToDownload.append(Link("NDG1","Fecha de Emision: 12/06/2013. Fecha de Vencimiento: 12/06/2016. Moneda de emision: Dolares. Moneda de pago: En Pesos al tipo de cambio entre el Peso y el Dolar Estadounidense, correspondiente al cierre de la fecha correspondiente que resulte en la mayor cantidad de Pesos entre (a) el tipo de cambio entre el Peso y el Dolar Estadounidense vendedor 'billete' publicado por el Banco de la Nacion Argentina y (b) el tipo de cambio entre el Peso y el Dolar Estadounidense publicado por el BCRA, de conformidad con lo establecido por la Comunicacion 'A' 3.500 o la que en el futuro lo reemplace. En caso de que el tipo de cambio publicado por el BCRA no fuera publicado en el futuro, se debera utilizar el tipo de cambio entre el Peso y el Dolar Estadounidense publicado por EMTA en su pagina web: www.emta.org (seccion 'FX Rates - EMTA Rate Quotation Services - EMTA ARS Industry Survey - Historical Rate'). En caso de que el tipo de cambio no fuera publicado por EMTA, se debera utilizar el tipo de cambio entre el Peso y el Dolar Estadounidense vendedor promedio publicado por cinco entidades financieras argentinas, de alto reconocimiento en el mercado a ser seleccionadas por la Provincia, como por ejemplo: Banco Provincia del Neuquen S.A., Banco de Galicia y Buenos Aires S.A., Banco Macro S.A., Citibank N.A. Sucursal Argentina, Banco Santander Rio S.A., ICBC S.A., Deutsche Bank S.A. y HSBC Argentina S.A.. Intereses: Tasa fija anual del 3%. Los intereses seran pagaderos trimestralmente los dias 12 de marzo, junio, septiembre y diciembre de cada ano. El primer pago es el 12/09/2013. La base para el computo de los intereses es dias reales / dias reales. Amortizacion: En nueve cuotas. Las ocho primeras de 11,11% y la novena de 11,12%. El primer pago es el 12/06/2014.",True,"Neuquen 2016","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=NDG1&csv=1"))
csvsToDownload.append(Link("PMD18","Fecha de Emision: 18/12/2013. Fecha de Vencimiento: 18/12/2018. Moneda de emision: Dolares. Moneda de pago: En Pesos al tipo de cambio al cierre del sexto dia habil inmediato anterior a la fecha de pago, que resulte mayor entre: (i) el vendedor dolar 'billete' del Banco Nacion, ii) el EMTA ARS Industry Survey Rate, y iii) el BCRA comunicacion 'A' 3.500. Intereses: Tasa fija anual del 2,75%. Los intereses seran pagaderos trimestralmente los dias 18-mar, 18-jun, 18-sep y 18-dic de cada ano. El primer pago es el 18-mar-14. La base para el computo de los intereses es 30/360. Amortizacion: En diecisiete cuotas trimestrales consecutivas a partir del 18-dic-2014. La primera cuota es del 5,92%, y el resto de 5,88%.",True,"Mendoza 2018 Clase 3","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PMD18&csv=1"))
csvsToDownload.append(Link("PMO18","Fecha de Emision: 30/10/2013. Fecha de Vencimiento: 30/10/2018. Moneda de emision: Dolares. Moneda de pago: En Pesos al tipo de cambio al cierre del sexto dia habil inmediato anterior a la fecha de pago, que resulte mayor entre: (i) el vendedor dolar 'billete' del Banco Nacion, ii) el EMTA ARS Industry Survey Rate, y iii) el BCRA comunicacion 'A' 3.500. Intereses: Tasa fija anual del 2,75%. Los intereses seran pagaderos trimestralmente los dias 30-ene, 30-abr, 30-jul y 30-oct de cada ano. El primer pago es el 30-ene-14. La base para el computo de los intereses es 30/360. Amortizacion: En diecisiete cuotas trimestrales consecutivas a partir del 30-oct-2014. La primera cuota es del 5,92%, y el resto de 5,88%.",True,"Mendoza 2018 Clase 2","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PMO18&csv=1"))
csvsToDownload.append(Link("PMY16","Fecha de Emision: 28/05/2013. Fecha de Vencimiento: 28/05/2016. Moneda de emision: Dolares. Moneda de pago: En Pesos al tipo de cambio al cierre del sexto Dia Habil inmediato anterior a la Fecha de Pago que corresponda y que resulte en la mayor cantidad de Pesos entre: (x) el tipo de cambio vendedor 'billete' publicado por el Banco de la Nacion Argentina; (y) el tipo de cambio EMTA ARS Industry Survey Rate publicado por EMTA en su sitio web, y (z) el tipo de cambio publicado por el Banco Central de la Republica Argentina de conformidad con lo establecido por la Comunicacion 'A' 3500. Intereses: Tasa fija anual del 3%. Los intereses seran pagaderos trimestralmente los dias 28 de febrero, mayo, agosto y noviembre de cada ano. El primer pago es el 28/08/2013. La base para el computo de los intereses es 30/360. Amortizacion: En nueve cuotas. Las ocho primeras de 11,11% y la novena de 11,12%. El primer pago es el 28/05/2014.",True,"Mendoza 2016","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PMY16&csv=1"))
csvsToDownload.append(Link("PUO19","Fecha de Emision: 21/10/2013. Fecha de Vencimiento: 21/10/2019. Moneda de emision: Dolares. Moneda de pago: En Pesos al tipo de cambio disponible el quinto dia habil anterior al pago, que resulte mayor entre: (i) el vendedor 'billete' del Banco Nacion y, (ii) el EMTA ARS Industry Survey y iii) el informado por el BCRA a traves de su comunicacion 'A' 3.500. Intereses: Tasa fija anual del 4%. Los intereses seran pagaderos trimestralmente los dias 21-ene, 21-abr, 21-jul y 21-oct de cada ano. El primer pago es el 21-ene-14. La base para el computo de los intereses es 30/360. Amortizacion: En 16 cuotas trimestrales iguales y consecutivas. El primer pago es el 21-ene-2016.",True,"Chubut 2019","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PUO19&csv=1"))
csvsToDownload.append(Link("CUAP","Fecha de emision: 31/12/2003. Fecha de vencimiento: 31/12/2045. Moneda de emision: Pesos. Interes: Devengan intereses, calculados sobre la base de un ano de 360 dias integrado por doce meses de 30 dias cada uno, desde el 31.12.03 inclusive hasta pero sin incluir el 31.12.45, a una tasa anual equivalente al 3,31%. Los intereses devengados el o antes del 31.12.13 seran capitalizados, despues de esa fecha los pagos de intereses se efectuaran en efectivo. Las fechas de pago de intereses son el 30 de junio y 31 de diciembre de cada ano, comenzando el 30.06.14. El monto de capital que el tenedor recibio tras la liquidacion incluyo el monto de capital original mas los intereses capitalizados al 31.12.04. En consecuencia los intereses seran capitalizados desde el 31.12.04 hasta pero sin incluir la primera fecha de pago (30.06.14). Forma de amortizacion: El capital se pagara en 20 cuotas semestrales iguales el 30 de junio y el 31 de diciembre de cada ano, comenzando el 30 de junio de 2036, los montos de los pagos seran ajustados por inflacion en base al CER.",False,"CUASIPAR en $","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=CUAP&csv=1"))
csvsToDownload.append(Link("DICP","Fecha de emision: 31/12/2003. Fecha de vencimiento: 31/12/2033. Moneda de emision: Pesos. Interes: Los titulos devengaran intereses, calculados sobre la base de un ano de 360 dias integrado por doce meses de 30 dias cada uno, desde el 31.12.03 inclusive, hasta pero sin incluir el 31.12.33, al 5,83% Una parte de los intereses devengados antes del 31.12.13 se pagara en efectivo y otra parte sera capitalizada. Esto significa que en la fecha de pago pertinente la porcion de los intereses que se capitaliza no se paga en efectivo sino que, por el contrario, se suma el monto de capital de los titulos Discount, y los calculos futuros de los intereses se basan en este monto de capital ajustado. Los pagos en efectivo seran efectuados en la moneda en la que esten denominados los Titulos Discount correspondientes. Las fechas de pago de intereses para los titulos Discount son el 30 de junio y el 31 de diciembre de cada ano. Forma de amortizacion: En 20 cuotas semestrales el 30 de junio y el 31 de diciembre de cada ano, comenzando el 30 de junio de 2024. Cada uno de los 20 pagos semestrales incluira los montos capitalizados devengados antes de la primera fecha de amortizacion.",False,"Discount $ Ley Arg.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=DICP&csv=1"))
csvsToDownload.append(Link("DIP0","Fecha de emision: 31/12/2003. Fecha de vencimiento: 31/12/2033. Moneda de emision: Pesos. Interes: Los titulos devengaran intereses al 5,83% anual, calculados sobre la base de un ano de 360 dias integrado por doce meses de 30 dias cada uno. Una parte de los intereses devengados antes del 31.12.13 se pagara en efectivo y otra parte sera capitalizada. Esto significa que en la fecha de pago pertinente la porcion de los intereses que se capitaliza no se paga en efectivo sino que, por el contrario, se suma el monto de capital de los titulos Discount, y los calculos futuros de los intereses se basan en este monto de capital ajustado. Los pagos en efectivo seran efectuados en la moneda en la qu esten denominados los Bonos Discount corresp. Detalle de las tasas anuales: Desde 31.12.09 hasta el 31.12.13 (exclusive): 4,06% en efectivo y 1,77% se capitaliza. Desde 31.12.13 hasta el 31.12.33 (exclusive): efectivo 5,83%.Las fechas de pago de intereses para los titulos Discount son el 30 de junio y el 31 de diciembre de cada ano. Forma de amortizacion: En 20 cuotas semestrales el 30 de junio y el 31 de diciembre de cada ano, comenzando el 30 de junio de 2024. Cada uno de los 20 pagos semestrales incluira los montos capitalizados devengados antes de la primera fecha de amortizacion.",False,"Discount $ Ley Arg. Canje 2010","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=DIP0&csv=1"))
csvsToDownload.append(Link("NF18","Fecha de emision: 04/02/2002. Fecha de vencimiento: 04/02/2018. Moneda de emision: Pesos. Interes: Devengan intereses a una tasa anual fija del 2%. Los intereses se capitalizaron hasta el 4/9/02 y se pagan mensualmente. El primer vencimiento opero el 4/10/02. Forma de amortizacion: 60 cuotas del 0,40%, 48 cuotas del 0,60%, 47 cuotas del 0,98% y una cuota del 1,14% del capital ajustado por el coeficiente de estabilizacion de referencia (cer), venciendo la primera de ellas el 4/3/05.",False,"BOGAR 2018","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=NF18&csv=1"))
csvsToDownload.append(Link("NO20","Fecha de emision: 04/02/2002. Fecha de vencimiento: 04/10/2020. Interes: Devengan intereses a una tasa anual fija del dos por ciento (2%). Desde el 4/02/02 hasta el 4/02/05 devengan intereses sobre saldos ajustados al dos por ciento (2%) anual. Del total asi determinado, el sesenta por ciento (60%) se abono en efectivo, el diez por ciento (10%) se capitalizo y el treinta por ciento (30%) corresponde a quita. Desde el 4/02/05 hasta el 4/08/05 se capitalizo el interes sobre saldos ajustados al dos por ciento (2%) anual, arrojando un valor tecnico a esta ultima fecha de $ 1,666276902 por cada v$n. 1.-. A partir de entonces se pagaran intereses mensualmente al dos por ciento (2%) anual, habiendo operado el primer vencimiento el 4 de setiembre de 2005. El Emisor informo que los servicios de intereses del 04.09.05 y posteriores vencidos, se cancelaran con la transferencia de estos titulos a las Entidades Bancarias (BBVA Banco Frances S.A. y Banco Rio de la Plata S.A.) que suscribieron el Convenio de Conversion de Deuda Publica. Forma de amortizacion: Se efectuara en ciento cincuenta y seis (156) cuotas mensuales y consecutivas, siendo las sesenta (60) primeras cuotas equivalentes al cero coma cuarenta por ciento (0,40%); las cuarenta y ocho (48) siguientes cuotas equivalentes al cero coma sesenta por ciento (0,60%); las cuarenta y siete (47) restantes cuotas equivalentes al cero coma noventa y ocho por ciento (0,98%) y una (1) ultima cuota al uno coma catorce por ciento (1,14%), venciendo la primera de ellas el 4 de noviembre de 2007",False,"BOGAR 2020","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=NO20&csv=1"))
csvsToDownload.append(Link("PARP","Fecha de emision: 31/12/2003. Fecha de vencimiento: 31/12/2038. Moneda de emision: Pesos. Interes: Devengan por el periodo 31.12.03 al 31.03.09 (exclusive) el 0,63%; por el periodo 31.03.09 al 31.03.19 (exclusive) el 1,18%; por el periodo 31.03.19 al 31.03.29 (exclusive) el 1,77% y por el periodo 31.03.29 al 31.12.38 (exclusive) el 2,48%. Las fechas de pago son el 31 de marzo y el 30 de setiembre de cada ano y el 31 de diciembre de 2038. Se calculan sobre la base de un ano de 360 dias integrado por 12 meses de 30 dias cada uno. Los intereses devengados desde el 31.12.03 al 31.03.05, exclusive, se pagaran en efectivo en la Fecha de Liquidacion (prevista para el 01.04.05). En la primera fecha de pago de intereses siguiente a la Fecha de Liquidacion se pagaran los intereses devengados al 31.03.05, inclusive, hasta pero sin incluir esa fecha de pago. Forma de amortizacion: En 20 cuotas. Las primeras 19 cuotas semestrales se pagaran el 31 de marzo y el 30 de setiembre de cada ano, comenzando el 30.09.29 y se pagara la ultima cuota el 31.12.38. El monto de capital en circulacion sera ajustado por inflacion utilizando el Coeficiente de Estabilizacion y Referencia (CER), correspondiente al 10 dia inmediatamente anterior a la fecha de pago pertinente.",False,"Par $ Ley Arg.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PARP&csv=1"))
csvsToDownload.append(Link("PR13","Fecha de emision: 15/03/2004. Fecha de vencimiento: 15/03/2024. Moneda de emision: Pesos. Interes: Devengaran intereses sobre saldos ajustados a partir de la fecha de emision, a la tasa del 2% anual. Los intereses se capitalizaran mensualmente hasta el 15/03/2014 y se pagaran conjuntamente con las cuotas de amortizacion. Se calcularan hasta el dia de vencimiento de cada servicio, tomandose como base del calculo meses de 30 dias divididos por un ano de 360 dias (30/360). Forma de amortizacion: Se efectuara en 120 cuotas mensuales, iguales y sucesivas, equivalentes las 119 primeras al 0,83% y una ultima equivalente al 1,23% del monto emitido y ajustado por aplicacion del CER, mas los intereses capitalizados hasta el 15/03/2014. La primera cuota vencera el 15/04/2014.",False,"Consolidacion Serie 6","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PR13&csv=1"))
csvsToDownload.append(Link("RNG21","Fecha de emision: 04/02/2002. Fecha de vencimiento: 04/02/2018. Moneda de emision: Pesos. Interes: Devengan una tasa de interes fija anual del 2%, pagaderos mensualmente. El primer servicio establecido conforme a condiciones de emision es el 4.09.02. Por nota del 28.10.03, la Provincia informo que con respecto a la puesta a disposicion de los servicios devengados se ha previsto que se realice en efectivo a la fecha de perfeccionamiento del proceso de canje de estos bonos por los titulos actualmente en circulacion de la Emisora. Durante el periodo de gracia del capital, los intereses se capitalizaron hasta el mes 6 y se pagaran mensualmente con la amortizacion. Forma de amortizacion: Se efectuara en 156 cuotas mensuales y consecutivas desde el mes 37 (04.03.05) al mes 192 (04.02.18), siendo las 60 primeras cuotas equivalentes al 0,40%, las 48 siguientes al 0,60%, las 47 cuotas restantes al 0,98% y una ultima al 1,14%.",False,"Rio Negro Bogar 2","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=RNG21&csv=1"))
csvsToDownload.append(Link("TUCS1","Fecha de emision: 04/02/2002. Fecha de vencimiento: 04/02/2018. Moneda de emision: Pesos. Interes: Tasa fija del 2% nominal anual, calculandose sobre la base de un ano de 365 dias por los efectivamente transcurridos en el periodo. Los intereses se capitalizaron por el periodo 04/02/2002 al 04/09/2002 y seran pagaderos mensualmente. El primer servicio establecido conforme a condiciones de emision opero el 04/10/2002. Forma de amortizacion: En 156 cuotas mensuales y consecutivas, siendo las 60 primeras equivalentes al 0,40%, las 48 siguientes al 0,60%, las 47 restantes al 0,98% y una ultima cuota al 1,14% del monto emitido mas los intereses capitalizados hasta el 04/09/2002 y ajustado a partir de la fecha de emision conforme al CER referido en el Art. 4 del Dto. nac. N 214/02, tomandose en consideracion el indice del quinto dia habil anterior a la fecha de cada vencimiento. El primer servicio de amortizacion vencera el 04/03/05.",False,"Tucuman Consadep Serie 1","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=TUCS1&csv=1"))
csvsToDownload.append(Link("A2M6","Moneda de emision: Pesos. Fecha de Emision: 31/03/2015. Fecha Vencimiento: 31/03/2016. Interes: Se devengaran a una tasa variable, pagaderos trimestralmente los dias 31 de marzo, 30 de junio, 30 de septiembre y 31 de diciembre de cada ano hasta el vencimiento, calculada sobre la base de los dias efectivamente transcurridos y la cantidad exacta de dias que tiene cada ano (actual/actual). El primer servicio sera el 30.06.15. La tasa variable sera el equivalente al promedio aritmetico simple de las tasas de interes implicitas de Letras Internas del BCRA en pesos, publicadas por dicha entidad en sus Comunicados de resultados de licitaciones, para el plazo mas proximo a noventa (90) dias, que no podra ser inferior a sesenta (60) dias ni superior a ciento veinte (120) dias. Si esas tasas no se publican, entonces sera BADLAR privada + 365 puntos basicos. Forma de amortizacion: En una cuota al vencimiento. Renta: Tasa Var.=26.10%",False,"BONAC MARZO 2016","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=A2M6&csv=1"))
csvsToDownload.append(Link("AS16","Moneda de emision: Pesos. Fecha de Emision: 29/09/2014. Fecha Vencimiento: 29/09/2016. Interes: Devengan una tasa equivalente a la Tasa BADLAR Privada (promedio aritmetico simple de las tasas de interes para depositos a plazo fijo de mas de un millon de pesos - BADLAR promedio bancos privados-, publicadas por el Banco Central de la Republica Argentina desde el 10 dia habil anterior al inicio de cada servicio financiero hasta el 10 dia habil anterior al vencimiento del servicio financiero pertinente) mas 200 puntos basicos, pagaderos trimestralmente los dias 29 de marzo, 29 de junio, 29 de septiembre y 29 de diciembre de cada ano. El primer servicio vencera el 29.12.14. Los intereses seran calculados sobre la base de los dias efectivamente transcurridos y la cantidad exacta de dias que tiene cada ano. Forma de amortizacion: En una cuota al vencimiento.",False,"BONAR 2016","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AS16&csv=1"))
csvsToDownload.append(Link("PR15","Fecha de emision: 04/01/2010. Fecha de vencimiento: 04/10/2022. Moneda de emision: Pesos. Interes: Tasa nominal anual resultante de promedio simple de tasas de interes pasivas publicadas por BCRA de los ultimos 20 dias habiles anteriores a los 5 dias habiles previos al inicio de c/periodo de interes, informadas mediante encuesta diaria a bancos privados para plazos fijos en $ de 30 a 35 dias, de montos mayores o iguales a $ 1.000.000.- - BADLAR-. Desde fecha de emision y hasta el 04/04/14, inclusive, los intereses se capitalizan trimestralmente y se pagaran con las cuotas de amortizacion. A partir del 04/07/14 los intereses se pagaran trimestralmente en efvo., calculados s/base de dias efectivamente transcurridos y la cantidad exacta de dias que tiene c/ano. Fechas de pago: 4 de enero, 4 de abril, 4 de julio y 4 de octubre de c/ano, siendo la primera fecha depago el 04/07/14. Cuando el vto. de un cupon no fuere un dia habil, la fecha de pago sera el dia habil inmediato posterior a la fecha de vto. original, y el calculo del mismo se realizara hasta la fecha de efectivo pago. Forma de amortizacion: 14 cuotas trimestrales y consecutivas, equivalentes las 2 primeras al 5%, las 11 siguientes al 7% y una ultima al 13%. La primeracuota vencera el 4 de julio de 2019.",False,"Consolidacion Serie 8","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PR15&csv=1"))
csvsToDownload.append(Link("AA17","Fecha de emision: 17/04/2007. Fecha de vencimiento: 17/04/2017. Moneda de emision: Dolares. Interes: Devengan semestralmente una tasa fija del 7% nominal anual, calculada sobre la base de un ano de 360 dias, integrado por 12 meses de 30 dias cada uno. Las fechas de pago de intereses seran el 17 de abril y el 17 de octubre de cada ano. Renta: Tasa fija=7.00%.",False,"BONAR X","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AA17&csv=1"))
csvsToDownload.append(Link("AN18","Fecha de Emision: 29/11/2011. Fecha de vencimiento: 29/11/2018. Moneda de emision: Dolares. Interes: Tasa fija del 9% nominal anual, pagadera semestralmente el 29 de mayo y el 29 de noviembre de cada ano, siendo la primera fecha de pago el 29/05/12. Los intereses se calculan sobre la base de un ano de 360 dias integrado por 12 meses de 30 dias cada uno. Cuando el vto. de un servicio no fuere un dia habil, la fecha de pago sera el dia habil inmediato posterior a la fecha de vto. original, y el calculo del mismo se realizara hasta la fecha de vto. original. Forma de amortizacion: Integra al vencimiento.",False,"Bonar 2018","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AN18&csv=1"))
csvsToDownload.append(Link("AY24","Vencimiento: 07/05/2024. Interes: Devengan una tasa del 8,75% nominal anual, pagaderos semestralmente los dias 7 de mayo y 7 de noviembre de cada ano, calculados sobre la base de un ano de 360 dias integrado por 12 meses de 30 dias cada uno. La primera fecha de pago sera el 7 de noviembre de 2014. Cuando el vencimiento de un servicio no fuere un dia habil, la fecha de pago sera el dia habil inmediato posterior a la fecha de vencimiento original, pero el calculo del mismo se realizara hasta la fecha de vencimiento original. Amortizacion: En seis cuotas anuales y consecutivas, comenzando el 5  ano posterior a la fecha de emision (2019). Las primeras cinco cuotas seran del 16,66% y la ultima del 16,70%.",False,"BONAR 2024","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=AY24&csv=1"))
csvsToDownload.append(Link("BADER","Fecha de emision: 17/07/2013. Fecha de vencimiento: 17/07/2016. Moneda de emision: Dolares. Interes: Devengan semestralmente una tasa fija del 4% nominal anual, calculada sobre la base de un ano de 360 dias, integrado por 12 meses de 30 dias cada uno. Las fechas de pago de intereses seran el 17 de enero y 17 de julio de cada ano. La primera fecha de pago de interes sera el 17.01.14. Moneda de pago: dolares estadounidenses. Forma de amortizacion: Integra al vencimiento.",False,"BAADE","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BADER&csv=1"))
csvsToDownload.append(Link("BDED","Fecha de emision: 01/12/2005. Fecha de vencimiento: 15/04/2017. Moneda de emision: Dolares. Interes: Devengaran intereses calculados sobre la base de un ano de 360 dias integrado por 12 meses de 30 dias cada uno, desde el 1.12.05, a una tasa fija anual del 9,25%. Las fechas de pago de intereses seran el 15 de abril y el 15 de octubre de cada ano, comenzando a partir del 15.04.06. Devengan intereses a una tasa fija del 9,25%. Forma de amortizacion: En 10 cuotas semestrales iguales y consecutivas, equivalentes al 10% del capital, pagaderas el 15 de abril y 15 de octubre de cada ano, comenzando el 15 de octubre de 2012.",False,"Bs.As. Discount Largo Plazo U$S","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BDED&csv=1"))
csvsToDownload.append(Link("BP18","Fecha de emision: 31/10/2006. Fecha de vencimiento: 14/09/2018. Moneda de emision: Dolares. Interes:	Devengaran desde la fecha de emision, una tasa fija del 9,375% nominal anual, pagadera el dia 14 de marzo y stiembre de cada ano, comenzando el 14 de marzo del 2007. Forma de amortizacion: Total al vencimiento.",False,"Buenos Aires 2018","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BP18&csv=1"))
csvsToDownload.append(Link("BP21","Fecha de emision: 26/01/2011. Fecha de vencimiento: 26/01/2021. Moneda de emision: Dolares. Interes: Devengan desde la fecha de emision una tasa fija anual del 10,875% pagadera semestralmente el 26 de enero y el 26 de julio de cada ano, comenzando el 26 de julio de 2011. Los intereses se calcularan sobre la base de un ano de 360 dias integrado por 12 meses de 30 dias cada uno. Forma de amortizacion: En tres (3) cuotas anuales, las dos (2) primeras del 33,33% y la ultima del 33,34%, con vencimientos el 26 de enero de 2019, el 26 de enero de 2019, el 26 de enero de 2020 y el 26 de enero de 2021.",False,"Buenos Aires 2021","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BP21&csv=1"))
csvsToDownload.append(Link("BP28","Fecha de emision: 18/04/2007. Fecha de vencimiento: 18/04/2028. Moneda de emision: Dolares. Interes: Se devengan desde la fecha de emision a una tasa fija anual del 9,625%, pagadera semestralmente el 18 de abril y el 18 de octubre de cada ano, comenzando el 18 de octubre de 2007. Los intereses se calcularan sobre la base de un ano de 360 dias integrado por 12 meses de 30 dias cada uno. Forma de amortizacion: En tres cuotas anuales y consecutivas equivalentes al 33,33% las dos primeras y al 33,34% la ultima, pagaderas el 18 de abril de 2026, 2027 y 2028.",False,"Buenos Aires 2028","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BP28&csv=1"))
csvsToDownload.append(Link("BPLD","Fecha de emision: 01/12/2005. Fecha de vencimiento: 15/05/2035. Moneda de emision: Dolares. Interes: Devengaran intereses sobre la base de un ano de 360 dias integrado por 12 meses de 30 dias cada uno, desde el 01.12.05, de la siguiente manera: Periodo Tasa Anual 01.12.2005 hasta el 15.11.2007 2% 16.11.2007 hasta el 15.11.2009 3% 16.11.2009 hasta el 15.05.2035 4% Los intereses seran pagados cada 15 de mayo y 15 de noviembre. El primer servicio operara el 15.05.06. Forma de amortizacion: En 30 cuotas semestrales y consecutivas, equivalentes las 29 primeras al 3,33% del capital y la ultima al 3,43%. Los servicios operaran el 15 de mayo y 15 de noviembre, comenzando a partir del 15.11.2020.",False,"Bs.As. Par Largo Plazo u$s","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BPLD&csv=1"))
csvsToDownload.append(Link("BPMD","Fecha de emision: 01/12/2005. Fecha de vencimiento: 01/05/2020. Moneda de emision: Dolares. Interes: Devengaran intereses calculados sobre la base de un ano de 360 dias integrado por 12 meses de 30 dias cada uno, desde el 01.12.05, de acuerdo al siguiente detalle: Periodo Tasa Anual 01.12.2005 hasta el 01.11.2009 1% 02.11.2009 hasta el 01.11.2013 2% 02.11.2013 hasta el 01.11.2017 3% 02.11.2017 hasta el 01.05.2020 4% Las fechas de pago de intereses seran el 1 de mayo y el 1 de noviembre de cada ano, comenzando a partir del 01.05.06. Forma de amortizacion: En 6 cuotas semestrales y consecutivas el 1 de mayo y el 1 de noviembre de cada ano, comenzando el 1 de noviembre de 2017, equivalentes las 5 primeras al 16,666% del capital y la ultima al 16,67%.",False,"Bs.As. Par Mediano Plazo u$s","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=BPMD&csv=1"))
csvsToDownload.append(Link("DIA0","Fecha de emision: 31/12/2003. Fecha de vencimiento: 31/12/2033. Moneda de emision: Dolares. Interes: Los titulos devengaran intereses al 8,28% anual, calculados sobre la base de un ano de 360 dias integrado por doce meses de 30 dias cada uno. Una parte de los intereses devengados antes del 31.12.13 se pagara en efectivo y otra parte sera capitalizada. Esto significa que en la fecha de pago pertinente la porcion de los intereses que se capitaliza no se paga en efectivo sino que, por el contrario, se suma el monto de capital de los titulos Discount, y los calculos futuros de los intereses se basan en este monto de capital ajustado. Los pagos en efectivo seran efectuados en la moneda en la qu esten denominados los Bonos Discount corresp. Detalle de las tasas anuales: Desde 31.12.09 hasta el 31.12.13 (exclusive): 5,77% en efectivo y 2,51% se capitaliza. Desde 31.12.13 hasta el 31.12.33 (exclusive): efectivo 8,28%.Las fechas de pago de intereses para los titulos Discount son el 30 de junio y el 31 de diciembre de cada ano. Forma de amortizacion: 20 cuotas semestrales iguales el 30 de junio y el 31 de diciembre de cada ano, a partir del 30/06/2024. Cada uno de los 20 pagos semestrales incluira los montos capitalizados devengados antes de la primera fecha de amortizacion.",False,"Discount U$S Ley Arg.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=DIA0&csv=1"))
csvsToDownload.append(Link("DICA","Fecha de emision: 31/12/2003. Fecha de vencimiento: 31/12/2033. Moneda de emision: Dolares. Interes: Los titulos devengaran intereses al 8,28% anual, calculados sobre la base de un ano de 360 dias integrado por doce meses de 30 dias cada uno, desde el 31.12.03 inclusive, hasta pero sin incluir el 31.12.33. Una parte de los intereses devengados antes del 31.12.13 se pagara en efectivo y otra parte sera capitalizada. Esto significa que en la fecha de pago pertinente la porcion de los intereses que se capitaliza no se paga en efectivo sino que, por el contrario, se suma el monto de capital de los titulos Discount, y los calculos futuros de los intereses se basan en este monto de capital ajustado. Porcion que se capitalizara: Desde 31.12.03 hasta el 31.12.08 (exclusive): 4,31%; Desde 31.12.08 hasta el 31.12.13 (exclusive): 2,51%. Las fechas de pago de intereses para los titulos Discount son el 30 de junio y el 31 de diciembre de cada ano. La porcion de los intereses que deberia haberse pagado en efectivo el 30.06.04 y 31.12.04 sera pagada en efectivo en la Fecha de Liquidacion. Forma de amortizacion: En 20 cuotas semestrales el 30 de junio y el 31 de diciembre de cada ano, comenzando el 30 de junio de 2024. Cada uno de los 20 pagos semestrales incluira los montos capitalizados devengados antes de la primera fecha de amortizacion. Las cuotas de los titulos denominados en dolares estadounidenses seran iguales.",False,"Discount U$S Ley Arg.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=DICA&csv=1"))
csvsToDownload.append(Link("DICY","Fecha de emision: 31/12/2003. Fecha de vencimiento: 31/12/2033. Moneda de emision: Dolares. Interes: Los titulos devengaran intereses al 8,28% anual, calculados sobre la base de un ano de 360 dias integrado por doce meses de 30 dias cada uno, desde el 31.12.03 inclusive, hasta pero sin incluir el 31.12.33. Una parte de los intereses devengados antes del 31.12.13 se pagara en efectivo y otra parte sera capitalizada. Esto significa que en la fecha de pago pertinente la porcion de los intereses que se capitaliza no se paga en efectivo sino que, por el contrario, se suma el monto de capital de los titulos Discount, y los calculos futuros de los intereses se basan en este monto de capital ajustado. Porcion que se capitalizara: Desde 31.12.03 hasta el 31.12.08 (exclusive): 4,31%; Desde 31.12.08 hasta el 31.12.13 (exclusive): 2,51%. Las fechas de pago de intereses para los titulos Discount son el 30 de junio y el 31 de diciembre de cada ano. La porcion de los intereses que deberia haberse pagado en efectivo el 30.06.04 y 31.12.04 sera pagada en efectivo en la Fecha de Liquidacion. Forma de amortizacion: En 20 cuotas semestrales el 30 de junio y el 31 de diciembre de cada ano, comenzando el 30 de junio de 2024. Cada uno de los 20 pagos semestrales incluira los montos capitalizados devengados antes de la primera fecha de amortizacion. Las cuotas de los titulos denominados en dolares estadounidenses seran iguales.",False,"Discount U$S Ley N.Y.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=DICY&csv=1"))
csvsToDownload.append(Link("DIY0","Fecha de emision: 31/12/2003. Fecha de vencimiento: 31/12/2033. Moneda de emision: Dolares. Interes: Los titulos devengaran intereses al 8,28% anual, calculados sobre la base de un ano de 360 dias integrado por doce meses de 30 dias cada uno. Una parte de los intereses devengados antes del 31.12.13 se pagara en efectivo y otra parte sera capitalizada. Esto significa que en la fecha de pago pertinente la porcion de los intereses que se capitaliza no se paga en efectivo sino que, por el contrario, se suma el monto de capital de los titulos Discount, y los calculos futuros de los intereses se basan en este monto de capital ajustado. Detalle de las tasas anuales: Desde 31.12.09 hasta el 31.12.13 (exclusive): 5,77% en efectivo y 2,51% se capitaliza. Desde 31.12.13 hasta el 31.12.33 (exclusive): 8,28% en efectivo. Las fechas de pago de intereses para los titulos Discount son el 30 de junio y el 31 de diciembre de cada ano. Renta: Tasa fija=8.28%.",False,"Discount U$S Ley NY Canje 2010","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=DIY0&csv=1"))
csvsToDownload.append(Link("GJ17","Fecha de emision: 02/06/2010. Fecha de vencimiento: 02/06/2017. Moneda de emision: Dolares. Interes: 8,75% anual, desde la Fecha de Liquidacion Inicial (02/06/10), pagaderos semestralmente por periodo vencido y calculados sobre la base de un ano de 360 dias integrado por 12 meses de 30 dias cadauno. Las fechas de pago son el 2 de junio y el 2 de diciembre de cada ano, comenzando el 02.12.10. Renta: Tasa fija=8.750",False,"Global 2017","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=GJ17&csv=1"))
csvsToDownload.append(Link("PAA0","Fecha de emision: 31/12/2003. Fecha de vencimiento: 31/12/2038. Moneda de emision: Dolares. Interes: Semestral por periodo vencido, calculado sobre la base de un ano de 360 dias, a las siguientes tasas anuales: 30.09.09 al 31.03.19 exclusive: 2,50%; 31.03.19 al 31.03.29 exclusive: 3,75% y del 31.03.29 al 31.12.38 exclusive: 5,25%. Fechas de pago: 31 de marzo y 30 de septiembre de cada ano y el 31 de diciembre de 2038. Forma de amortizacion: En 20 cuotas semestrales iguales. Las primeras 19 se pagaran el 31 de marzo y el 30 de septiembre de cada ano, comenzando el 30.09.29 y la ultima cuota el 31.12.38 ",False,"Par U$S Ley Arg. (Canje 2010)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PAA0&csv=1"))
csvsToDownload.append(Link("PARA","Fecha de emision: 31/12/2003. Fecha de vencimiento: 31/12/2038. Moneda de emision: Dolares. Interes: Devengan por el periodo 31.12.03 al 31.03.09 (exclusive) el 1,33%; por el periodo 31.03.09 al 31.03.19 (exclusive) el 2,50%; por el periodo 31.03.19 al 31.03.29 (exclusive) el 3,75% y por el periodo 31.03.29 al 31.12.38 (exclusive) el 5,25%. Las fechas de pago son el 31 de marzo y el 30 de setiembre de cada ano y el 31 de diciembre de 2038. Se calculan sobre la base de un ano de 360 dias integrado por 12 meses de 30 dias cada uno. Los intereses devengados desde el 31.12.03 al 31.03.05, exclusive, se pagaran en efectivo en la Fecha de Liquidacion (prevista para el 01.04.05). En la primera fecha de pago de intereses siguiente a la Fecha de Liquidacion se pagaran los intereses devengados al 31.03.05, inclusive, hasta pero sin incluir esa fecha de pago. Forma de amortizacion: En 20 cuotas iguales. Las primeras 19 cuotas semestrales se pagaran el 31 de marzo y el 30 de setiembre de cada ano, comenzando el 30.09.29 y se pagara la ultima cuota el 31.12.38.",False,"Par U$S Ley Arg.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PARA&csv=1"))
csvsToDownload.append(Link("PARY","Fecha de emision: 31/12/2003. Fecha de vencimiento: 31/12/2038. Moneda de emision: Dolares. Interes: Devengan por el periodo 31.12.03 al 31.03.09 (exclusive) el 1,33%; por el periodo 31.03.09 al 31.03.19 (exclusive) el 2,50%; por el periodo 31.03.19 al 31.03.29 (exclusive) el 3,75% y por el periodo 31.03.29 al 31.12.38 (exclusive) el 5,25%. Las fechas de pago son el 31 de marzo y el 30 de setiembre de cada ano y el 31 de diciembre de 2038. Se calculan sobre la base de un ano de 360 dias integrado por 12 meses de 30 dias cada uno. Los intereses devengados desde el 31.12.03 al 31.03.05, exclusive, se pagaran en efectivo en la Fecha de Liquidacion (prevista para el 01.04.05). En la primera fecha de pago de intereses siguiente a la Fecha de Liquidacion se pagaran los intereses devengados al 31.03.05, inclusive, hasta pero sin incluir esa fecha de pago. Forma de amortizacion: En 20 cuotas. Las primeras 19 cuotas semestrales se pagaran el 31 de marzo y el 30 de septiembre de cada ano, comenzando el 30.09.29, y pagara la ultima cuota el 31.12.38.",False,"Par U$S Ley N.Y.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PARY&csv=1"))
csvsToDownload.append(Link("PAY0","Fecha de emision: 31/12/2003. Fecha de vencimiento: 31/12/2038. Moneda de emision: Dolares. Interes: Semestral por periodo vencido, calculado sobre la base de un ano de 360 dias, a las siguientes tasas anuales: 30.09.09 al 31.03.19 exclusive: 2,50%; 31.03.19 al 31.03.29 exclusive: 3,75% y del 31.03.29 al 31.12.38 exclusive: 5,25%. Fechas de pago: 31 de marzo y 30 de septiembre de cada ano y el 31 de diciembre de 2038. Forma de amortizacion: En 20 cuotas semestrales iguales. Las primeras 19 se pagaran el 31 de marzo y el 30 de septiembre de cada ano, comenzando el 30.09.29 y la ultima cuota el 31.12.38.",False,"Par U$S Ley N.Y. (Canje 2010)","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=PAY0&csv=1"))
csvsToDownload.append(Link("TVPA","Fecha de emision: 31/12/2003. Fecha de vencimiento: 15/12/2035. Moneda de emision: Dolares. Interes: En cada fecha de pago, los tenedores de estos titulos tendran derecho a recibir pagos por un monto igual al Excedente del PBI Disponible para el correspondiente ano de referencia, multiplicado por el monto teorico de los titulos. El Excedente del PBI Disponible se calculara de la siguiente forma: (0,05 x Excedente PBI -segun se define en el Prospecto-) x Coeficiente unidad de moneda. A los efectos de realizar los pagos respecto de estos titulos, el Excedente de PBI Disponible se convertira a la moneda de pago pertinente, utilizando el tipo de cambio promedio en el mercado libre del peso frente a la moneda de pago aplicable durante los 15 dias calendario anteriores al 31 de diciembre de cada ano de referencia pertinente. ",False,"Cupones PBI U$S Ley Argentina","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=TVPA&csv=1"))
csvsToDownload.append(Link("TVPE","Fecha de emision: 31/12/2003. Fecha de vencimiento: 15/12/2035. Moneda de emision: Euro. Interes: En cada fecha de pago, los tenedores de estos titulos tendran derecho a recibir pagos por un monto igual al Excedente del PBI Disponible para el correspondiente ano de referencia, multiplicado por el valor nocional de los titulos. El Excedente del PBI Disponible se calculara de la siguiente forma: (0,05 x Excedente PBI -segun se define en el Prospecto-) x Coeficiente unidad de moneda (0 ,015387) A los efectos de realizar los pagos respecto de estos titulos, el Excedente de PBI Disponible se convertira en Euros, utilizando el tipo de cambio promedio en el mercado libre del peso frente a esa moneda durante los 15 dias calendario anteriores al 31 de diciembre de cada ano de referencia pertinente. ",False,"Cupones PBI en Euros","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=TVPE&csv=1"))
csvsToDownload.append(Link("TVPP","Fecha de emision: 31/12/2003. Fecha de vencimiento: 15/12/2035. Moneda de emision: Pesos. Interes: En cada fecha de pago, los tenedores de estos titulos tendran derecho a recibir pagos por un monto igual al Excedente del PBI Disponible para el correspondiente ano de referencia, multiplicado por el monto teorico de los titulos. El Excedente del PBI Disponible se calculara de la siguiente forma: (0,05 x Excedente PBI -segun se define en el Prospecto-) x Coeficiente unidad de moneda. A los efectos de realizar los pagos respecto de estos titulos, el Excedente de PBI Disponible se convertira a la moneda de pago pertinente, utilizando el tipo de cambio promedio en el mercado libre del peso frente a la moneda de pago aplicable durante los 15 dias calendario anteriores al 31 de diciembre de cada ano de referencia pertinente.",False,"Cupones PBI en Pesos","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=TVPP&csv=1"))
csvsToDownload.append(Link("TVPY","Fecha de emision: 31/12/2003. Fecha de vencimiento: 15/12/2035. Moneda de emision: Dolares. Interes: En cada fecha de pago, los tenedores de estos titulos tendran derecho a recibir pagos por un monto igual al Excedente del PBI Disponible para el correspondiente ano de referencia, multiplicado por el monto teorico de los titulos. El Excedente del PBI Disponible se calculara de la siguiente forma: (0,05 x Excedente PBI -segun se define en el Prospecto-) x Coeficiente unidad de moneda. A los efectos de realizar los pagos respecto de estos titulos, el Excedente de PBI Disponible se convertira a la moneda de pago pertinente, utilizando el tipo de cambio promedio en el mercado libre del peso frente a la moneda de pago aplicable durante los 15 dias calendario anteriores al 31 de diciembre de cada ano de referencia pertinente. ",False,"Cupones PBI U$S Ley N.Y.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=TVPY&csv=1"))
csvsToDownload.append(Link("TVY0","Fecha de emision: 31/12/2003. Fecha de vencimiento: 15/12/2035. Moneda de emision: Dolares. Interes: En cada fecha de pago, los tenedores de estos titulos tendran derecho a recibir pagos por un monto igual al Excedente del PBI Disponible para el correspondiente ano de referencia, multiplicado por el monto teorico de los titulos. El Excedente del PBI Disponible se calculara de la siguiente forma: (0,05 x Excedente PBI -segun se define en el Prospecto-) x Coeficiente unidad de moneda. A los efectos de realizar los pagos respecto de estos titulos, el Excedente de PBI Disponible se convertira a la moneda de pago pertinente, utilizando el tipo de cambio promedio en el mercado libre del peso frente a la moneda de pago aplicable durante los 15 dias calendario anteriores al 31 de diciembre de cada ano de referencia pertinente.",False,"Cupones PBI U$S 2010 Ley N.Y.","http://www.ravaonline.com/v2/empresas/precioshistoricos.php?e=TVY0&csv=1"))

bonds =[]
finalBonds = []

for oneLink in csvsToDownload:
    print("Descargando:",oneLink.ticker)
    webpage = urllib.request.urlopen(oneLink.url)
    datareader = csv.reader(io.TextIOWrapper(webpage))
    currentBond = InvertarBond(oneLink.ticker,oneLink.name)
    currentBond.description = oneLink.description
    currentBond.dollarLinked = oneLink.dollar_linked
    currentBond.tradingSessions = []
    for row in datareader:
        if row[4] != "cierre":
            currentBond.tradingSessions.append(InvertarTradingSession(float(row[4]),float(row[1]),float(row[2]),float(row[3]),row[0],int(row[5])))
    bonds.append(currentBond)

for oneBond in bonds:

    print("Procesando:",oneBond.ticker)
    myInvertarBond = InvertarBond(oneBond.ticker,oneBond.name)
    currentBond = oneBond
    myInvertarBond.description = oneBond.description
    myInvertarBond.dollarLinked = oneBond.dollarLinked
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

        # Only inserts last 3 years but we take in consideration for the calculations the last 5 years
        if datetime.strptime(currentTradingSession.tradingDate,"%Y-%m-%d").date()>=date.today() - timedelta(days=1085):
            myInvertarBond.tradingSessions.append(currentTradingSession)

    finalBonds.append(myInvertarBond)

# Open connection to API
conn = requests.request('POST', url=login_url, headers=headers, data=json.dumps(credentials))
session_cookie = conn.cookies

for oneInvertarBond in finalBonds:
    macd_macd_line = []
    last_ema_9 = 0.0
    index = 0
    for oneInvertarTradingSession in oneInvertarBond.tradingSessions:
        oneInvertarTradingSession.tradingDate = int(time.mktime(datetime.strptime(oneInvertarTradingSession.tradingDate,"%Y-%m-%d").timetuple())) * 1000
        oneInvertarTradingSession.adjClosingPrice = 0.0
        index = index +1
        if index == len(oneInvertarBond.tradingSessions)-1:
            oneInvertarBond.lastTradingPrice = oneInvertarTradingSession.closingPrice
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

    #oneInvertarBond.tradingSessions = map(lambda x: x.__dict__, oneInvertarBond.tradingSessions)

    # La linea de arriba deberia andar pero no lo hace, por eso la horripilancia de abajo
    json_ts = []
    for ts in oneInvertarBond.tradingSessions:
        json_ts.append(ts.__dict__)
    oneInvertarBond.tradingSessions = json_ts

    r =requests.request('POST', url=store_url, headers=headers, cookies=session_cookie, data=json.dumps(oneInvertarBond.__dict__))

    if r.status_code == 200:
        print("Cargado:",oneInvertarBond.ticker)
    else:
        print("Error en la carga del bono {}: {}".format(oneInvertarBond.ticker, r.content))

print("Fin de Carga de Bonos")