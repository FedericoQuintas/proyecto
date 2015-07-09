package com.proyecto.rest.resource.yahoo;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyecto.common.exception.ApplicationServiceException;
import com.proyecto.yahoofinance.service.YahooFinanceInformationService;

@Controller("yahooResource")
@RequestMapping("/yahoo")
public class YahooFinanceResource {

	@Resource
	private YahooFinanceInformationService yahooFinanceInformationService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void update() throws ApplicationServiceException {
		yahooFinanceInformationService.update();
	}

}
