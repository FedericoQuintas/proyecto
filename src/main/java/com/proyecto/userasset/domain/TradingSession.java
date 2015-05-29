package com.proyecto.userasset.domain;

import java.util.Date;
import java.util.List;

public class TradingSession {
		protected Date tradingDate;
		protected Long openPrice;
		protected Long closePrice;
		protected Long lowestPrice;
		protected Long highestPrice;
		
		//validar con la UI, por ahi en el DTO agregamos un metodo que devuelva si es positivo o negativo(para candlestick)
		protected Long change; 		
		protected int volume;
		protected Long changePercentage;
		protected Long dividendOrYield; //dividends para acciones, yield para bonos
		
		public Date getTradingDate() {
			return tradingDate;
		}
		public void setTradingDate(Date tradingDate) {
			this.tradingDate = tradingDate;
		}
		public Long getOpenPrice() {
			return openPrice;
		}
		public void setOpenPrice(Long openPrice) {
			this.openPrice = openPrice;
		}
		public Long getClosePrice() {
			return closePrice;
		}
		public void setClosePrice(Long closePrice) {
			this.closePrice = closePrice;
		}
		public Long getLowestPrice() {
			return lowestPrice;
		}
		public void setLowestPrice(Long lowestPrice) {
			this.lowestPrice = lowestPrice;
		}
		public Long getHighestPrice() {
			return highestPrice;
		}
		public void setHighestPrice(Long highestPrice) {
			this.highestPrice = highestPrice;
		}
		public Long getChange() {
			return change;
		}
		public void setChange(Long change) {
			this.change = change;
		}
		public int getVolume() {
			return volume;
		}
		public void setVolume(int volume) {
			this.volume = volume;
		}
		public Long getChangePercentage() {
			return changePercentage;
		}
		public void setChangePercentage(Long changePercentage) {
			this.changePercentage = changePercentage;
		}
		public Long getDividendOrYield() {
			return dividendOrYield;
		}
		public void setDividendOrYield(Long dividendOrYield) {
			this.dividendOrYield = dividendOrYield;
		}


}
