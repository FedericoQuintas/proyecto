package com.proyecto.user.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.proyecto.asset.exception.AssetNotFoundException;
import com.proyecto.asset.service.AssetService;
import com.proyecto.common.exception.ApplicationServiceException;
import com.proyecto.common.exception.DomainException;
import com.proyecto.common.exception.ObjectNotFoundException;
import com.proyecto.rest.resource.user.dto.InvertarUserDTO;
import com.proyecto.rest.resource.user.dto.PortfolioDTO;
import com.proyecto.rest.resource.user.dto.TransactionDTO;
import com.proyecto.user.domain.InvertarUser;
import com.proyecto.user.domain.Portfolio;
import com.proyecto.user.domain.factory.InvertarUserDTOFactory;
import com.proyecto.user.domain.factory.InvertarUserFactory;
import com.proyecto.user.domain.factory.PortfolioDTOFactory;
import com.proyecto.user.domain.factory.PortfolioFactory;
import com.proyecto.user.domain.service.PortfolioDomainService;
import com.proyecto.user.domain.valueobject.MarketValueVO;
import com.proyecto.user.exception.InvalidPortfolioArgumentException;
import com.proyecto.user.exception.PortfolioNotFoundException;
import com.proyecto.user.exception.UserNotFoundException;
import com.proyecto.user.persistence.UserDAO;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserDAO userDAO;

	@Resource
	private PortfolioDomainService portfolioDomainService;
	
	@Resource
	private AssetService assetService;

	@Override
	public InvertarUserDTO findById(Long id) throws UserNotFoundException {
		try {
			InvertarUser user = userDAO.findById(id);
			return InvertarUserDTOFactory.create(user);
		} catch (ObjectNotFoundException e) {
			throw new UserNotFoundException(e);
		}
	}

	@Override
	public List<PortfolioDTO> getPortfolios(Long userId)
			throws UserNotFoundException {

		InvertarUserDTO userDTO = findById(userId);

		return userDTO.getPortfolios();
	}

	@Override
	public PortfolioDTO addPortfolio(PortfolioDTO portfolioDTO, Long userId)
			throws UserNotFoundException, InvalidPortfolioArgumentException {

		try {
			InvertarUser user = userDAO.findById(userId);
			Portfolio portfolio = PortfolioFactory.create(portfolioDTO,
					userDAO.nextPortfolioID());

			user.addPortfolio(portfolio);

			updateUser(user);

			return PortfolioDTOFactory.create(portfolio);
		} catch (ObjectNotFoundException e) {
			throw new UserNotFoundException(e);
		}

	}

	private void updateUser(InvertarUser invertarUser) {
		userDAO.update(invertarUser);
	}

	@Override
	public PortfolioDTO findPortfolioById(Long userId, Long portfolioId)
			throws ApplicationServiceException {

		InvertarUser user;
		try {
			user = userDAO.findById(userId);
			Portfolio portfolio = portfolioDomainService
					.obtainSpecifiedPortfolio(portfolioId, user.getPortfolios());

			return PortfolioDTOFactory.create(portfolio);
		} catch (ObjectNotFoundException e) {
			throw new ApplicationServiceException(e.getMessage(),
					e.getErrorCode());
		}

	}

	@Override
	public Double getPortfoliosPerformance(Long userId)
			throws UserNotFoundException {

		try {
			InvertarUser user = userDAO.findById(userId);
			Double portfoliosPerformance = new Double(0);

			for (Portfolio portfolio : user.getPortfolios()) {
				portfoliosPerformance += portfolioDomainService
						.calculatePerformance(portfolio);
			}

			return portfoliosPerformance;
		} catch (ObjectNotFoundException e) {
			throw new UserNotFoundException(e);
		}

	}

	@Override
	public Double getPortfolioPerformance(Long userId, Long portfolioId)
			throws UserNotFoundException, PortfolioNotFoundException {
		try {
			InvertarUser user = userDAO.findById(userId);
			Portfolio portfolio = user.getPortfolio(portfolioId);
			return portfolioDomainService.calculatePerformance(portfolio);
		} catch (ObjectNotFoundException e) {
			throw new UserNotFoundException(e);
		}

	}

	@Override
	public InvertarUserDTO store(InvertarUserDTO userDTO) {

		InvertarUser user = InvertarUserFactory.create(userDAO.nextID(),
				userDTO.getUsername(), userDTO.getMail());

		InvertarUser storedUser = userDAO.store(user);

		return InvertarUserDTOFactory.create(storedUser);

	}

	@Override
	public List<MarketValueVO> getPortfolioMarketValue(Long userId,
			Long portfolioId) throws UserNotFoundException,
			PortfolioNotFoundException, AssetNotFoundException {
		try {
			InvertarUser user = userDAO.findById(userId);
			Portfolio portfolio = user.getPortfolio(portfolioId);
			return portfolioDomainService.calculateMarketValue(portfolio);
		} catch (ObjectNotFoundException e) {
			throw new UserNotFoundException(e);
		}

	}

	@Override
	public TransactionDTO storeUserAsset(TransactionDTO transactionDTO,
			Long userId, Long portfolioId) throws ApplicationServiceException {

		try {
			InvertarUser user = userDAO.findById(userId);
			assetService.findById(transactionDTO.getAssetId());
			portfolioDomainService.storeUserAsset(transactionDTO, user,
					portfolioId);
			updateUser(user);
			return transactionDTO;
		} catch (DomainException e) {
			throw new ApplicationServiceException(e.getMessage(),
					e.getErrorCode());
		} catch (ObjectNotFoundException e) {
			throw new ApplicationServiceException(e.getMessage(),
					e.getErrorCode());
		}

	}

}
