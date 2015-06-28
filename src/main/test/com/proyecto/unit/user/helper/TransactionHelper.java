package com.proyecto.unit.user.helper;

import java.util.Date;

import com.proyecto.rest.resource.user.dto.TransactionDTO;
import com.proyecto.user.domain.TransactionType;

public class TransactionHelper {

	private static final Integer DEFAULT_QUANTITY = 10;
	private static final Float DEFAULT_PRICE_PAID = new Float(100);

	public static TransactionDTO createDefaultTransactionDTO() {

		TransactionDTO transactionDTO = new TransactionDTO();
		transactionDTO.setPricePaid(DEFAULT_PRICE_PAID);
		transactionDTO.setQuantity(new Long(DEFAULT_QUANTITY));
		transactionDTO.setTradingDate(new Date());
		transactionDTO.setAssetId(new Long(1));
		transactionDTO.setType(TransactionType.PURCHASE);
		return transactionDTO;
	}

}
