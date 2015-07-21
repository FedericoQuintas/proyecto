package com.proyecto.rest.resource.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyecto.common.exception.ApplicationServiceException;
import com.proyecto.rest.resource.user.dto.InvertarUserDTO;
import com.proyecto.rest.resource.user.dto.InvertarUserLoginDTO;
import com.proyecto.rest.resource.user.dto.PortfolioDTO;
import com.proyecto.rest.resource.user.dto.TheoreticalPortfolioDTO;
import com.proyecto.rest.resource.user.dto.TransactionDTO;
import com.proyecto.user.domain.InvestorProfileEnum;
import com.proyecto.user.domain.valueobject.MarketValueVO;
import com.proyecto.user.exception.InvalidPasswordException;
import com.proyecto.user.service.UserService;

@Controller("userResource")
@RequestMapping("/users")
public class UserResource {

	@Resource
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public InvertarUserDTO store(@RequestBody InvertarUserDTO invertarUserDTO)
			throws InvalidPasswordException {
		return userService.store(invertarUserDTO);
	}

	@RequestMapping(value = "/{user_id}", method = RequestMethod.GET)
	@ResponseBody
	public InvertarUserDTO findUserById(@PathVariable("user_id") Long userId)
			throws ApplicationServiceException {
		return userService.findById(userId);
	}

	@RequestMapping(value = "/{user_id}/portfolios", method = RequestMethod.GET)
	@ResponseBody
	public List<PortfolioDTO> getUserPortfolios(
			@PathVariable("user_id") Long userId)
			throws ApplicationServiceException {
		return userService.getPortfolios(userId);
	}

	@RequestMapping(value = "/{user_id}/portfolios", method = RequestMethod.POST)
	@ResponseBody
	public PortfolioDTO store(@RequestBody PortfolioDTO portfolioDTO,
			@PathVariable("user_id") Long userId)
			throws ApplicationServiceException {
		return userService.addPortfolio(portfolioDTO, userId);
	}

	@RequestMapping(value = "/{user_id}/portfolios/{portfolio_id}", method = RequestMethod.GET)
	@ResponseBody
	public PortfolioDTO getUserPortfolio(@PathVariable("user_id") Long userId,
			@PathVariable("portfolio_id") Long portfolioId)
			throws ApplicationServiceException {
		return userService.findPortfolioById(userId, portfolioId);
	}

	@RequestMapping(value = "/{user_id}/portfolios/{portfolio_id}/marketValue", method = RequestMethod.GET)
	@ResponseBody
	public List<MarketValueVO> getUserPortfolioMarketValue(
			@PathVariable("user_id") Long userId,
			@PathVariable("portfolio_id") Long portfolioId)
			throws ApplicationServiceException {
		return userService.getPortfolioMarketValue(userId, portfolioId);
	}

	@RequestMapping(value = "/{user_id}/portfolios/{portfolio_id}/transactions", method = RequestMethod.POST)
	@ResponseBody
	public TransactionDTO addTransaction(@PathVariable("user_id") Long userId,
			@PathVariable("portfolio_id") Long portfolioId,
			@RequestBody TransactionDTO transactionDTO)
			throws ApplicationServiceException {
		return userService.addTransaction(transactionDTO, userId, portfolioId);
	}

	@RequestMapping(value = "/{user_id}/portfolios/{portfolio_id}/performance", method = RequestMethod.GET)
	@ResponseBody
	public Float getUserPortfolioPerformance(
			@PathVariable("user_id") Long userId,
			@PathVariable("portfolio_id") Long portfolioId)
			throws ApplicationServiceException {
		return userService.getPortfolioPerformance(userId, portfolioId);
	}

	@RequestMapping(value = "/{user_id}/portfolios/performance", method = RequestMethod.GET)
	@ResponseBody
	public Float getUserPortfolioPerformance(
			@PathVariable("user_id") Long userId)
			throws ApplicationServiceException {
		return userService.getPortfoliosPerformance(userId);
	}
	

	@RequestMapping(value = "/{user_id}/investorProfile", method = RequestMethod.POST)
	@ResponseBody
	public List<TheoreticalPortfolioDTO> getInvestorProfile(@RequestBody Integer amountOfPoints)
	{
		return userService.getInvestorProfile(amountOfPoints);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public InvertarUserDTO login(@RequestBody InvertarUserLoginDTO loginDTO)
			throws ApplicationServiceException {
		return userService.login(loginDTO);
	}
	

}
