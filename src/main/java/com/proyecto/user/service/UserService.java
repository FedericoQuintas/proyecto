package com.proyecto.user.service;

import java.util.List;

import com.proyecto.rest.resource.user.dto.InvertarUserDTO;
import com.proyecto.rest.resource.user.dto.PortfolioDTO;
import com.proyecto.user.exception.InvalidPortfolioArgumentException;
import com.proyecto.user.exception.PortfolioNotFoundException;
import com.proyecto.user.exception.UserNotFoundException;

public interface UserService {

	/**
	 * Stores the UserDTO specified and retrieves it with new Id assigned.
	 * 
	 * @return InvertarUserDTO
	 */
	InvertarUserDTO store();

	/**
	 * Retrieves the InvertarUserDTO with specified ID
	 * 
	 * @param id
	 * @return InvertarUserDTO
	 * @throws UserNotFoundException
	 */
	InvertarUserDTO findById(Long id) throws UserNotFoundException;

	/**
	 * Retrieves user's portfolios
	 * 
	 * @param userId
	 * @return List<PortfolioDTO>
	 * @throws UserNotFoundException
	 */
	List<PortfolioDTO> getPortfolios(Long userId) throws UserNotFoundException;

	/**
	 * Adds the specified Portfolio to the user's collection and retrieves it
	 * with new Id assigned.
	 * 
	 * @param userId
	 * @param portfolioDTO
	 * @return PortfolioDTO
	 * @throws UserNotFoundException
	 * @throws InvalidPortfolioArgumentException
	 */
	PortfolioDTO addPortfolio(PortfolioDTO portfolioDTO, Long userId)
			throws UserNotFoundException, InvalidPortfolioArgumentException;

	/**
	 * Retrieves specified Portfolio
	 * 
	 * @param userId
	 * @param portfolioId
	 * @return PortfolioDTO
	 * @throws UserNotFoundException
	 * @throws PortfolioNotFoundException
	 */

	PortfolioDTO findPortfolioById(Long userId, Long portfolioId)
			throws UserNotFoundException, PortfolioNotFoundException;

}
