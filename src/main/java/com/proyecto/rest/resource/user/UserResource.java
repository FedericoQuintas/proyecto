package com.proyecto.rest.resource.user;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyecto.common.exception.ApplicationServiceException;
import com.proyecto.common.exception.ObjectNotFoundException;
import com.proyecto.rest.resource.user.dto.InvertarUserDTO;
import com.proyecto.rest.resource.user.dto.PortfolioDTO;
import com.proyecto.rest.resource.user.dto.TheoreticalPortfolioDTO;
import com.proyecto.rest.resource.user.dto.TransactionDTO;
import com.proyecto.user.domain.valueobject.MarketValueVO;
import com.proyecto.user.exception.InvalidPasswordException;
import com.proyecto.user.exception.UserMailAlreadyExistsException;
import com.proyecto.user.exception.UsernameAlreadyExistsException;
import com.proyecto.user.service.UserService;

@Controller("userResource")
@RequestMapping("/users")
public class UserResource {

	@Resource
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public InvertarUserDTO store(HttpSession session,
			@RequestBody InvertarUserDTO invertarUserDTO)
			throws InvalidPasswordException, ObjectNotFoundException,
			UsernameAlreadyExistsException, UserMailAlreadyExistsException {
		InvertarUserDTO user = userService.store(invertarUserDTO);
		session.setAttribute("MEMBER", user.getId());
		if (isAdmin(user)) {
			session.setAttribute("ROLE", "admin");
		}
		return user;
	}

	private boolean isAdmin(InvertarUserDTO user) {
		return user.getMail().equals("admin@invertar.com");
	}

	@RequestMapping(value = "/{user_id}", method = RequestMethod.GET)
	@ResponseBody
	public InvertarUserDTO findUserById(HttpSession session,
			@PathVariable("user_id") Long userId)
			throws ApplicationServiceException {
		checkSession(session, userId);

		return userService.findById(userId);
	}

	private void checkSession(HttpSession session, Long userId) {
		Long attribute = (Long) session.getAttribute("MEMBER");
		if (attribute == null || !attribute.equals(userId)) {
			throw new AccessDeniedException("Invalid session");
		}
	}

	@RequestMapping(value = "/{user_id}/portfolios", method = RequestMethod.GET)
	@ResponseBody
	public List<PortfolioDTO> getUserPortfolios(HttpSession session,
			@PathVariable("user_id") Long userId)
			throws ApplicationServiceException {
		checkSession(session, userId);
		return userService.getPortfolios(userId);
	}

	@RequestMapping(value = "/{user_id}/portfolios", method = RequestMethod.POST)
	@ResponseBody
	public PortfolioDTO store(HttpSession session,
			@RequestBody PortfolioDTO portfolioDTO,
			@PathVariable("user_id") Long userId)
			throws ApplicationServiceException {
		checkSession(session, userId);
		return userService.addPortfolio(portfolioDTO, userId);
	}

	@RequestMapping(value = "/{user_id}/portfolios/{portfolio_id}", method = RequestMethod.GET)
	@ResponseBody
	public PortfolioDTO getUserPortfolio(HttpSession session,
			@PathVariable("user_id") Long userId,
			@PathVariable("portfolio_id") Long portfolioId)
			throws ApplicationServiceException {
		checkSession(session, userId);
		return userService.findPortfolioById(userId, portfolioId);
	}

	@RequestMapping(value = "/{user_id}/portfolios/{portfolio_id}/marketValue", method = RequestMethod.GET)
	@ResponseBody
	public List<MarketValueVO> getUserPortfolioMarketValue(HttpSession session,
			@PathVariable("user_id") Long userId,
			@PathVariable("portfolio_id") Long portfolioId)
			throws ApplicationServiceException {
		checkSession(session, userId);
		return userService.getPortfolioMarketValue(userId, portfolioId);
	}

	@RequestMapping(value = "/{user_id}/portfolios/{portfolio_id}/transactions", method = RequestMethod.POST)
	@ResponseBody
	public TransactionDTO addTransaction(HttpSession session,
			@PathVariable("user_id") Long userId,
			@PathVariable("portfolio_id") Long portfolioId,
			@RequestBody TransactionDTO transactionDTO)
			throws ApplicationServiceException {
		checkSession(session, userId);
		return userService.addTransaction(transactionDTO, userId, portfolioId);
	}

	@RequestMapping(value = "/{user_id}/portfolios/{portfolio_id}/performance", method = RequestMethod.GET)
	@ResponseBody
	public Float getUserPortfolioPerformance(HttpSession session,
			@PathVariable("user_id") Long userId,
			@PathVariable("portfolio_id") Long portfolioId)
			throws ApplicationServiceException {
		checkSession(session, userId);
		return userService.getPortfolioPerformance(userId, portfolioId);
	}

	@RequestMapping(value = "/{user_id}/portfolios/performance", method = RequestMethod.GET)
	@ResponseBody
	public Float getUserPortfolioPerformance(HttpSession session,
			@PathVariable("user_id") Long userId)
			throws ApplicationServiceException {
		checkSession(session, userId);
		return userService.getPortfoliosPerformance(userId);
	}

	@RequestMapping(value = "/{user_id}/investorProfile", method = RequestMethod.POST)
	@ResponseBody
	public List<TheoreticalPortfolioDTO> getInvestorProfile(
			HttpSession session, @RequestBody Integer amountOfPoints) {
		return userService.getInvestorProfile(amountOfPoints);
	}

}
