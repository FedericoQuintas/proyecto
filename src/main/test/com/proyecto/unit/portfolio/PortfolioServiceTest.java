package com.proyecto.unit.portfolio;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.proyecto.common.SpringBaseTest;
import com.proyecto.portfolio.domain.Portfolio;
import com.proyecto.portfolio.service.PortfolioService;
import com.proyecto.rest.resource.user.dto.PortfolioDTO;
import com.proyecto.unit.portfolio.helper.PortfolioHelper;

public class PortfolioServiceTest extends SpringBaseTest {

	@Resource
	private PortfolioService portfolioService;
	private Portfolio portfolio;

	@Before
	public void before() {
		storePortfolio();
	}

	@Test
	public void whenCreatesPortfolioThenPortfolioIsCreatedWithId() {

		Assert.assertNotNull(portfolio.getId());
	}

	@Test
	public void whenCreatesPortfolioThenPortfolioIsCreatedWithAListOfAssets() {

		Assert.assertNotNull(portfolio.getCurrentAssets());
	}

	private void storePortfolio() {

		PortfolioDTO portfolioDTO = PortfolioHelper.createDefaultDTO();

		portfolio = portfolioService.store(portfolioDTO);
	}

}
