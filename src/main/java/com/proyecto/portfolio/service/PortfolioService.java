package com.proyecto.portfolio.service;

import com.proyecto.asset.exception.InvalidAssetArgumentException;
import com.proyecto.rest.resource.portfolio.dto.PortfolioDTO;

public interface PortfolioService {

	/**
	 * Stores the Portfolio specified and retrieves it with new Id assigned.
	 * 
	 * @param portfolioDTO
	 * @return PortfolioDTO
	 * @throws InvalidAssetArgumentException
	 */
	PortfolioDTO store(PortfolioDTO portfolioDTO) throws InvalidAssetArgumentException;

}
