package com.proyecto.unit.user.helper;

import com.proyecto.rest.resource.user.dto.PortfolioDTO;

public class PortfolioHelper {

	private static final String DEFAULT_NAME = "Default Name";

	public static PortfolioDTO createDefaultDTO() {
		PortfolioDTO portfolioDTO = new PortfolioDTO();

		portfolioDTO.setName(DEFAULT_NAME);
		return portfolioDTO;
	}

}
