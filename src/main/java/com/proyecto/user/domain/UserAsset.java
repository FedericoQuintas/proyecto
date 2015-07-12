package com.proyecto.user.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.proyecto.user.exception.InvalidUserAssetTransactionException;

public class UserAsset {

	private Long assetId;
	private Float cumulativePayments; // Pueden ser: Dividendos (Acciones),

	// Pagos de Amortizaci�n o Renta
	// (Bonos), o Redondeos en caso de Split
	// (Acciones)
	private List<Transaction> transactions = new ArrayList<Transaction>();
	private Long ownedQuantity = new Long(0);

	public UserAsset() {
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Long getAssetId() {
		return assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}

	public Transaction obtainLastTransaction() {

		Transaction lastTransaction = null;

		if (!getTransactions().isEmpty()) {

			lastTransaction = calculateLastTransaction();

		}

		return lastTransaction;

	}

	private Transaction calculateLastTransaction() {
		Transaction firstTransaction = getTransactions().get(0);
		Date lastDate = firstTransaction.getTradingDate();
		Transaction lastTransaction = firstTransaction;

		for (Transaction transaction : getTransactions()) {
			if (transaction.getTradingDate().after(lastDate)) {
				lastDate = transaction.getTradingDate();
				lastTransaction = transaction;
			}

		}
		return lastTransaction;
	}

	public void addTransactions(Transaction transaction)
			throws InvalidUserAssetTransactionException {
		this.transactions.add(transaction);
		calculateQuantity(transaction);
	}

	private void calculateQuantity(Transaction transaction)
			throws InvalidUserAssetTransactionException {
		Long newOwnedQuantity = calculateNewOwnedQuantity(transaction);
		if (newOwnedQuantity >= 0) {
			this.ownedQuantity = newOwnedQuantity;
		} else {
			throw new InvalidUserAssetTransactionException();

		}
	}

	private Long calculateNewOwnedQuantity(Transaction transaction) {
		Long newOwnedQuantity = new Long(0);

		if (transaction.getType().equals(TransactionType.PURCHASE)) {
			newOwnedQuantity = this.ownedQuantity + transaction.getQuantity();
		} else {
			newOwnedQuantity = this.ownedQuantity - transaction.getQuantity();
		}
		return newOwnedQuantity;
	}

	public Long getOwnedQuantity() {
		return ownedQuantity;
	}

	public Float getCumulativePayments() {
		return cumulativePayments;
	}

	public void setCumulativePayments(Float cumulativePayments) {
		this.cumulativePayments = cumulativePayments;
	}
}
