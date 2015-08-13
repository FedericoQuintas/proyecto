package com.proyecto.asset.domain;

import com.proyecto.common.currency.InvertarCurrency;

public class Stock extends Asset {
	private String industry;
	private boolean isLeader = false;
	
	public Stock(Long id, String description, String ticker,
			InvertarCurrency invertarCurrency) {
		super(id, description, ticker, invertarCurrency);
	}
}
