package com.proyecto.user.domain.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.proyecto.asset.exception.AssetNotFoundException;
import com.proyecto.asset.service.AssetService;
import com.proyecto.common.currency.InvertarCurrencyCode;
import com.proyecto.rest.resource.asset.dto.AssetDTO;
import com.proyecto.rest.resource.user.dto.TransactionDTO;
import com.proyecto.user.domain.InvertarUser;
import com.proyecto.user.domain.Portfolio;
import com.proyecto.user.domain.Transaction;
import com.proyecto.user.domain.TransactionType;
import com.proyecto.user.domain.UserAsset;
import com.proyecto.user.domain.factory.TransactionFactory;
import com.proyecto.user.domain.factory.UserAssetFactory;
import com.proyecto.user.domain.valueobject.MarketValueVO;
import com.proyecto.user.exception.InvalidUserAssetTransactionException;
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

			AssetDTO assetDTO = obtainAsset(userAsset.getAssetId());

			generateMarketValues(marketValues, assetDTO.getLastTradingPrice(),
					assetDTO.getCurrency(), userAsset.getOwnedQuantity());

		}
	}

	private void generateMarketValues(List<MarketValueVO> marketValues,
			Float lastTradingPrice, InvertarCurrencyCode currency,
			Long ownedQuantity) {

		if (marketValuesHasCurrency(currency, marketValues)) {

			calculateMarketValue(marketValues, lastTradingPrice, currency,
					ownedQuantity);

		} else {

			generateMarketValue(marketValues, currency, lastTradingPrice,
					ownedQuantity);
		}
	}

	private void calculateMarketValue(List<MarketValueVO> marketValues,
			Float lastTradingPrice, InvertarCurrencyCode lastTransactionCurrency,
			Long ownedQuantity) {

		MarketValueVO marketValueWithCurrency = obtainMarketValueWithCurrency(
				lastTransactionCurrency, marketValues);

		marketValueWithCurrency.calculate(lastTradingPrice, ownedQuantity);
	}

	private void generateMarketValue(List<MarketValueVO> marketValues,
			InvertarCurrencyCode currency, Float lastTradingPrice,
			Long ownedQuantity) {
		marketValues.add(new MarketValueVO(currency, lastTradingPrice,
				ownedQuantity));
	}

	private AssetDTO obtainAsset(Long assetId) throws AssetNotFoundException {
		return assetService.findById(assetId);
	}

	private MarketValueVO obtainMarketValueWithCurrency(
			InvertarCurrencyCode currency, List<MarketValueVO> marketValues) {

		MarketValueVO marketValueVOResult = null;

		for (MarketValueVO marketValueVO : marketValues) {
			if (marketValueVO.getCurrency().equals(currency)) {
				return marketValueVO;
			}
		}
		return marketValueVOResult;
	}

	private Boolean marketValuesHasCurrency(InvertarCurrencyCode currency,
			List<MarketValueVO> marketValues) {

		Boolean hasCurrency = Boolean.FALSE;

		List<InvertarCurrencyCode> currencies = obtainMarketValuesCurrencies(marketValues);

		if (currencies.contains(currency)) {

			hasCurrency = Boolean.TRUE;
		}

		return hasCurrency;
	}

	private List<InvertarCurrencyCode> obtainMarketValuesCurrencies(
			List<MarketValueVO> marketValues) {

		List<InvertarCurrencyCode> currencies = new ArrayList<InvertarCurrencyCode>();
		for (MarketValueVO marketValue : marketValues) {
			currencies.add(marketValue.getCurrency());
		}
		return currencies;
	}

	@Override
	public Float calculatePerformance(Portfolio portfolio)
			throws AssetNotFoundException {

		Float totalPerformance = new Float(0);

		for (UserAsset userAsset : portfolio.getUserAssets()) {

			totalPerformance += calculateUserAssetPerformance(userAsset,
					obtainAsset(userAsset.getAssetId()));

		}

		return calculateTotalPerformance(portfolio, totalPerformance);
	}

	private Float calculateTotalPerformance(Portfolio portfolio,
			Float totalPerformance) {

		Float averagePerformance = new Float(0);
		if (portfolio.getUserAssets().size() != 0) {
			averagePerformance = totalPerformance
					/ new Float(portfolio.getUserAssets().size());
		}
		return averagePerformance;
	}

	private Float calculateUserAssetPerformance(UserAsset userAsset,
			AssetDTO assetDTO) {

		Float investedMoney = new Float(0);
		Float quantityOwned = new Float(0);
		Float earnedMoney = new Float(0);

		for (Transaction transaction : userAsset.getTransactions()) {

			if (transaction.getType().equals(TransactionType.PURCHASE)) {
				investedMoney += transaction.getPricePaid();
				quantityOwned += transaction.getQuantity().floatValue();

			} else {
				earnedMoney += transaction.getPricePaid();
				quantityOwned -= transaction.getQuantity().floatValue();
			}

		}

		Float actualValue = quantityOwned * assetDTO.getLastTradingPrice()
				+ earnedMoney;
		Float performance = (actualValue / investedMoney - new Float(1))
				* new Float(100);

		return performance;
	}

	@Override
	public void storeUserAsset(TransactionDTO transactionDTO,
			InvertarUser user, Long portfolioId)
			throws PortfolioNotFoundException, UserAssetNotFoundException,
			InvalidUserAssetTransactionException {

		Transaction transaction = TransactionFactory.create(transactionDTO);

		Portfolio portfolio = obtainSpecifiedPortfolio(portfolioId,
				user.getPortfolios());

		fillUserAssetWithTransaction(transaction, portfolio);

	}

	private void fillUserAssetWithTransaction(Transaction transaction,
			Portfolio portfolio) throws UserAssetNotFoundException,
			InvalidUserAssetTransactionException {

		if (userAssetExists(transaction, portfolio)) {
			addTransactionToUserAsset(transaction, portfolio);
		} else {

			generateNewUserAsset(transaction, portfolio);
		}

	}

	private void generateNewUserAsset(Transaction transaction,
			Portfolio portfolio) throws UserAssetNotFoundException,
			InvalidUserAssetTransactionException {
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
			Portfolio portfolio) throws InvalidUserAssetTransactionException {

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
