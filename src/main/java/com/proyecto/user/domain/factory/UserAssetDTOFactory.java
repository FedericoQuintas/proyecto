package com.proyecto.user.domain.factory;

import java.util.ArrayList;
import java.util.List;

import com.proyecto.rest.resource.user.dto.TransactionDTO;
import com.proyecto.rest.resource.user.dto.UserAssetDTO;
import com.proyecto.user.domain.Transaction;
import com.proyecto.user.domain.UserAsset;

public class UserAssetDTOFactory {

	public static UserAssetDTO create(UserAsset userAsset) {
		UserAssetDTO userAssetDTO = new UserAssetDTO();

		userAssetDTO.setAssetId(userAsset.getAssetId());
		userAssetDTO.setTransactions(convertToDTOs(userAsset));
		userAssetDTO.setCumulativePayments(userAsset.getCumulativePayments());
		return userAssetDTO;

	}

	private static List<TransactionDTO> convertToDTOs(UserAsset userAsset) {

		List<TransactionDTO> transactionsDTO = new ArrayList<TransactionDTO>();

		for (Transaction transaction : userAsset.getTransactions()) {
			transactionsDTO.add(TransactionDTOFactory.create(transaction));
		}

		return transactionsDTO;
	}
}
