package com.proyecto.portfolio.domain.factory;

import com.proyecto.portfolio.domain.Portfolio;
import com.proyecto.rest.resource.portfolio.dto.PortfolioDTO;

public class PortfolioFactory {

	public static Portfolio create(PortfolioDTO portfolioDTO, Long nextID) {
		return new Portfolio(nextID);
	}

}
