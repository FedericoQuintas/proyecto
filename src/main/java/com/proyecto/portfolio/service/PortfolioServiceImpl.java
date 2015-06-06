package com.proyecto.portfolio.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.proyecto.portfolio.domain.Portfolio;
import com.proyecto.portfolio.domain.factory.PortfolioFactory;
import com.proyecto.portfolio.persistence.PortfolioDAO;
import com.proyecto.rest.resource.portfolio.dto.PortfolioDTO;

@Service("portfolioService")
public class PortfolioServiceImpl implements PortfolioService {

	@Resource
	private PortfolioDAO portfolioDAO;

	@Override
	public Portfolio store(PortfolioDTO portfolioDTO) {
		Portfolio portfolio = PortfolioFactory.create(portfolioDTO,
				portfolioDAO.nextID());

		return portfolioDAO.store(portfolio);
	}

}
