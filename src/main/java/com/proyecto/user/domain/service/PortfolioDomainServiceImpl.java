package com.proyecto.user.domain.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.proyecto.asset.exception.AssetNotFoundException;
import com.proyecto.asset.service.AssetService;
import com.proyecto.common.currency.InvertarCurrency;
import com.proyecto.rest.resource.user.dto.TransactionDTO;
import com.proyecto.user.domain.InvertarUser;
import com.proyecto.user.domain.Portfolio;
import com.proyecto.user.domain.Transaction;
import com.proyecto.user.domain.TransactionType;
import com.proyecto.user.domain.UserAsset;
import com.proyecto.user.domain.factory.TransactionFactory;
import com.proyecto.user.domain.factory.UserAssetFactory;
import com.proyecto.user.domain.valueobject.MarketValueVO;
import com.proyecto.user.exception.PortfolioNotFoundException;

public class PortfolioDomainServiceImpl implements PortfolioDomainService {

	@Resource
	private AssetService assetService;

	@Override
	public List<MarketValueVO> calculateMarketValue(Portfolio portfolio)
			throws AssetNotFoundException {

		List<MarketValueVO> marketValues = new ArrayList<MarketValueVO>();

		for (UserAsset userAsset : portfolio.getUserAssets()) {

			Transaction lastTransaction = userAsset.obtainLastTransaction();

			evaluateLastTransaction(marketValues, lastTransaction, userAsset);

		}

		return marketValues;
	}

	private void evaluateLastTransaction(List<MarketValueVO> marketValues,
			Transaction lastTransaction, UserAsset userAsset)
			throws AssetNotFoundException {

		if (lastTransaction != null) {

			Float lastTradingPrice = obtainLastTradingPrice(userAsset
					.getAssetId());

			generateMarketValues(marketValues, lastTransaction,
					lastTradingPrice);

		}
	}

	private void generateMarketValues(List<MarketValueVO> marketValues,
			Transaction lastTransaction, Float lastTradingPrice) {

			if (marketValuesHasCurrency(lastTransaction.getCurrency(), marketValues)) {

				calculateMarketValue(marketValues, lastTradingPrice, lastTransaction.getCurrency());

			} else {

				generateMarketValue(marketValues, lastTransaction,
						lastTradingPrice);
		}
	}

	private void calculateMarketValue(List<MarketValueVO> marketValues,
			Float lastTradingPrice, InvertarCurrency lastTransactionCurrency) {

		MarketValueVO marketValueWithCurrency = obtainMarketValueWithCurrency(
				lastTransactionCurrency, marketValues);

		marketValueWithCurrency.calculate(lastTradingPrice);
	}

	private void generateMarketValue(List<MarketValueVO> marketValues,
			Transaction lastTransaction, Float lastTradingPrice) {
		marketValues.add(new MarketValueVO(lastTransaction.getCurrency(),
				lastTradingPrice));
	}

	private Float obtainLastTradingPrice(Long assetId)
			throws AssetNotFoundException {
		return assetService.findById(assetId).getLastTradingPrice();
	}

	private MarketValueVO obtainMarketValueWithCurrency(
			InvertarCurrency currency, List<MarketValueVO> marketValues) {

		MarketValueVO marketValueVOResult = null;

		for (MarketValueVO marketValueVO : marketValues) {
			if (marketValueVO.getCurrency().equals(currency)) {
				return marketValueVO;
			}
		}
		return marketValueVOResult;
	}

	private Boolean marketValuesHasCurrency(InvertarCurrency currency,
			List<MarketValueVO> marketValues) {

		Boolean hasCurrency = Boolean.FALSE;

		List<InvertarCurrency> currencies = obtainMarketValuesCurrencies(marketValues);

		if (currencies.contains(currency)) {

			hasCurrency = Boolean.TRUE;
		}

		return hasCurrency;
	}

	private List<InvertarCurrency> obtainMarketValuesCurrencies(
			List<MarketValueVO> marketValues) {

		List<InvertarCurrency> currencies = new ArrayList<InvertarCurrency>();
		for (MarketValueVO marketValue : marketValues) {
			currencies.add(marketValue.getCurrency());
		}
		return currencies;
	}

	@Override
	public Double calculatePerformance(Portfolio portfolio) {

		return new Double(0);
	}

	@Override
	public void storeUserAsset(TransactionDTO transactionDTO,
			InvertarUser user, Long portfolioId)
			throws PortfolioNotFoundException, UserAssetNotFoundException {

		Transaction transaction = TransactionFactory.create(transactionDTO);

		Portfolio portfolio = obtainSpecifiedPortfolio(portfolioId,
				user.getPortfolios());

		fillUserAssetWithTransaction(transaction, portfolio);

	}

	private void fillUserAssetWithTransaction(Transaction transaction,
			Portfolio portfolio) throws UserAssetNotFoundException {

		if (userAssetExists(transaction, portfolio)) {
			addTransactionToUserAsset(transaction, portfolio);
		} else {

			generateNewUserAsset(transaction, portfolio);
		}

	}

	private void generateNewUserAsset(Transaction transaction,
			Portfolio portfolio) throws UserAssetNotFoundException {
		if (transaction.getType().equals(TransactionType.PURCHASE)) {
			UserAsset newUserAsset = UserAssetFactory.create(transaction
					.getAssetId());
			newUserAsset.addTransactions(transaction);
			portfolio.addUsserAsset(newUserAsset);
		} else {
			throw new UserAssetNotFoundException();
		}
	}

	private Boolean userAssetExists(Transaction transaction, Portfolio portfolio) {
		Boolean userAssetExists = Boolean.FALSE;

		for (UserAsset userAsset : portfolio.getUserAssets()) {
			if (userAsset.getAssetId().equals(transaction.getAssetId())) {

				return Boolean.TRUE;

			}
		}
		return userAssetExists;
	}

	private void addTransactionToUserAsset(Transaction transaction,
			Portfolio portfolio) {

		for (UserAsset userAsset : portfolio.getUserAssets()) {
			if (userAsset.getAssetId().equals(transaction.getAssetId())) {

				userAsset.addTransactions(transaction);

			}
		}
	}

	@Override
	public Portfolio obtainSpecifiedPortfolio(Long portfolioId,
			List<Portfolio> portfolios) throws PortfolioNotFoundException {
		Portfolio portfolio = null;

		for (Portfolio userPortfolio : portfolios) {
			if (userPortfolio.getId().equals(portfolioId)) {
				portfolio = userPortfolio;
			}
		}

		validateResult(portfolio);
		return portfolio;
	}

	private void validateResult(Portfolio portfolio)
			throws PortfolioNotFoundException {
		if (portfolio == null) {
			throw new PortfolioNotFoundException();
		}
	}

}
