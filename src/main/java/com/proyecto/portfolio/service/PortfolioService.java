package com.proyecto.portfolio.service;

import com.proyecto.asset.exception.InvalidAssetArgumentException;
import com.proyecto.portfolio.exception.PortfolioNotFoundException;
import com.proyecto.rest.resource.portfolio.dto.PortfolioDTO;

public interface PortfolioService {

	/**
	 * Stores the Portfolio specified and retrieves it with new Id assigned.
	 * 
	 * @param portfolioDTO
	 * @return PortfolioDTO
	 * @throws InvalidAssetArgumentException
	 */
	PortfolioDTO store(PortfolioDTO portfolioDTO)
			throws InvalidAssetArgumentException;

	/**
	 * Retrieves the PortfolioDTO with specified ID
	 * 
	 * @param id
	 * @return PortfolioDTO
	 * @throws PortfolioNotFoundException
	 */
	PortfolioDTO findById(Long id) throws PortfolioNotFoundException;

}
