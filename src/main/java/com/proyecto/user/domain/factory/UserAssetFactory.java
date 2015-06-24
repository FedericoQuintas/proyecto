package com.proyecto.user.domain.factory;

import java.util.ArrayList;
import java.util.List;

import com.proyecto.rest.resource.user.dto.TransactionDTO;
import com.proyecto.rest.resource.user.dto.UserAssetDTO;
import com.proyecto.user.domain.Transaction;
import com.proyecto.user.domain.UserAsset;

public class UserAssetFactory {

	public static UserAsset create(UserAssetDTO userAssetDTO) {
		UserAsset userAsset = new UserAsset();

		userAsset.setAssetId(userAssetDTO.getAssetId());
		userAsset.setCumulativePayments(userAssetDTO.getCumulativePayments());
		List<Transaction> transactions = convertTransactions(userAssetDTO);
		userAsset.setTransactions(transactions);
		return userAsset;
	}

	private static List<Transaction> convertTransactions(
			UserAssetDTO userAssetDTO) {
		List<Transaction> transactions = new ArrayList<Transaction>();
		for (TransactionDTO transactionDTO : userAssetDTO.getTransactions()) {
			transactions.add(TransactionFactory.create(transactionDTO));
		}
		return transactions;
	}

	public static UserAsset create(Long assetId) {
		UserAsset userAsset = new UserAsset();

		userAsset.setAssetId(assetId);

		return userAsset;
	}

}
