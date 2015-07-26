package com.proyecto.user.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.proyecto.asset.exception.AssetNotFoundException;
import com.proyecto.asset.service.AssetService;
import com.proyecto.common.exception.ApplicationServiceException;
import com.proyecto.common.exception.DomainException;
import com.proyecto.common.exception.ObjectNotFoundException;
import com.proyecto.rest.resource.user.dto.InvertarUserDTO;
import com.proyecto.rest.resource.user.dto.InvertarUserLoginDTO;
import com.proyecto.rest.resource.user.dto.PortfolioDTO;
import com.proyecto.rest.resource.user.dto.TheoreticalPortfolioDTO;
import com.proyecto.rest.resource.user.dto.TransactionDTO;
import com.proyecto.user.domain.InvertarUser;
import com.proyecto.user.domain.InvestorProfile;
import com.proyecto.user.domain.InvestorProfileEnum;
import com.proyecto.user.domain.Portfolio;
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
import com.proyecto.user.exception.UserNotFoundException;
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
			throws UserNotFoundException, AssetNotFoundException {

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
			AssetNotFoundException {
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
			throws InvalidPasswordException {

		String encryptedPassword;
		try {
			validatePassword(userDTO.getPassword());
			encryptedPassword = applySHAtoPassword(userDTO.getPassword());
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new InvalidPasswordException();
		}

		InvertarUser user = InvertarUserFactory.create(userDAO.nextID(),
				userDTO.getUsername(), userDTO.getMail(), encryptedPassword);

		InvertarUser storedUser = userDAO.store(user);

		return InvertarUserDTOFactory.create(storedUser);

	}
	
	@Override
	public List<TheoreticalPortfolioDTO> getInvestorProfile(Integer amountOfPoints){
		InvestorProfile.loadXmlFile();
		if(amountOfPoints<=4){
			return InvestorProfile.getConservativeInvestor();
		}else if(amountOfPoints<=8){
			return InvestorProfile.getModerateInvestor();
		}else{
			return InvestorProfile.getAgressiveInvestor();
		}
	}

	private void validatePassword(String password)
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

}
