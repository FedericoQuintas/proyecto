package com.proyecto.user.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.proyecto.asset.exception.InvalidAssetArgumentException;
import com.proyecto.common.exception.ObjectNotFoundException;
import com.proyecto.rest.resource.user.dto.InvertarUserDTO;
import com.proyecto.rest.resource.user.dto.PortfolioDTO;
import com.proyecto.user.domain.InvertarUser;
import com.proyecto.user.domain.Portfolio;
import com.proyecto.user.domain.factory.InvertarUserDTOFactory;
import com.proyecto.user.domain.factory.InvertarUserFactory;
import com.proyecto.user.domain.factory.PortfolioDTOFactory;
import com.proyecto.user.domain.factory.PortfolioFactory;
import com.proyecto.user.exception.PortfolioNotFoundException;
import com.proyecto.user.exception.UserNotFoundException;
import com.proyecto.user.persistence.UserDAO;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserDAO userDAO;

	@Override
	public InvertarUserDTO store() {

		InvertarUser user = InvertarUserFactory.create(userDAO.nextID());

		InvertarUser storedUser = userDAO.store(user);

		return InvertarUserDTOFactory.create(storedUser);

	}

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
			throws InvalidAssetArgumentException, UserNotFoundException {

		InvertarUser user = obtainUser(userId);

		Portfolio portfolio = PortfolioFactory.create(portfolioDTO,
				userDAO.nextPortfolioID());

		user.addPortfolio(portfolio);

		updateUser(user);

		return PortfolioDTOFactory.create(portfolio);
	}

	private void updateUser(InvertarUser invertarUser) {
		userDAO.update(invertarUser);
	}

	private InvertarUser obtainUser(Long userId) throws UserNotFoundException {

		InvertarUserDTO userDTO = findById(userId);

		InvertarUser user = InvertarUserFactory.create(userDTO.getId());

		return user;
	}

	@Override
	public PortfolioDTO findPortfolioById(Long userId, Long portfolioId)
			throws UserNotFoundException, PortfolioNotFoundException {

		InvertarUserDTO userDTO = findById(userId);

		PortfolioDTO portfolioDTO = obtainSpecifiedPortfolio(portfolioId,
				userDTO);

		return portfolioDTO;

	}

	private PortfolioDTO obtainSpecifiedPortfolio(Long portfolioId,
			InvertarUserDTO userDTO) throws PortfolioNotFoundException {
		PortfolioDTO portfolioDTO = null;

		for (PortfolioDTO userPortfolioDTO : userDTO.getPortfolios()) {
			if (userPortfolioDTO.getId().equals(portfolioId)) {
				portfolioDTO = userPortfolioDTO;
			}
		}

		validateResult(portfolioDTO);
		return portfolioDTO;
	}

	private void validateResult(PortfolioDTO portfolioDTO)
			throws PortfolioNotFoundException {
		if (portfolioDTO == null) {
			throw new PortfolioNotFoundException();
		}
	}
}
