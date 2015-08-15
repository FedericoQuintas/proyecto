package com.proyecto.user.domain.factory;

import java.util.Date;

import com.proyecto.rest.resource.user.dto.TransactionDTO;
import com.proyecto.user.domain.Transaction;

public class TransactionFactory {

	public static Transaction create(TransactionDTO transactionDTO) {

		Transaction transaction = new Transaction(
				transactionDTO.getPricePaid(), transactionDTO.getQuantity(),
				new Date(transactionDTO.getTradingDate()),
				transactionDTO.getAssetId(), transactionDTO.getType());

		return transaction;
	}

}
