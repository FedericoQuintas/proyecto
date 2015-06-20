package com.proyecto.user.domain.factory;

import com.proyecto.rest.resource.user.dto.TransactionDTO;
import com.proyecto.user.domain.Transaction;

public class TransactionDTOFactory {

	public static TransactionDTO create(Transaction transaction){
		TransactionDTO transactionDTO = new TransactionDTO();
		
		transactionDTO.setId(transaction.getId());
		transactionDTO.setPriceSold(transaction.getPriceSold());
		transactionDTO.setPricePaid(transaction.getPricePaid());
		transactionDTO.setTradingDate(transaction.getTradingDate());
		transactionDTO.setQuantity(transaction.getQuantity());
		
		return transactionDTO;
	}
	
}
