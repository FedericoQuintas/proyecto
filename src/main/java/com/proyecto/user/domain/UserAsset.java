package com.proyecto.user.domain;

import java.util.ArrayList;
import java.util.List;

public class UserAsset {

	private Long id;
	private Long assetId;
	private float cumulativePayments; // Pueden ser: Dividendos (Acciones),
										// Pagos de Amortizaci�n o Renta
										// (Bonos), o Redondeos en caso de Split
										// (Acciones)
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

	public long getAssetId() {
		return assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}

}
