package com.proyecto.unit.portfolio;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.proyecto.asset.exception.InvalidAssetArgumentException;
import com.proyecto.common.SpringBaseTest;
import com.proyecto.common.error.InvertarErrorCode;
import com.proyecto.common.exception.ApplicationServiceException;
import com.proyecto.portfolio.exception.PortfolioNotFoundException;
import com.proyecto.portfolio.service.PortfolioService;
import com.proyecto.rest.resource.portfolio.dto.PortfolioDTO;
import com.proyecto.unit.portfolio.helper.PortfolioHelper;
import com.proyecto.user.exception.UserNotFoundException;

public class PortfolioServiceTest extends SpringBaseTest {

	@Resource
	private PortfolioService portfolioService;

	private PortfolioDTO portfolioDTO;

	@Before
	public void before() throws InvalidAssetArgumentException {
		storePortfolio();
	}

	@Test
	public void whenCreatesPortfolioThenPortfolioIsCreatedWithId() {

		Assert.assertNotNull(portfolioDTO.getId());
	}

	@Test
	public void whenCreatesPortfolioThenPortfolioIsCreatedWithAListOfAssets() {

		Assert.assertNotNull(portfolioDTO.getCurrentAssets());
	}

	@Test
	public void whenCreatesPortfolioThenPortfolioIsCreatedWithName() {

		PortfolioDTO incompletePortfolioDTO = PortfolioHelper
				.createDefaultDTO();

		incompletePortfolioDTO.setName(null);

		try {
			portfolioService.store(incompletePortfolioDTO);
		} catch (ApplicationServiceException e) {
			Assert.assertTrue(e.getErrorCode().equals(
					InvertarErrorCode.INVALID_ARGUMENT));
		}
	}

	@Test
	public void whenSearchAPortolioByIdThenPortfolioIsRetrieved()
			throws PortfolioNotFoundException {

		PortfolioDTO storedPortfolioDTO = portfolioService
				.findById(portfolioDTO.getId());

		Assert.assertTrue(portfolioDTO.getId().equals(
				storedPortfolioDTO.getId()));

	}

	@Test
	public void whenSearchAnUserByIdAndUserDoesNotExistThenUserExceptionIsThrown()
			throws UserNotFoundException {

		Long NOT_EXISTING_PORTFOLIO_ID = new Long(1000);

		try {
			portfolioService.findById(NOT_EXISTING_PORTFOLIO_ID);
		} catch (ApplicationServiceException e) {
			Assert.assertTrue(e.getErrorCode().equals(
					InvertarErrorCode.OBJECT_NOT_FOUND));
		}

	}

	private void storePortfolio() throws InvalidAssetArgumentException {

		portfolioDTO = PortfolioHelper.createDefaultDTO();

		portfolioDTO = portfolioService.store(portfolioDTO);
	}

}
