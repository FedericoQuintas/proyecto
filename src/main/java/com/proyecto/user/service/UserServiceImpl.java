package com.proyecto.user.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.proyecto.asset.exception.AssetNotFoundException;
import com.proyecto.asset.exception.InvalidAssetTypeException;
import com.proyecto.asset.service.AssetService;
import com.proyecto.common.exception.ApplicationServiceException;
import com.proyecto.common.exception.DomainException;
import com.proyecto.common.exception.ObjectNotFoundException;
import com.proyecto.rest.resource.asset.dto.AssetDTO;
import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;
import com.proyecto.rest.resource.user.dto.InvertarUserDTO;
import com.proyecto.rest.resource.user.dto.InvertarUserLoginDTO;
import com.proyecto.rest.resource.user.dto.PortfolioDTO;
import com.proyecto.rest.resource.user.dto.TheoreticalPortfolioDTO;
import com.proyecto.rest.resource.user.dto.TransactionDTO;
import com.proyecto.rest.resource.user.dto.UserAssetDTO;
import com.proyecto.user.domain.InvertarUser;
import com.proyecto.user.domain.InvestorProfile;
import com.proyecto.user.domain.Portfolio;
import com.proyecto.user.domain.PortfolioHistoryVO;
import com.proyecto.user.domain.TransactionType;
import com.proyecto.user.domain.factory.InvertarUserDTOFactory;
import com.proyecto.user.domain.factory.InvertarUserFactory;
import com.proyecto.user.domain.factory.PortfolioDTOFactory;
import com.proyecto.user.domain.factory.PortfolioFactory;
import com.proyecto.user.domain.service.PortfolioDomainService;
import com.proyecto.user.domain.valueobject.MarketValueVO;
import com.proyecto.user.exception.InvalidLoginException;
import com.proyecto.user.exception.InvalidPasswordException;
import com.proyecto.user.exception.InvalidPortfolioArgumentException;
import com.proyecto.user.exception.PortfolioNameAlreadyInUseException;
import com.proyecto.user.exception.PortfolioNotFoundException;
import com.proyecto.user.exception.UserMailAlreadyExistsException;
import com.proyecto.user.exception.UserNotFoundException;
import com.proyecto.user.exception.UsernameAlreadyExistsException;
import com.proyecto.user.persistence.UserDAO;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name = "userMongoDAO")
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
			throws UserNotFoundException, InvalidPortfolioArgumentException,
			PortfolioNameAlreadyInUseException {

		try {
			InvertarUser user = userDAO.findById(userId);
			Portfolio portfolio = PortfolioFactory.create(portfolioDTO,
					userDAO.nextPortfolioID());

			user.addPortfolio(portfolio);

			updateUser(user);

			return PortfolioDTOFactory.create(portfolio);
		} catch (ObjectNotFoundException e) {
			throw new UserNotFoundException(e);
		} catch (PortfolioNameAlreadyInUseException e) {
			throw new PortfolioNameAlreadyInUseException(e);
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
	public Float getPortfoliosPerformance(Long userId)
			throws UserNotFoundException, AssetNotFoundException,
			InvalidAssetTypeException {

		try {
			InvertarUser user = userDAO.findById(userId);
			Float portfoliosPerformance = new Float(0);

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
	public Float getPortfolioPerformance(Long userId, Long portfolioId)
			throws UserNotFoundException, PortfolioNotFoundException,
			AssetNotFoundException, InvalidAssetTypeException {
		try {
			InvertarUser user = userDAO.findById(userId);
			Portfolio portfolio = user.getPortfolio(portfolioId);
			Float performance = portfolioDomainService
					.calculatePerformance(portfolio);
			portfolio.setPerformance(performance);
			updateUser(user);
			return performance;
		} catch (ObjectNotFoundException e) {
			throw new UserNotFoundException(e);
		}

	}

	@Override
	public InvertarUserDTO store(InvertarUserDTO userDTO)
			throws InvalidPasswordException, UsernameAlreadyExistsException,
			UserMailAlreadyExistsException {

		String encryptedPassword = validatePassword(userDTO);

		validateUsername(userDTO.getUsername());

		validateMail(userDTO.getMail());

		InvertarUser user = InvertarUserFactory.create(userDAO.nextID(),
				userDTO.getUsername(), userDTO.getMail(), encryptedPassword);

		InvertarUser storedUser = userDAO.store(user);

		return InvertarUserDTOFactory.create(storedUser);

	}

	private void validateUsername(String username)
			throws UsernameAlreadyExistsException {
		if (userDAO.existsUserWithUsername(username)) {
			throw new UsernameAlreadyExistsException();
		}

	}

	private void validateMail(String mail)
			throws UserMailAlreadyExistsException {
		if (userDAO.existsUserWithMail(mail)) {
			throw new UserMailAlreadyExistsException();
		}

	}

	private String validatePassword(InvertarUserDTO userDTO)
			throws InvalidPasswordException {
		String encryptedPassword;
		try {
			validatePasswordNotNull(userDTO.getPassword());
			encryptedPassword = applySHAtoPassword(userDTO.getPassword());
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new InvalidPasswordException();
		}
		return encryptedPassword;
	}

	@Override
	public List<TheoreticalPortfolioDTO> getInvestorProfile(
			Integer amountOfPoints) {
		InvestorProfile.loadXmlFile();
		if (amountOfPoints <= 4) {
			return InvestorProfile.getConservativeInvestor();
		} else if (amountOfPoints <= 8) {
			return InvestorProfile.getModerateInvestor();
		} else {
			return InvestorProfile.getAgressiveInvestor();
		}
	}

	private void validatePasswordNotNull(String password)
			throws InvalidPasswordException {
		if (StringUtils.isBlank(password)) {
			throw new InvalidPasswordException();
		}

	}

	private String applySHAtoPassword(String password)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		MessageDigest md;
		md = MessageDigest.getInstance("SHA-1");
		byte[] sha1hash = new byte[40];
		md.update(password.getBytes("iso-8859-1"), 0, password.length());
		sha1hash = md.digest();
		return convertToHex(sha1hash);

	}

	private static String convertToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}

	@Override
	public List<MarketValueVO> getPortfolioMarketValue(Long userId,
			Long portfolioId) throws UserNotFoundException,
			PortfolioNotFoundException, AssetNotFoundException,
			InvalidAssetTypeException {
		try {
			InvertarUser user = userDAO.findById(userId);
			Portfolio portfolio = user.getPortfolio(portfolioId);
			return portfolioDomainService.calculateMarketValue(portfolio);
		} catch (ObjectNotFoundException e) {
			throw new UserNotFoundException(e);
		}

	}

	@Override
	public TransactionDTO addTransaction(TransactionDTO transactionDTO,
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

	@Override
	public InvertarUserDTO login(InvertarUserLoginDTO loginDTO)
			throws UserNotFoundException, InvalidPasswordException,
			InvalidLoginException {

		InvertarUser user = findByMail(loginDTO.getMail());

		String encryptInputPassword = encrypt(loginDTO.getPassword());

		validatePassword(user, encryptInputPassword);

		return InvertarUserDTOFactory.create(user);
	}

	private String encrypt(String password) throws InvalidPasswordException {
		try {
			return applySHAtoPassword(password);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new InvalidPasswordException();
		}
	}

	private void validatePassword(InvertarUser user, String encryptInputPassword)
			throws InvalidLoginException {
		if (!user.getPassword().equals(encryptInputPassword)) {
			throw new InvalidLoginException();
		}
	}

	private InvertarUser findByMail(String mail) throws UserNotFoundException {
		try {
			return userDAO.findByMail(mail);
		} catch (ObjectNotFoundException e) {
			throw new UserNotFoundException(e);
		}
	}

	@Override
	public Map<Long, List<PortfolioHistoryVO>> getPortfoliosHistories(
			Long userId) throws ApplicationServiceException {

		List<PortfolioDTO> portfolios = getPortfolios(userId);

		List<PortfolioHistoryVO> portfolioHistoryVOs = new ArrayList<PortfolioHistoryVO>();

		Map<Long, List<PortfolioHistoryVO>> portfoliosByDates = new HashMap<Long, List<PortfolioHistoryVO>>();

		for (PortfolioDTO portfolioDTO : portfolios) {

			portfoliosByDates.put(portfolioDTO.getId(), portfolioHistoryVOs);

			Date specificDate = obtainEarliestTradingDate(portfolioDTO);

			Float marketValue = new Float(0);

			while (!isToday(specificDate)) {

				PortfolioHistoryVO portfolioHistoryVO = new PortfolioHistoryVO();

				portfolioHistoryVO.setDate(specificDate);

				List<UserAssetDTO> userAssets = portfolioDTO.getUserAssets();

				Map<Long, Double> pricesByAsset = obtainPricesByAsset(
						specificDate, userAssets);

				portfolioHistoryVO.setPricesByAsset(pricesByAsset);

				obtainTransactions(specificDate, userAssets, portfolioHistoryVO);

				marketValue = calculateUpdatedMarketValue(marketValue,
						portfolioHistoryVO);

				portfolioHistoryVOs.add(portfolioHistoryVO);

				incrementDay(specificDate);

				portfolioHistoryVO.setMarketValue(marketValue);
			}
		}

		return portfoliosByDates;
	}

	private Float calculateUpdatedMarketValue(Float marketValue,
			PortfolioHistoryVO portfolioHistoryVO) {

		Float valueAccumulated = new Float(0);
		valueAccumulated = accumulatePurchasingTransactionsValues(
				portfolioHistoryVO, valueAccumulated);

		valueAccumulated = accumulateSellingTransactionsValues(
				portfolioHistoryVO, valueAccumulated);
		return marketValue + valueAccumulated;
	}

	private Float accumulatePurchasingTransactionsValues(
			PortfolioHistoryVO portfolioHistoryVO, Float valueAccumulated) {
		for (TransactionDTO transaction : portfolioHistoryVO
				.getPurchasingTransactions()) {

			valueAccumulated = valueAccumulated + transaction.getQuantity()
					* transaction.getPricePaid();
		}
		return valueAccumulated;
	}

	private Float accumulateSellingTransactionsValues(
			PortfolioHistoryVO portfolioHistoryVO, Float valueAccumulated) {
		for (TransactionDTO transaction : portfolioHistoryVO
				.getSellingTransactions()) {

			valueAccumulated = valueAccumulated - transaction.getQuantity()
					* transaction.getPricePaid();
		}
		return valueAccumulated;
	}

	private void incrementDay(Date specificDate) {
		specificDate.setTime(specificDate.getTime() + 24 * 60 * 60 * 1000);
	}

	@SuppressWarnings("deprecation")
	private boolean isToday(Date earliestDate) {
		return new Date().getDay() == earliestDate.getDay()
				&& new Date().getYear() == earliestDate.getYear();
	}

	private Map<Long, Double> obtainPricesByAsset(Date date,
			List<UserAssetDTO> userAssets) throws AssetNotFoundException,
			InvalidAssetTypeException {
		Map<Long, Double> pricesByAsset = new HashMap<Long, Double>();

		for (UserAssetDTO userAssetDTO : userAssets) {

			AssetDTO asset = assetService.findById(userAssetDTO.getAssetId());

			for (TradingSessionDTO tradingSessionDTO : asset
					.getTradingSessions()) {
				if (tradingSessionDTO.getTradingDate().equals(date)) {
					Double closingPrice = tradingSessionDTO.getClosingPrice();
					pricesByAsset.put(asset.getId(), closingPrice);
				}

			}

		}
		return pricesByAsset;
	}

	private void obtainTransactions(Date earliestDate,
			List<UserAssetDTO> userAssets, PortfolioHistoryVO portfolioHistoryVO) {
		for (UserAssetDTO userAssetDTO : userAssets) {
			List<TransactionDTO> sellingTransactionsByDateAndAsset = obtainSellingTransactionsByDateAndAsset(
					userAssetDTO, earliestDate);
			portfolioHistoryVO
					.setSellingTransactions(sellingTransactionsByDateAndAsset);
			List<TransactionDTO> purchasingTransactionsByDateAndAsset = obtainPurchasingTransactionsByDateAndAsset(
					userAssetDTO, earliestDate);
			portfolioHistoryVO
					.setPurchasingTransactions(purchasingTransactionsByDateAndAsset);
		}
	}

	private List<TransactionDTO> obtainSellingTransactionsByDateAndAsset(
			UserAssetDTO userAssetDTO, Date specificDate) {

		List<TransactionDTO> sellingTransaction = new ArrayList<TransactionDTO>();

		for (TransactionDTO transactionDTO : userAssetDTO.getTransactions()) {
			if (transactionDTO.getTradingDate().equals(specificDate)) {
				if (transactionDTO.getType().equals(TransactionType.SELL)) {
					sellingTransaction.add(transactionDTO);
				}
			}
		}
		return sellingTransaction;

	}

	private List<TransactionDTO> obtainPurchasingTransactionsByDateAndAsset(
			UserAssetDTO userAssetDTO, Date specificDate) {

		List<TransactionDTO> purchasingTransaction = new ArrayList<TransactionDTO>();

		for (TransactionDTO transactionDTO : userAssetDTO.getTransactions()) {
			if (transactionDTO.getTradingDate().equals(specificDate)) {
				if (transactionDTO.getType().equals(TransactionType.PURCHASE)) {
					purchasingTransaction.add(transactionDTO);
				}
			}
		}
		return purchasingTransaction;
	}

	private Date obtainEarliestTradingDate(PortfolioDTO portfolioDTO) {

		List<TransactionDTO> transactions = obtainAllTheTransactions(portfolioDTO);
		Date earliestDate = new Date();

		for (TransactionDTO transactionDTO : transactions) {
			Date tradingDate = transactionDTO.getTradingDate();
			if (tradingDate.before(earliestDate)) {
				earliestDate = tradingDate;
			}

		}
		return earliestDate;
	}

	private List<TransactionDTO> obtainAllTheTransactions(
			PortfolioDTO portfolioDTO) {
		List<TransactionDTO> transactions = new ArrayList<TransactionDTO>();
		List<UserAssetDTO> userAssets = portfolioDTO.getUserAssets();
		for (UserAssetDTO userAssetDTO : userAssets) {
			transactions.addAll(userAssetDTO.getTransactions());
		}
		return transactions;
	}
}
