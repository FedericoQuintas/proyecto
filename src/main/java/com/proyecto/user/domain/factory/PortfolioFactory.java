package com.proyecto.user.domain.factory;

import com.proyecto.common.exception.InvalidArgumentException;
import com.proyecto.common.validator.FieldValidator;
import com.proyecto.rest.resource.user.dto.PortfolioDTO;
import com.proyecto.user.domain.Portfolio;
import com.proyecto.user.exception.InvalidPortfolioArgumentException;

public class PortfolioFactory {

	public static Portfolio create(PortfolioDTO portfolioDTO, Long nextID)
			throws InvalidPortfolioArgumentException {

		validateMandatoryFields(portfolioDTO);

		Portfolio portfolio = new Portfolio(nextID, portfolioDTO.getName());

		return portfolio;
	}

	private static void validateMandatoryFields(PortfolioDTO portfolioDTO)
			throws InvalidPortfolioArgumentException {

		try {
			FieldValidator.validateEmptyOrNull(portfolioDTO.getName());
		} catch (InvalidArgumentException e) {
			throw new InvalidPortfolioArgumentException(e);
		}

	}

}
