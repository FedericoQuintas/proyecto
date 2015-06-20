package com.proyecto.unit.user.helper;

import com.proyecto.rest.resource.user.dto.PortfolioDTO;
import com.proyecto.rest.resource.user.dto.UserAssetDTO;

public class PortfolioHelper {

	private static final String DEFAULT_NAME = "Default Name";

	public static PortfolioDTO createDefaultDTO() {
		PortfolioDTO portfolioDTO = new PortfolioDTO();

		portfolioDTO.setName(DEFAULT_NAME);
		UserAssetDTO userAssetDTO = new UserAssetDTO();
		userAssetDTO.setAssetId(new Long(1));
		portfolioDTO.getUserAssets().add(userAssetDTO);

		return portfolioDTO;
	}

}
