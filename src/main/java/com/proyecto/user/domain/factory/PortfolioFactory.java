package com.proyecto.user.domain.factory;

import com.proyecto.asset.exception.InvalidAssetArgumentException;
import com.proyecto.common.exception.InvalidArgumentException;
import com.proyecto.common.validator.FieldValidator;
import com.proyecto.rest.resource.user.dto.PortfolioDTO;
import com.proyecto.user.domain.Portfolio;

public class PortfolioFactory {

	public static Portfolio create(PortfolioDTO portfolioDTO, Long nextID)
			throws InvalidAssetArgumentException {

		validateMandatoryFields(portfolioDTO);

		Portfolio portfolio = new Portfolio(nextID, portfolioDTO.getName());

		return portfolio;
	}

	private static void validateMandatoryFields(PortfolioDTO portfolioDTO)
			throws InvalidAssetArgumentException {

		try {
			FieldValidator.validateEmptyOrNull(portfolioDTO.getName());
		} catch (InvalidArgumentException e) {
			throw new InvalidAssetArgumentException(e);
		}

	}

}
