package com.proyecto.user.domain;

import java.util.ArrayList;
import java.util.List;

public class UserAsset {
	
	private Long id;
	private long asset_id;
	private float cumulativePayments; // Pueden ser: Dividendos (Acciones), Pagos de Amortizaci�n o Renta (Bonos), o Redondeos en caso de Split (Acciones)
	private List<Transaction> transactions = new ArrayList<Transaction>();
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public List<Transaction> getTransactions() {
		return transactions;
	}
	
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public float getCumulativePayments() {
		return cumulativePayments;
	}

	public void setCumulativePayments(float cumulativePayments) {
		this.cumulativePayments = cumulativePayments;
	}

	public long getAsset_id() {
		return asset_id;
	}

	public void setAsset_id(long asset_id) {
		this.asset_id = asset_id;
	}
	
}
