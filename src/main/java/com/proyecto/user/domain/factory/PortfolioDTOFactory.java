package com.proyecto.user.domain.factory;

import com.proyecto.rest.resource.user.dto.PortfolioDTO;
import com.proyecto.user.domain.Portfolio;

public class PortfolioDTOFactory {

	public static PortfolioDTO create(Portfolio portfolio) {
		PortfolioDTO portfolioDTO = new PortfolioDTO();

		portfolioDTO.setId(portfolio.getId());
		portfolioDTO.setName(portfolio.getName());
		portfolioDTO.setMarketValue(portfolio.getMarketValue());

		return portfolioDTO;

	}

}
