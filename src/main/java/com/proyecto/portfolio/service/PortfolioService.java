package com.proyecto.portfolio.service;

import com.proyecto.portfolio.domain.Portfolio;
import com.proyecto.rest.resource.user.dto.PortfolioDTO;

public interface PortfolioService {

	/**
	 * Stores the Portfolio specified and retrieves it with new Id assigned.
	 * 
	 * @param portfolioDTO
	 * @return Portfolio
	 */
	Portfolio store(PortfolioDTO portfolioDTO);

}
