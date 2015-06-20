package com.proyecto.user.domain.factory;

import java.util.ArrayList;
import java.util.List;

import com.proyecto.rest.resource.user.dto.PortfolioDTO;
import com.proyecto.rest.resource.user.dto.TransactionDTO;
import com.proyecto.rest.resource.user.dto.UserAssetDTO;
import com.proyecto.user.domain.Portfolio;
import com.proyecto.user.domain.Transaction;
import com.proyecto.user.domain.UserAsset;

public class PortfolioDTOFactory {

	public static PortfolioDTO create(Portfolio portfolio) {
		PortfolioDTO portfolioDTO = new PortfolioDTO();

		portfolioDTO.setId(portfolio.getId());
		portfolioDTO.setName(portfolio.getName());
		portfolioDTO.setMarketValue(portfolio.getMarketValue());
		portfolioDTO.setUserAssets(convertToDTOs(portfolio));

		return portfolioDTO;

	}
	
	private static List<UserAssetDTO> convertToDTOs(Portfolio portfolio) {

		List<UserAssetDTO> UserAssetsDTO = new ArrayList<UserAssetDTO>();

		for (UserAsset asset : portfolio.getUserAssets()) {
			UserAssetsDTO.add(UserAssetDTOFactory.create(asset));
		}

		return UserAssetsDTO;
	}

}
