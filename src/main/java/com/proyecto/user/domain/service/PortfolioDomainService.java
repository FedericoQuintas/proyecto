package com.proyecto.user.domain.service;

import java.util.List;

import com.proyecto.asset.exception.AssetNotFoundException;
import com.proyecto.asset.exception.InvalidAssetTypeException;
import com.proyecto.rest.resource.user.dto.TransactionDTO;
import com.proyecto.user.domain.InvertarUser;
import com.proyecto.user.domain.Portfolio;
import com.proyecto.user.domain.valueobject.MarketValueVO;
import com.proyecto.user.exception.InvalidUserAssetTransactionException;
import com.proyecto.user.exception.PortfolioNotFoundException;

public interface PortfolioDomainService {

	List<MarketValueVO> calculateMarketValue(Portfolio portfolio)
			throws AssetNotFoundException, InvalidAssetTypeException;

	Float calculatePerformance(Portfolio portfolio)
			throws AssetNotFoundException, InvalidAssetTypeException;

	void storeUserAsset(TransactionDTO transactionDTO, InvertarUser user,
			Long portfolioId) throws PortfolioNotFoundException,
			UserAssetNotFoundException, InvalidUserAssetTransactionException;

	Portfolio obtainSpecifiedPortfolio(Long portfolioId,
			List<Portfolio> portfolios) throws PortfolioNotFoundException;

}
