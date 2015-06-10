package com.proyecto.portfolio.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.proyecto.asset.exception.InvalidAssetArgumentException;
import com.proyecto.portfolio.domain.Portfolio;
import com.proyecto.portfolio.domain.factory.PortfolioFactory;
import com.proyecto.portfolio.persistence.PortfolioDAO;
import com.proyecto.rest.resource.portfolio.dto.PortfolioDTO;
import com.proyecto.rest.resource.portfolio.dto.factory.PortfolioFactoryDTO;

@Service("portfolioService")
public class PortfolioServiceImpl implements PortfolioService {

	@Resource
	private PortfolioDAO portfolioDAO;

	@Override
	public PortfolioDTO store(PortfolioDTO portfolioDTO)
			throws InvalidAssetArgumentException {

		Portfolio portfolio = PortfolioFactory.create(portfolioDTO,
				portfolioDAO.nextID());

		Portfolio storedPortfolio = portfolioDAO.store(portfolio);

		return PortfolioFactoryDTO.create(storedPortfolio);
	}

}
