package com.proyecto.user.domain.factory;

import com.proyecto.rest.resource.user.dto.TransactionDTO;
import com.proyecto.user.domain.Transaction;

public class TransactionDTOFactory {

	public static TransactionDTO create(Transaction transaction) {
		TransactionDTO transactionDTO = new TransactionDTO();

		transactionDTO.setPricePaid(transaction.getPricePaid());
		transactionDTO.setTradingDate(transaction.getTradingDate());
		transactionDTO.setQuantity(transaction.getQuantity());
		transactionDTO.setAssetId(transaction.getAssetId());
		transactionDTO.setCurrency(transaction.getCurrency());
		transactionDTO.setType(transaction.getType());

		return transactionDTO;
	}

}
