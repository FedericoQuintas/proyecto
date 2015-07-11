package com.proyecto.unit.user;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.proyecto.asset.exception.AssetNotFoundException;
import com.proyecto.asset.exception.DBAccessException;
import com.proyecto.asset.exception.InvalidAssetArgumentException;
import com.proyecto.asset.exception.InvalidTradingSessionArgumentException;
import com.proyecto.asset.service.AssetService;
import com.proyecto.common.SpringBaseTest;
import com.proyecto.common.currency.InvertarCurrency;
import com.proyecto.common.error.InvertarErrorCode;
import com.proyecto.common.exception.ApplicationServiceException;
import com.proyecto.rest.resource.asset.dto.AssetDTO;
import com.proyecto.rest.resource.user.dto.InvertarUserDTO;
import com.proyecto.rest.resource.user.dto.InvertarUserLoginDTO;
import com.proyecto.rest.resource.user.dto.PortfolioDTO;
import com.proyecto.rest.resource.user.dto.TransactionDTO;
import com.proyecto.unit.asset.helper.AssetHelper;
import com.proyecto.unit.user.helper.PortfolioHelper;
import com.proyecto.unit.user.helper.TransactionHelper;
import com.proyecto.unit.user.helper.UserHelper;
import com.proyecto.user.domain.TransactionType;
import com.proyecto.user.domain.valueobject.MarketValueVO;
import com.proyecto.user.exception.InvalidLoginException;
import com.proyecto.user.exception.InvalidPasswordException;
import com.proyecto.user.exception.InvalidPortfolioArgumentException;
import com.proyecto.user.exception.PortfolioNameAlreadyInUseException;
import com.proyecto.user.exception.PortfolioNotFoundException;
import com.proyecto.user.exception.UserNotFoundException;
import com.proyecto.user.service.UserService;
import com.proyecto.yahoofinance.service.YahooFinanceInformationService;

public class UserServiceTest extends SpringBaseTest {

	@Resource
	private UserService userService;

	@Resource
	private YahooFinanceInformationService yahooService;

	@Resource
	private AssetService assetService;

	private InvertarUserDTO userDTO;

	private AssetDTO assetDTO;

	@Before
	public void before() throws InvalidAssetArgumentException,
			InvalidTradingSessionArgumentException, InvalidPasswordException,
			DBAccessException {
		storeUser();
		storeAsset();
	}

	@Test
	public void whenCreatesUserThenUserIsCreatedWithId() {

		Assert.assertNotNull(userDTO.getId());
	}

	@Test(expected = InvalidPasswordException.class)
	public void whenCreatesUserWithNoPasswordThenExceptionIsThrown()
			throws InvalidPasswordException {

		InvertarUserDTO userDTO = UserHelper.createDefaultUserDTO();
		userDTO.setPassword(null);

		userDTO = userService.store(userDTO);

	}

	@Test
	public void whenUserLogInWithCorrectPasswordThenUserIsLoggedIn()
			throws UserNotFoundException, InvalidPasswordException,
			InvalidLoginException {

		InvertarUserLoginDTO loginDTO = UserHelper.createDefaultLoginDTO();

		Assert.assertNotNull(userService.login(loginDTO));
	}

	@Test(expected = InvalidLoginException.class)
	public void whenUserLogInWithIncorrectPasswordThenUserIsLoggedIn()
			throws UserNotFoundException, InvalidPasswordException,
			InvalidLoginException {

		InvertarUserLoginDTO loginDTO = UserHelper.createDefaultLoginDTO();

		loginDTO.setPassword("invalidpassword");

		userService.login(loginDTO);

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
			throws UserNotFoundException, InvalidPortfolioArgumentException,
			PortfolioNameAlreadyInUseException {

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

	private PortfolioDTO addPortfolioToUser() throws UserNotFoundException,
			InvalidPortfolioArgumentException,
			PortfolioNameAlreadyInUseException {
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

		userService.getPortfolioPerformance(userDTO.getId(),
				retrievedPortfolioDTO.getId());

		retrievedPortfolioDTO = userService.findPortfolioById(userDTO.getId(),
				portfolioDTO.getId());

		Assert.assertNotNull(retrievedPortfolioDTO.getPerformance());

	}

	@Test
	public void whenAsksForAUserPortfoliosPerformanceThenUserPortfoliosPerformanceIsRetrieved()
			throws UserNotFoundException, InvalidPortfolioArgumentException,
			AssetNotFoundException, PortfolioNameAlreadyInUseException {

		PortfolioDTO firstPortfolioDTO = PortfolioHelper.createDefaultDTO();

		firstPortfolioDTO = userService.addPortfolio(firstPortfolioDTO,
				userDTO.getId());

		PortfolioDTO secondPortfolioDTO = PortfolioHelper.createDefaultDTO();

		secondPortfolioDTO.setName("Second Portfolio Name");

		secondPortfolioDTO = userService.addPortfolio(secondPortfolioDTO,
				userDTO.getId());

		Float portfoliosPerformance = userService
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
			InvalidAssetArgumentException,
			InvalidTradingSessionArgumentException,
			PortfolioNameAlreadyInUseException {

		PortfolioDTO portfolioDTO = PortfolioHelper.createDefaultDTO();

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

		assetService.update(assetDTO);

		portfolioDTO = userService.addPortfolio(portfolioDTO, userDTO.getId());

		TransactionDTO transactionDTO = TransactionHelper
				.createDefaultTransactionDTO();
		transactionDTO.setAssetId(assetDTO.getId());

		userService.addTransaction(transactionDTO, userDTO.getId(),
				portfolioDTO.getId());

		portfolioDTO = userService.findPortfolioById(userDTO.getId(),
				portfolioDTO.getId());

		Assert.assertFalse(portfolioDTO.getUserAssets().isEmpty());

	}

	@Test
	public void whenAUserBuysAUserAssetForFirstTimeThenAUserAssetIsAddedToPortfolioWithATransaction()
			throws ApplicationServiceException {

		PortfolioDTO portfolioDTO = PortfolioHelper.createDefaultDTO();

		assetService.update(assetDTO);

		portfolioDTO = userService.addPortfolio(portfolioDTO, userDTO.getId());

		TransactionDTO transactionDTO = TransactionHelper
				.createDefaultTransactionDTO();

		userService.addTransaction(transactionDTO, userDTO.getId(),
				portfolioDTO.getId());

		portfolioDTO = userService.findPortfolioById(userDTO.getId(),
				portfolioDTO.getId());

		Assert.assertFalse(portfolioDTO.getUserAssets().get(0)
				.getTransactions().isEmpty());

	}

	@Test
	public void whenAsksForAUserPortfolioPerformanceWithOneAssetThenUserPortfolioPerformanceIsCorrectlyRetrieved()
			throws ApplicationServiceException {

		yahooService.update();

		PortfolioDTO portfolioDTO = PortfolioHelper.createDefaultDTO();

		portfolioDTO = userService.addPortfolio(portfolioDTO, userDTO.getId());

		TransactionDTO transactionDTO = TransactionHelper
				.createDefaultTransactionDTO();

		userService.addTransaction(transactionDTO, userDTO.getId(),
				portfolioDTO.getId());

		TransactionDTO secondTransactionDTO = TransactionHelper
				.createDefaultTransactionDTO();

		secondTransactionDTO.setType(TransactionType.SELL);
		secondTransactionDTO.setQuantity(new Long(5));
		secondTransactionDTO.setPricePaid(new Float(1));

		userService.addTransaction(secondTransactionDTO, userDTO.getId(),
				portfolioDTO.getId());

		portfolioDTO = userService.findPortfolioById(userDTO.getId(),
				portfolioDTO.getId());

		Float portfolioPerformance = userService.getPortfolioPerformance(
				userDTO.getId(), portfolioDTO.getId());

		Float expectedPerformance = calculateExpectedPerformance(
				transactionDTO, secondTransactionDTO);

		Assert.assertTrue(portfolioPerformance.equals(expectedPerformance));

	}

	@Test
	public void whenAsksForAUserPortfolioPerformanceWithTwoAssetsThenUserPortfolioPerformanceIsCorrectlyRetrieved()
			throws ApplicationServiceException {

		AssetDTO secondAssetDTO = AssetHelper.createDefaultAssetDTO();
		secondAssetDTO = assetService.store(secondAssetDTO);

		yahooService.update();

		PortfolioDTO portfolioDTO = PortfolioHelper.createDefaultDTO();

		portfolioDTO = userService.addPortfolio(portfolioDTO, userDTO.getId());

		TransactionDTO transactionDTO = TransactionHelper
				.createDefaultTransactionDTO();

		userService.addTransaction(transactionDTO, userDTO.getId(),
				portfolioDTO.getId());

		TransactionDTO secondTransactionDTO = TransactionHelper
				.createDefaultTransactionDTO();

		secondTransactionDTO.setType(TransactionType.SELL);
		secondTransactionDTO.setQuantity(new Long(5));
		secondTransactionDTO.setPricePaid(new Float(1));

		userService.addTransaction(secondTransactionDTO, userDTO.getId(),
				portfolioDTO.getId());

		TransactionDTO thirdTransactionDTO = TransactionHelper
				.createDefaultTransactionDTO();

		thirdTransactionDTO.setAssetId(secondAssetDTO.getId());

		userService.addTransaction(thirdTransactionDTO, userDTO.getId(),
				portfolioDTO.getId());

		TransactionDTO fourthTransactionDTO = TransactionHelper
				.createDefaultTransactionDTO();

		fourthTransactionDTO.setAssetId(secondAssetDTO.getId());

		fourthTransactionDTO.setType(TransactionType.SELL);
		fourthTransactionDTO.setQuantity(new Long(5));
		fourthTransactionDTO.setPricePaid(new Float(1));

		userService.addTransaction(fourthTransactionDTO, userDTO.getId(),
				portfolioDTO.getId());

		portfolioDTO = userService.findPortfolioById(userDTO.getId(),
				portfolioDTO.getId());

		Float portfolioPerformance = userService.getPortfolioPerformance(
				userDTO.getId(), portfolioDTO.getId());

		Float firstExpectedPerformance = calculateExpectedPerformance(
				transactionDTO, secondTransactionDTO);

		Float secondExpectedPerformance = calculateExpectedPerformance(
				thirdTransactionDTO, fourthTransactionDTO);

		Float expectedPerformance = (firstExpectedPerformance + secondExpectedPerformance)
				/ new Float(2);

		Assert.assertTrue(portfolioPerformance.equals(expectedPerformance));

	}

	private Float calculateExpectedPerformance(TransactionDTO transactionDTO,
			TransactionDTO secondTransactionDTO) throws AssetNotFoundException {
		assetDTO = assetService.findById(assetDTO.getId());

		Float quantityOwned = transactionDTO.getQuantity().floatValue()
				- secondTransactionDTO.getQuantity().floatValue();
		Float earnedMoney = secondTransactionDTO.getPricePaid().floatValue();
		Float investedMoney = transactionDTO.getPricePaid().floatValue();

		Float actualValue = quantityOwned
				* assetDTO.getLastTradingPrice().floatValue() + earnedMoney;
		Float expectedPerformance = (actualValue / investedMoney - new Float(1))
				* new Float(100);
		return expectedPerformance;
	}

	@Test
	public void whenAUserBuysAUserAssetForFirstTimeThenAUserAssetIsAddedToPortfolioWithAPurchaseTransaction()
			throws ApplicationServiceException {

		PortfolioDTO portfolioDTO = PortfolioHelper.createDefaultDTO();

		assetService.update(assetDTO);

		portfolioDTO = userService.addPortfolio(portfolioDTO, userDTO.getId());

		TransactionDTO transactionDTO = TransactionHelper
				.createDefaultTransactionDTO();

		userService.addTransaction(transactionDTO, userDTO.getId(),
				portfolioDTO.getId());

		portfolioDTO = userService.findPortfolioById(userDTO.getId(),
				portfolioDTO.getId());

		TransactionDTO storedTransactionDTO = portfolioDTO.getUserAssets()
				.get(0).getTransactions().get(0);
		Assert.assertTrue(storedTransactionDTO.getType().equals(
				TransactionType.PURCHASE));

	}

	@Test
	public void whenAUserHasOneUserAssetThenPortfolioMarketValueIsTheAssetLastPriceMultipliedByTheQuantityHeOwns()
			throws ApplicationServiceException {

		PortfolioDTO portfolioDTO = PortfolioHelper.createDefaultDTO();

		yahooService.update();

		AssetDTO updatedAssetDTO = assetService.findById(assetDTO.getId());

		assetService.update(updatedAssetDTO);

		portfolioDTO = userService.addPortfolio(portfolioDTO, userDTO.getId());

		TransactionDTO transactionDTO = TransactionHelper
				.createDefaultTransactionDTO();

		transactionDTO.setPricePaid(updatedAssetDTO.getLastTradingPrice());

		userService.addTransaction(transactionDTO, userDTO.getId(),
				portfolioDTO.getId());

		portfolioDTO = userService.findPortfolioById(userDTO.getId(),
				portfolioDTO.getId());

		List<MarketValueVO> portfolioMarketValue = userService
				.getPortfolioMarketValue(userDTO.getId(), portfolioDTO.getId());

		TransactionDTO storedTransactionDTO = portfolioDTO.getUserAssets()
				.get(0).getTransactions().get(0);

		Float pricePaid = storedTransactionDTO.getPricePaid();
		Float value = pricePaid
				* storedTransactionDTO.getQuantity().floatValue();
		Assert.assertTrue(value.equals(portfolioMarketValue.get(0).getValue()));

	}

	@Test
	public void whenAUserHasTwoUserAssetsThenPortfolioMarketValueIsTheSumOfTheirLastPrice()
			throws ApplicationServiceException {

		AssetDTO secondAssetDTO = AssetHelper.createDefaultAssetDTO();
		secondAssetDTO.setCurrency(InvertarCurrency.ARS);
		secondAssetDTO = assetService.store(secondAssetDTO);

		PortfolioDTO portfolioDTO = PortfolioHelper.createDefaultDTO();

		yahooService.update();

		AssetDTO updatedAssetDTO = assetService.findById(assetDTO.getId());

		assetService.update(updatedAssetDTO);

		AssetDTO updatedSecondAssetDTO = assetService.findById(secondAssetDTO
				.getId());

		assetService.update(updatedSecondAssetDTO);

		portfolioDTO = userService.addPortfolio(portfolioDTO, userDTO.getId());

		TransactionDTO transactionDTO = TransactionHelper
				.createDefaultTransactionDTO();

		transactionDTO.setPricePaid(updatedAssetDTO.getLastTradingPrice());

		userService.addTransaction(transactionDTO, userDTO.getId(),
				portfolioDTO.getId());

		TransactionDTO secondTransactionDTO = TransactionHelper
				.createDefaultTransactionDTO();

		secondTransactionDTO.setAssetId(secondAssetDTO.getId());

		secondTransactionDTO.setPricePaid(updatedSecondAssetDTO
				.getLastTradingPrice());

		userService.addTransaction(secondTransactionDTO, userDTO.getId(),
				portfolioDTO.getId());

		portfolioDTO = userService.findPortfolioById(userDTO.getId(),
				portfolioDTO.getId());

		List<MarketValueVO> portfolioMarketValue = userService
				.getPortfolioMarketValue(userDTO.getId(), portfolioDTO.getId());

		Assert.assertTrue(portfolioMarketValue.size() == 2);

	}

	@Test
	public void whenAUserSellsAUserAssetThatDoesNotHaveThenExceptionIsThrown()
			throws ApplicationServiceException {

		PortfolioDTO portfolioDTO = PortfolioHelper.createDefaultDTO();

		assetService.update(assetDTO);

		portfolioDTO = userService.addPortfolio(portfolioDTO, userDTO.getId());

		TransactionDTO transactionDTO = TransactionHelper
				.createDefaultTransactionDTO();

		transactionDTO.setType(TransactionType.SELL);

		try {
			userService.addTransaction(transactionDTO, userDTO.getId(),
					portfolioDTO.getId());
		} catch (ApplicationServiceException e) {
			Assert.assertTrue(e.getErrorCode().equals(
					InvertarErrorCode.OBJECT_NOT_FOUND));
		}

	}

	@Test
	public void whenAUserBuysAUserAssetForSecondTimeThenUserAssetHasTwoTransactions()
			throws ApplicationServiceException {

		PortfolioDTO portfolioDTO = PortfolioHelper.createDefaultDTO();

		assetService.update(assetDTO);

		portfolioDTO = userService.addPortfolio(portfolioDTO, userDTO.getId());

		TransactionDTO transactionDTO = TransactionHelper
				.createDefaultTransactionDTO();

		userService.addTransaction(transactionDTO, userDTO.getId(),
				portfolioDTO.getId());

		userService.addTransaction(transactionDTO, userDTO.getId(),
				portfolioDTO.getId());

		portfolioDTO = userService.findPortfolioById(userDTO.getId(),
				portfolioDTO.getId());

		Integer transactionsSize = portfolioDTO.getUserAssets().get(0)
				.getTransactions().size();
		Assert.assertTrue(transactionsSize.equals(new Integer(2)));

	}

	@Test(expected = AssetNotFoundException.class)
	public void whenAUserBuysAUserAssetThatDoesNotExistThenExceptionIsThrown()
			throws ApplicationServiceException {

		PortfolioDTO portfolioDTO = PortfolioHelper.createDefaultDTO();

		assetService.update(assetDTO);

		portfolioDTO = userService.addPortfolio(portfolioDTO, userDTO.getId());

		TransactionDTO transactionDTO = TransactionHelper
				.createDefaultTransactionDTO();

		Long invalidAssetId = new Long(1000);
		transactionDTO.setAssetId(invalidAssetId);

		userService.addTransaction(transactionDTO, userDTO.getId(),
				portfolioDTO.getId());

	}

	@Test(expected = PortfolioNameAlreadyInUseException.class)
	public void whenStoringTwoPortfoliosWithSameNameThenExceptionIsThrown()
			throws UserNotFoundException, InvalidPortfolioArgumentException,
			PortfolioNameAlreadyInUseException {
		addPortfolioToUser();
		addPortfolioToUser();
	}

	@Test
	public void whenStoringTwoPortfolioWithDifferentNamesThenNothingBadHappens()
			throws UserNotFoundException, InvalidPortfolioArgumentException,
			PortfolioNameAlreadyInUseException {
		PortfolioDTO portfolioDTO = PortfolioHelper.createDefaultDTO();
		userService.addPortfolio(portfolioDTO, userDTO.getId());
		portfolioDTO.setName("Portfolio2");
		userService.addPortfolio(portfolioDTO, userDTO.getId());
	}

	private void storeAsset() throws InvalidAssetArgumentException,
			InvalidTradingSessionArgumentException, DBAccessException {

		assetDTO = AssetHelper.createDefaultAssetDTO();
		assetDTO = assetService.store(assetDTO);
	}

	private void storeUser() throws InvalidPasswordException {

		userDTO = UserHelper.createDefaultUserDTO();

		userDTO = userService.store(userDTO);

	}

}
