package com.proyecto.unit.user.helper;

import java.util.Date;

import com.proyecto.common.currency.InvertarCurrency;
import com.proyecto.rest.resource.user.dto.TransactionDTO;

public class TransactionHelper {

	private static final Integer DEFAULT_QUANTITY = 10;
	private static final Float DEFAULT_PRICE_PAID = new Float(100);

	public static TransactionDTO createDefaultTransactionDTO() {

		TransactionDTO transactionDTO = new TransactionDTO();
		transactionDTO.setPricePaid(DEFAULT_PRICE_PAID);
		transactionDTO.setQuantity(new Integer(DEFAULT_QUANTITY));
		transactionDTO.setTradingDate(new Date());
		transactionDTO.setCurrency(InvertarCurrency.US);
		transactionDTO.setAssetId(new Long(1));
		return transactionDTO;
	}

}
