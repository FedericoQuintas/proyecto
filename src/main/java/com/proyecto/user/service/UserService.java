package com.proyecto.user.service;

import java.util.List;

import com.proyecto.asset.exception.AssetNotFoundException;
import com.proyecto.common.exception.ApplicationServiceException;
import com.proyecto.rest.resource.user.dto.InvertarUserDTO;
import com.proyecto.rest.resource.user.dto.PortfolioDTO;
import com.proyecto.rest.resource.user.dto.TransactionDTO;
import com.proyecto.user.domain.valueobject.MarketValueVO;
import com.proyecto.user.exception.InvalidPortfolioArgumentException;
import com.proyecto.user.exception.PortfolioNotFoundException;
import com.proyecto.user.exception.UserNotFoundException;

public interface UserService {

	/**
	 * Stores the UserDTO specified and retrieves it with new Id assigned.
	 * 
	 * @param userDTO
	 * 
	 * @return InvertarUserDTO
	 */
	InvertarUserDTO store(InvertarUserDTO userDTO);

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
	 * @throws ApplicationServiceException
	 */

	PortfolioDTO findPortfolioById(Long userId, Long portfolioId)
			throws ApplicationServiceException;

	/**
	 * Calculates and retrieves de Performance of all the User Portfolios
	 * 
	 * @return Float
	 * @throws UserNotFoundException
	 * @throws AssetNotFoundException
	 */

	Float getPortfoliosPerformance(Long id) throws UserNotFoundException,
			AssetNotFoundException;

	/**
	 * Calculates and retrieves de Performance of the specified User Portfolio
	 * 
	 * @return Float
	 * @throws UserNotFoundException
	 * @throws PortfolioNotFoundException
	 * @throws AssetNotFoundException
	 */
	Float getPortfolioPerformance(Long userId, Long portfolioId)
			throws UserNotFoundException, PortfolioNotFoundException,
			AssetNotFoundException;

	/**
	 * Calculates and retrieves the Portfolio Market Value
	 * 
	 * @return List<MarketValue>
	 * @throws UserNotFoundException
	 * @throws PortfolioNotFoundException
	 * @throws AssetNotFoundException
	 */
	List<MarketValueVO> getPortfolioMarketValue(Long userId, Long portfolioId)
			throws UserNotFoundException, PortfolioNotFoundException,
			AssetNotFoundException;

	/**
	 * Tracks and retrieves a Transaction
	 * 
	 * @param transactionDTO
	 * @param userAssetId
	 * @param portfolioId
	 * @return TransactionDTO
	 * @throws UserNotFoundException
	 * @throws PortfolioNotFoundException
	 * @throws ApplicationServiceException
	 */

	TransactionDTO addTransaction(TransactionDTO transactionDTO, Long userId,
			Long portfolioId) throws UserNotFoundException,
			PortfolioNotFoundException, ApplicationServiceException;

}
