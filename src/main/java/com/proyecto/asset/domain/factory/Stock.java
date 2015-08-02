package com.proyecto.asset.domain.factory;

import com.proyecto.asset.domain.Asset;
import com.proyecto.common.currency.InvertarCurrency;

public class Stock extends Asset {
	private String industry;
	private boolean isLeader = false;
	
	public Stock(Long id, String description, String ticker,
			InvertarCurrency invertarCurrency) {
		super(id, description, ticker, invertarCurrency);
	}
}
