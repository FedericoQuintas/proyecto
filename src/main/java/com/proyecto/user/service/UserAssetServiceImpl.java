package com.proyecto.user.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.proyecto.user.domain.Transaction;
import com.proyecto.user.domain.UserAsset;

@Service("userAssetService")
public class UserAssetServiceImpl implements UserAssetService {

	@Override
	public Transaction obtainLastTransaction(UserAsset userAsset) {

		Transaction lastTransaction = null;

		if (!userAsset.getTransactions().isEmpty()) {

			lastTransaction = calculateLastTransaction(userAsset);

		}

		return lastTransaction;

	}

	private Transaction calculateLastTransaction(UserAsset userAsset) {
		Date lastDate = userAsset.getTransactions().get(0).getTradingDate();
		Transaction lastTransaction = null;

		for (Transaction transaction : userAsset.getTransactions()) {
			if (transaction.getTradingDate().after(lastDate)) {
				lastDate = transaction.getTradingDate();
				lastTransaction = transaction;
			}

		}
		return lastTransaction;
	}
}
