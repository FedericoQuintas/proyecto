package com.proyecto.user.domain.factory;

import com.proyecto.rest.resource.user.dto.InvestorProfileDTO;
import com.proyecto.user.domain.InvestorProfile;

public class InvestorProfileDTOFactory {
	public static InvestorProfileDTO create(InvestorProfile investorProfile) {
		InvestorProfileDTO investorProfileDTO = new InvestorProfileDTO();
		investorProfileDTO.setAmount(investorProfile.getAmount());
		investorProfileDTO.setDescription(investorProfile.getDescription());
		investorProfileDTO.setName(investorProfile.getName());
		investorProfileDTO.setPeriod(investorProfile.getPeriod());
		investorProfileDTO.setProfile(investorProfile.getProfile());
		investorProfileDTO.setProfileString(investorProfile.getProfileString());
		investorProfileDTO.setScore(investorProfile.getScore());
		investorProfileDTO.setYield(investorProfile.getYield());
		
		return investorProfileDTO;
	}
}
