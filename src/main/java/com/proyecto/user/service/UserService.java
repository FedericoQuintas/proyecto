package com.proyecto.user.service;

import java.util.List;
import java.util.Map;

import com.proyecto.asset.exception.AssetNotFoundException;
import com.proyecto.asset.exception.InvalidAssetTypeException;
import com.proyecto.common.exception.ApplicationServiceException;
import com.proyecto.rest.resource.user.dto.InvertarUserDTO;
import com.proyecto.rest.resource.user.dto.InvertarUserLoginDTO;
import com.proyecto.rest.resource.user.dto.PortfolioDTO;
import com.proyecto.rest.resource.user.dto.TheoreticalPortfolioDTO;
import com.proyecto.rest.resource.user.dto.TransactionDTO;
import com.proyecto.user.domain.PortfolioHistoryVO;
import com.proyecto.user.domain.valueobject.MarketValueVO;
import com.proyecto.user.exception.InvalidLoginException;
import com.proyecto.user.exception.InvalidPasswordException;
import com.proyecto.user.exception.InvalidPortfolioArgumentException;
import com.proyecto.user.exception.PortfolioNameAlreadyInUseException;
import com.proyecto.user.exception.PortfolioNotFoundException;
import com.proyecto.user.exception.UserMailAlreadyExistsException;
import com.proyecto.user.exception.UserNotFoundException;
import com.proyecto.user.exception.UsernameAlreadyExistsException;

public interface UserService {

	/**
	 * Stores the UserDTO specified and retrieves it with new Id assigned.
	 * 
	 * @param userDTO
	 * 
	 * @return InvertarUserDTO
	 * @throws InvalidPasswordException
	 * @throws UsernameAlreadyExistsException
	 * @throws UserMailAlreadyExistsException
	 */
	InvertarUserDTO store(InvertarUserDTO userDTO)
			throws InvalidPasswordException, UsernameAlreadyExistsException,
			UserMailAlreadyExistsException;

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
	 * @throws PortfolioNameAlreadyInUseException
	 */
	PortfolioDTO addPortfolio(PortfolioDTO portfolioDTO, Long userId)
			throws UserNotFoundException, InvalidPortfolioArgumentException,
			PortfolioNameAlreadyInUseException;

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
	 * Removes specified portfolio from the given user
	 * @param userId
	 * @param portfolioId
	 * @throws ApplicationServiceException 
	 */
	void removePortfolioById(Long userId, Long portfolioId) throws ApplicationServiceException;

	/**
	 * Calculates and retrieves de Performance of all the User Portfolios
	 * 
	 * @return Float
	 * @throws UserNotFoundException
	 * @throws AssetNotFoundException
	 * @throws InvalidAssetTypeException
	 */

	Float getPortfoliosPerformance(Long id) throws UserNotFoundException,
			AssetNotFoundException, InvalidAssetTypeException;

	/**
	 * Calculates and retrieves de Performance of the specified User Portfolio
	 * 
	 * @return Float
	 * @throws UserNotFoundException
	 * @throws PortfolioNotFoundException
	 * @throws AssetNotFoundException
	 * @throws InvalidAssetTypeException
	 */
	Float getPortfolioPerformance(Long userId, Long portfolioId)
			throws UserNotFoundException, PortfolioNotFoundException,
			AssetNotFoundException, InvalidAssetTypeException;

	/**
	 * Calculates and retrieves the Portfolio Market Value
	 * 
	 * @return List<MarketValue>
	 * @throws UserNotFoundException
	 * @throws PortfolioNotFoundException
	 * @throws AssetNotFoundException
	 * @throws InvalidAssetTypeException
	 */
	List<MarketValueVO> getPortfolioMarketValue(Long userId, Long portfolioId)
			throws UserNotFoundException, PortfolioNotFoundException,
			AssetNotFoundException, InvalidAssetTypeException;

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

	/**
	 * User Login
	 * 
	 * @param loginDTO
	 * @return
	 * @throws UserNotFoundException
	 * @throws InvalidPasswordException
	 * @throws InvalidLoginException
	 */
	InvertarUserDTO login(InvertarUserLoginDTO loginDTO)
			throws UserNotFoundException, InvalidPasswordException,
			InvalidLoginException;

	/**
	 * 
	 * @param amountOfPoints
	 * @return
	 */
	List<TheoreticalPortfolioDTO> getInvestorProfile(Integer amountOfPoints);

	Map<Long, List<PortfolioHistoryVO>> getPortfoliosHistories(Long userId)
			throws ApplicationServiceException;

}
