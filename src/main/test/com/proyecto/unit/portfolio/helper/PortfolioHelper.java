package com.proyecto.unit.portfolio.helper;

import com.proyecto.rest.resource.portfolio.dto.PortfolioDTO;

public class PortfolioHelper {

	private static final String DEFAULT_NAME = "Default Name";

	public static PortfolioDTO createDefaultDTO() {
		PortfolioDTO portfolioDTO = new PortfolioDTO();
		portfolioDTO.setName(DEFAULT_NAME);
		return portfolioDTO;
	}

}
