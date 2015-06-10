package com.proyecto.rest.resource.portfolio.dto.factory;

import java.util.ArrayList;
import java.util.List;

import com.proyecto.portfolio.domain.Portfolio;
import com.proyecto.rest.resource.portfolio.dto.PortfolioDTO;
import com.proyecto.rest.resource.userasset.dto.factory.UserAssetDTOFactory;
import com.proyecto.rest.resource.usserasset.dto.UserAssetDTO;
import com.proyecto.userasset.domain.UserAsset;

public class PortfolioFactoryDTO {

	public static PortfolioDTO create(Portfolio portfolio) {
		PortfolioDTO portfolioDTO = new PortfolioDTO();

		portfolioDTO.setId(portfolio.getId());
		portfolioDTO.setName(portfolio.getName());
		portfolioDTO.setCurrentAssets(convertToDTOs(portfolio
				.getCurrentAssets()));

		return portfolioDTO;

	}

	private static List<UserAssetDTO> convertToDTOs(
			List<UserAsset> currentAssets) {
		List<UserAssetDTO> userAssetDTOs = new ArrayList<>();
		for (UserAsset userAsset : currentAssets) {
			userAssetDTOs.add(UserAssetDTOFactory.create(userAsset));
		}
		return userAssetDTOs;
	}

}
