package com.proyecto.user.domain.factory;

import java.util.ArrayList;
import java.util.List;

import com.proyecto.rest.resource.user.dto.InvertarUserDTO;
import com.proyecto.rest.resource.user.dto.InvestorProfileDTO;
import com.proyecto.rest.resource.user.dto.PortfolioDTO;
import com.proyecto.user.domain.InvertarUser;
import com.proyecto.user.domain.InvestorProfile;
import com.proyecto.user.domain.Portfolio;

public class InvertarUserDTOFactory {

	public static InvertarUserDTO create(InvertarUser storedUser) {
		InvertarUserDTO invertarUserDTO = new InvertarUserDTO();

		invertarUserDTO.setId(storedUser.getId());
		invertarUserDTO.setUsername(storedUser.getUsername());
		invertarUserDTO.setMail(storedUser.getMail());
		invertarUserDTO.setPortfolios(convertPortfoliosToDTOs(storedUser.getPortfolios()));
		invertarUserDTO.setInvestorProfileDTO(convertInvestorProfilesToDTOs(storedUser.getInvestorProfiles()));
		return invertarUserDTO;
	}

	private static List<PortfolioDTO> convertPortfoliosToDTOs(List<Portfolio> portfolios) {

		List<PortfolioDTO> portfoliosDTO = new ArrayList<PortfolioDTO>();

		for (Portfolio portfolio : portfolios) {
			portfoliosDTO.add(PortfolioDTOFactory.create(portfolio));
		}

		return portfoliosDTO;
	}
	
	private static List<InvestorProfileDTO> convertInvestorProfilesToDTOs(List<InvestorProfile> investorProfiles) {

		List<InvestorProfileDTO> investorProfilesDTO = new ArrayList<InvestorProfileDTO>();

		for (InvestorProfile investorProfile : investorProfiles) {
			investorProfilesDTO.add(InvestorProfileDTOFactory.create(investorProfile));
		}

		return investorProfilesDTO;
	}
}
