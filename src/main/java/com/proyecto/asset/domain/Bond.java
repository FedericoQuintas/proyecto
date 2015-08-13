package com.proyecto.asset.domain;

import com.proyecto.common.currency.InvertarCurrency;

public class Bond extends Asset {
	private boolean isPesified = true;
	
	public Bond(Long id, String description, String ticker,
			InvertarCurrency invertarCurrency) {
		super(id, description, ticker, invertarCurrency);
		// TODO Auto-generated constructor stub
	}
}
