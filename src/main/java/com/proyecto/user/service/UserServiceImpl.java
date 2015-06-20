package com.proyecto.user.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.proyecto.asset.exception.AssetNotFoundException;
import com.proyecto.asset.service.AssetService;
import com.proyecto.common.currency.InvertarCurrency;
import com.proyecto.common.exception.ObjectNotFoundException;
import com.proyecto.rest.resource.user.dto.InvertarUserDTO;
import com.proyecto.rest.resource.user.dto.PortfolioDTO;
import com.proyecto.user.domain.InvertarUser;
import com.proyecto.user.domain.Portfolio;
import com.proyecto.user.domain.Transaction;
import com.proyecto.user.domain.UserAsset;
import com.proyecto.user.domain.factory.InvertarUserDTOFactory;
import com.proyecto.user.domain.factory.InvertarUserFactory;
import com.proyecto.user.domain.factory.PortfolioDTOFactory;
import com.proyecto.user.domain.factory.PortfolioFactory;
import com.proyecto.user.domain.valueobject.MarketValueVO;
import com.proyecto.user.exception.InvalidPortfolioArgumentException;
import com.proyecto.user.exception.PortfolioNotFoundException;
import com.proyecto.user.exception.UserNotFoundException;
import com.proyecto.user.persistence.UserDAO;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserDAO userDAO;

	@Resource
	private PortfolioService portfolioService;

	@Resource
	private UserAssetService userAssetService;

	@Resource
	private AssetService assetService;

	@Override
	public InvertarUserDTO findById(Long id) throws UserNotFoundException {
		try {
			InvertarUser user = userDAO.findById(id);
			return InvertarUserDTOFactory.create(user);
		} catch (ObjectNotFoundException e) {
			throw new UserNotFoundException(e);
		}
	}

	@Override
	public List<PortfolioDTO> getPortfolios(Long userId)
			throws UserNotFoundException {

		InvertarUserDTO userDTO = findById(userId);

		return userDTO.getPortfolios();
	}

	@Override
	public PortfolioDTO addPortfolio(PortfolioDTO portfolioDTO, Long userId)
			throws UserNotFoundException, InvalidPortfolioArgumentException {

		try {
			InvertarUser user = userDAO.findById(userId);

			Portfolio portfolio = PortfolioFactory.create(portfolioDTO,
					userDAO.nextPortfolioID());

			user.addPortfolio(portfolio);

			updateUser(user);

			return PortfolioDTOFactory.create(portfolio);
		} catch (ObjectNotFoundException e) {
			throw new UserNotFoundException(e);
		}

	}

	private void updateUser(InvertarUser invertarUser) {
		userDAO.update(invertarUser);
	}

	@Override
	public PortfolioDTO findPortfolioById(Long userId, Long portfolioId)
			throws UserNotFoundException, PortfolioNotFoundException {

		InvertarUserDTO userDTO = findById(userId);

		PortfolioDTO portfolioDTO = obtainSpecifiedPortfolio(portfolioId,
				userDTO);

		return portfolioDTO;

	}

	private PortfolioDTO obtainSpecifiedPortfolio(Long portfolioId,
			InvertarUserDTO userDTO) throws PortfolioNotFoundException {
		PortfolioDTO portfolioDTO = null;

		for (PortfolioDTO userPortfolioDTO : userDTO.getPortfolios()) {
			if (userPortfolioDTO.getId().equals(portfolioId)) {
				portfolioDTO = userPortfolioDTO;
			}
		}

		validateResult(portfolioDTO);
		return portfolioDTO;
	}

	private void validateResult(PortfolioDTO portfolioDTO)
			throws PortfolioNotFoundException {
		if (portfolioDTO == null) {
			throw new PortfolioNotFoundException();
		}
	}

	@Override
	public Double getPortfoliosPerformance(Long userId)
			throws UserNotFoundException {

		try {
			InvertarUser user = userDAO.findById(userId);
			Double portfoliosPerformance = new Double(0);

			for (Portfolio portfolio : user.getPortfolios()) {
				portfoliosPerformance += calculatePerformance(portfolio);
			}

			return portfoliosPerformance;
		} catch (ObjectNotFoundException e) {
			throw new UserNotFoundException(e);
		}

	}

	private Double calculatePerformance(Portfolio portfolio) {

		return new Double(0);
	}

	@Override
	public Double getPortfolioPerformance(Long userId, Long portfolioId)
			throws UserNotFoundException, PortfolioNotFoundException {
		try {
			InvertarUser user = userDAO.findById(userId);
			Portfolio portfolio = user.getPortfolio(portfolioId);
			return calculatePerformance(portfolio);
		} catch (ObjectNotFoundException e) {
			throw new UserNotFoundException(e);
		}

	}

	@Override
	public InvertarUserDTO store(InvertarUserDTO userDTO) {

		InvertarUser user = InvertarUserFactory.create(userDAO.nextID(),
				userDTO.getUsername(), userDTO.getMail());

		InvertarUser storedUser = userDAO.store(user);

		return InvertarUserDTOFactory.create(storedUser);

	}

	@Override
	public List<MarketValueVO> getPortfolioMarketValue(Long userId,
			Long portfolioId) throws UserNotFoundException,
			PortfolioNotFoundException, AssetNotFoundException {
		try {
			InvertarUser user = userDAO.findById(userId);
			Portfolio portfolio = user.getPortfolio(portfolioId);
			return calculateMarketValue(portfolio);
		} catch (ObjectNotFoundException e) {
			throw new UserNotFoundException(e);
		}

	}

	private List<MarketValueVO> calculateMarketValue(Portfolio portfolio)
			throws AssetNotFoundException {

		List<MarketValueVO> marketValues = new ArrayList<MarketValueVO>();

		for (UserAsset userAsset : portfolio.getUserAssets()) {

			Transaction lastTransaction = userAssetService
					.obtainLastTransaction(userAsset);

			evaluateLastTransaction(marketValues, lastTransaction, userAsset);

		}

		return marketValues;
	}

	private void evaluateLastTransaction(List<MarketValueVO> marketValues,
			Transaction lastTransaction, UserAsset userAsset)
			throws AssetNotFoundException {

		if (lastTransaction != null) {

			Long lastTradingPrice = obtainLastTradingPrice(userAsset
					.getAssetId());

			generateMarketValues(marketValues, lastTransaction,
					lastTradingPrice);

		}
	}

	private void generateMarketValues(List<MarketValueVO> marketValues,
			Transaction lastTransaction, Long lastTradingPrice) {
		for (MarketValueVO marketValue : marketValues) {

			if (marketValuesHasCurrency(marketValue.getCurrency(), marketValues)) {

				calculateMarketValue(marketValues, lastTradingPrice,
						marketValue);

			} else {

				generateMarketValue(marketValues, lastTransaction,
						lastTradingPrice);

			}

		}
	}

	private void calculateMarketValue(List<MarketValueVO> marketValues,
			Long lastTradingPrice, MarketValueVO marketValue) {

		MarketValueVO marketValueWithCurrency = obtainMarketValueWithCurrency(
				marketValue.getCurrency(), marketValues);

		marketValueWithCurrency.calculate(lastTradingPrice);
	}

	private void generateMarketValue(List<MarketValueVO> marketValues,
			Transaction lastTransaction, Long lastTradingPrice) {
		marketValues.add(new MarketValueVO(lastTransaction.getCurrency(),
				lastTradingPrice));
	}

	private Long obtainLastTradingPrice(Long assetId)
			throws AssetNotFoundException {
		return assetService.findById(assetId).getLastTradingPrice();
	}

	private MarketValueVO obtainMarketValueWithCurrency(
			InvertarCurrency invertarCurrency, List<MarketValueVO> marketValues) {

		return null;
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
}
