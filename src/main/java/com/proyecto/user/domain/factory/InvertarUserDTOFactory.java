package com.proyecto.user.domain.factory;

import java.util.ArrayList;
import java.util.List;

import com.proyecto.rest.resource.user.dto.InvertarUserDTO;
import com.proyecto.rest.resource.user.dto.PortfolioDTO;
import com.proyecto.user.domain.InvertarUser;
import com.proyecto.user.domain.Portfolio;

public class InvertarUserDTOFactory {

	public static InvertarUserDTO create(InvertarUser storedUser) {
		InvertarUserDTO invertarUserDTO = new InvertarUserDTO();

		invertarUserDTO.setId(storedUser.getId());
		invertarUserDTO.setPortfolios(convertToDTOs(storedUser));

		return invertarUserDTO;
	}

	private static List<PortfolioDTO> convertToDTOs(InvertarUser storedUser) {

		List<PortfolioDTO> portfoliosDTO = new ArrayList<PortfolioDTO>();

		for (Portfolio portfolio : storedUser.getPortfolios()) {
			portfoliosDTO.add(PortfolioDTOFactory.create(portfolio));
		}

		return portfoliosDTO;
	}

}
