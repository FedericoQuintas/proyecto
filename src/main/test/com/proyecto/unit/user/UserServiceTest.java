package com.proyecto.unit.user;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.proyecto.asset.exception.AssetNotFoundException;
import com.proyecto.asset.exception.InvalidAssetArgumentException;
import com.proyecto.asset.service.AssetService;
import com.proyecto.common.SpringBaseTest;
import com.proyecto.common.error.InvertarErrorCode;
import com.proyecto.common.exception.ApplicationServiceException;
import com.proyecto.rest.resource.asset.dto.AssetDTO;
import com.proyecto.rest.resource.user.dto.InvertarUserDTO;
import com.proyecto.rest.resource.user.dto.PortfolioDTO;
import com.proyecto.rest.resource.user.dto.TransactionDTO;
import com.proyecto.rest.resource.user.dto.UserAssetDTO;
import com.proyecto.unit.asset.helper.AssetHelper;
import com.proyecto.unit.user.helper.PortfolioHelper;
import com.proyecto.unit.user.helper.TransactionHelper;
import com.proyecto.unit.user.helper.UserHelper;
import com.proyecto.user.domain.valueobject.MarketValueVO;
import com.proyecto.user.exception.InvalidPortfolioArgumentException;
import com.proyecto.user.exception.PortfolioNotFoundException;
import com.proyecto.user.exception.UserNotFoundException;
import com.proyecto.user.service.UserService;

public class UserServiceTest extends SpringBaseTest {

	@Resource
	private UserService userService;

	@Resource
	private AssetService assetService;

	private InvertarUserDTO userDTO;

	@Before
	public void before() {
		storeUser();
	}

	@Test
	public void whenCreatesUserThenUserIsCreatedWithId() {

		Assert.assertNotNull(userDTO.getId());
	}

	@Test
	public void whenCreatesUserThenUserIsCreatedWithName() {

		Assert.assertNotNull(userDTO.getUsername());
	}

	@Test
	public void whenCreatesUserThenUserIsCreatedWithMail() {

		Assert.assertNotNull(userDTO.getMail());
	}

	@Test
	public void whenCreatesUserThenUserIsCreatedWithPortfolios() {

		Assert.assertNotNull(userDTO.getPortfolios());
	}

	@Test
	public void whenAsksForUserPortfoliosThenPortfoliosAreRetrieved()
			throws UserNotFoundException {

		List<PortfolioDTO> portfolios = userService.getPortfolios(userDTO
				.getId());

		Assert.assertNotNull(portfolios);
	}

	@Test
	public void whenAddsAPortfolioToAUserCollectionThenPortfolioIsAdded()
			throws UserNotFoundException, InvalidPortfolioArgumentException {

		addPortfolioToUser();

		InvertarUserDTO user = userService.findById(userDTO.getId());

		Assert.assertFalse(user.getPortfolios().isEmpty());
	}

	@Test
	public void whenAskForSpecificPortfolioThenPortfolioIsRetrieved()
			throws ApplicationServiceException {

		PortfolioDTO portfolioDTO = addPortfolioToUser();

		PortfolioDTO retrievedPortfolioDTO = userService.findPortfolioById(
				userDTO.getId(), portfolioDTO.getId());

		Assert.assertTrue(retrievedPortfolioDTO.getId().equals(
				portfolioDTO.getId()));
	}

	@Test
	public void whenAddsAPortfolioThenPortfolioIsAddedWithName()
			throws ApplicationServiceException {

		PortfolioDTO portfolioDTO = addPortfolioToUser();

		PortfolioDTO retrievedPortfolioDTO = userService.findPortfolioById(
				userDTO.getId(), portfolioDTO.getId());

		Assert.assertNotNull(retrievedPortfolioDTO.getName());
	}

	@Test
	public void whenAddsAPortfolioThenPortfolioIsAddedWithMarketValue()
			throws ApplicationServiceException {

		PortfolioDTO portfolioDTO = addPortfolioToUser();

		PortfolioDTO retrievedPortfolioDTO = userService.findPortfolioById(
				userDTO.getId(), portfolioDTO.getId());

		Assert.assertNotNull(retrievedPortfolioDTO.getMarketValue());
	}

	private PortfolioDTO addPortfolioToUser() throws UserNotFoundException,
			InvalidPortfolioArgumentException {
		PortfolioDTO portfolioDTO = PortfolioHelper.createDefaultDTO();

		return userService.addPortfolio(portfolioDTO, userDTO.getId());
	}

	@Test
	public void whenSearchAnUserByIdThenUserIsRetrieved()
			throws UserNotFoundException {

		InvertarUserDTO storedUserDTO = userService.findById(userDTO.getId());

		Assert.assertTrue(userDTO.getId().equals(storedUserDTO.getId()));

	}

	@Test
	public void whenAsksForAPortfolioThenPortfolioHasPerformance()
			throws ApplicationServiceException {

		PortfolioDTO portfolioDTO = PortfolioHelper.createDefaultDTO();

		portfolioDTO = userService.addPortfolio(portfolioDTO, userDTO.getId());

		PortfolioDTO retrievedPortfolioDTO = userService.findPortfolioById(
				userDTO.getId(), portfolioDTO.getId());

		Assert.assertNotNull(retrievedPortfolioDTO.getPerformance());

	}

	@Test
	public void whenAsksForAPortfolioThenPortfolioHasLastSessionPerformance()
			throws ApplicationServiceException {

		PortfolioDTO portfolioDTO = PortfolioHelper.createDefaultDTO();

		portfolioDTO = userService.addPortfolio(portfolioDTO, userDTO.getId());

		PortfolioDTO retrievedPortfolioDTO = userService.findPortfolioById(
				userDTO.getId(), portfolioDTO.getId());

		Assert.assertNotNull(retrievedPortfolioDTO.getLastSessionPerformance());

	}

	@Test
	public void whenAsksForAUserPortfolioPerformanceThenUserPortfolioPerformanceIsRetrieved()
			throws UserNotFoundException, InvalidPortfolioArgumentException,
			PortfolioNotFoundException {

		PortfolioDTO portfolioDTO = PortfolioHelper.createDefaultDTO();

		portfolioDTO = userService.addPortfolio(portfolioDTO, userDTO.getId());

		Double portfolioPerformance = userService.getPortfolioPerformance(
				userDTO.getId(), portfolioDTO.getId());

		Assert.assertTrue(portfolioPerformance.equals(portfolioDTO
				.getPerformance()));

	}

	@Test
	public void whenAsksForAUserPortfoliosPerformanceThenUserPortfoliosPerformanceIsRetrieved()
			throws UserNotFoundException, InvalidPortfolioArgumentException {

		PortfolioDTO firstPortfolioDTO = PortfolioHelper.createDefaultDTO();

		firstPortfolioDTO = userService.addPortfolio(firstPortfolioDTO,
				userDTO.getId());

		PortfolioDTO secondPortfolioDTO = PortfolioHelper.createDefaultDTO();

		secondPortfolioDTO.setName("Second Portfolio Name");

		secondPortfolioDTO = userService.addPortfolio(secondPortfolioDTO,
				userDTO.getId());

		Double portfoliosPerformance = userService
				.getPortfoliosPerformance(userDTO.getId());

		Assert.assertTrue(portfoliosPerformance.equals(firstPortfolioDTO
				.getPerformance() + secondPortfolioDTO.getPerformance()));

	}

	@Test
	public void whenSearchAnUserByIdAndUserDoesNotExistThenUserExceptionIsThrown()
			throws UserNotFoundException {

		Long NOT_EXISTING_USER_ID = new Long(1000);

		try {
			userService.findById(NOT_EXISTING_USER_ID);
		} catch (ApplicationServiceException e) {
			Assert.assertTrue(e.getErrorCode().equals(
					InvertarErrorCode.OBJECT_NOT_FOUND));
		}

	}

	@Test
	public void whenAskForPortfolioMarketValueThenMarketValueIsRetrieved()
			throws UserNotFoundException, InvalidPortfolioArgumentException,
			PortfolioNotFoundException, AssetNotFoundException,
			InvalidAssetArgumentException {

		PortfolioDTO portfolioDTO = PortfolioHelper.createDefaultDTO();

		AssetDTO assetDTO = storeAsset();

		assetService.update(assetDTO);

		portfolioDTO.getUserAssets().get(0).setAssetId(assetDTO.getId());

		portfolioDTO = userService.addPortfolio(portfolioDTO, userDTO.getId());

		List<MarketValueVO> portfolioMarketValues = userService
				.getPortfolioMarketValue(userDTO.getId(), portfolioDTO.getId());

		Assert.assertNotNull(portfolioMarketValues);

	}

	@Test
	public void whenAUserBuysAUserAssetForFirstTimeThenAUserAssetIsBoughtAndAddedToPortfolio()
			throws ApplicationServiceException {

		PortfolioDTO portfolioDTO = PortfolioHelper.createDefaultDTO();

		AssetDTO assetDTO = storeAsset();

		assetService.update(assetDTO);

		portfolioDTO = userService.addPortfolio(portfolioDTO, userDTO.getId());

		TransactionDTO transactionDTO = TransactionHelper
				.createDefaultTransactionDTO();

		userService.buyUserAsset(transactionDTO, userDTO.getId(),
				portfolioDTO.getId());

		portfolioDTO = userService.findPortfolioById(userDTO.getId(),
				portfolioDTO.getId());

		Assert.assertFalse(portfolioDTO.getUserAssets().isEmpty());

	}

	@Test
	public void whenAUserBuysAUserAssetForFirstTimeThenAUserAssetIsAddedToPortfolioWithATransaction()
			throws ApplicationServiceException {

		PortfolioDTO portfolioDTO = PortfolioHelper.createDefaultDTO();

		AssetDTO assetDTO = storeAsset();

		assetService.update(assetDTO);

		portfolioDTO = userService.addPortfolio(portfolioDTO, userDTO.getId());

		TransactionDTO transactionDTO = TransactionHelper
				.createDefaultTransactionDTO();

		userService.buyUserAsset(transactionDTO, userDTO.getId(),
				portfolioDTO.getId());

		portfolioDTO = userService.findPortfolioById(userDTO.getId(),
				portfolioDTO.getId());

		Assert.assertFalse(portfolioDTO.getUserAssets().get(0)
				.getTransactions().isEmpty());

	}

	@Test
	public void whenAUserSellsAUserAssetThenATransactionIsCreated()
			throws ApplicationServiceException {

		PortfolioDTO portfolioDTO = PortfolioHelper.createDefaultDTO();

		AssetDTO assetDTO = storeAsset();

		assetService.update(assetDTO);

		UserAssetDTO usserAssetDTO = portfolioDTO.getUserAssets().get(0);

		usserAssetDTO.setAssetId(assetDTO.getId());

		portfolioDTO = userService.addPortfolio(portfolioDTO, userDTO.getId());

		TransactionDTO transactionDTO = TransactionHelper
				.createDefaultTransactionDTO();

		userService.buyUserAsset(transactionDTO, userDTO.getId(),
				portfolioDTO.getId());

		portfolioDTO = userService.findPortfolioById(userDTO.getId(),
				portfolioDTO.getId());

		Assert.assertFalse(usserAssetDTO.getTransactions().isEmpty());
	}

	private AssetDTO storeAsset() throws InvalidAssetArgumentException {
		AssetDTO assetDTO = AssetHelper.createDefaultAssetDTO();
		AssetDTO asset = assetService.store(assetDTO);
		return asset;
	}

	private void storeUser() {

		userDTO = UserHelper.createDefaultUserDTO();

		userDTO = userService.store(userDTO);
	}

}
