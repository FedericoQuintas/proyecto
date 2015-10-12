package com.proyecto.user.domain.factory;

import com.proyecto.rest.resource.user.dto.InvestorProfileDTO;
import com.proyecto.user.domain.InvestorProfile;

public class InvestorProfileFactory {
	public static InvestorProfile create(InvestorProfileDTO investorProfileDTO) {
		InvestorProfile investorProfile = new InvestorProfile();
		investorProfile.setAmount(investorProfileDTO.getAmount());
		investorProfile.setDescription(investorProfileDTO.getDescription());
		investorProfile.setName(investorProfileDTO.getName());
		investorProfile.setPeriod(investorProfileDTO.getPeriod());
		investorProfile.setProfile(investorProfileDTO.getProfile());
		investorProfile.setProfileString(investorProfileDTO.getProfileString());
		investorProfile.setScore(investorProfileDTO.getScore());
		investorProfile.setYield(investorProfileDTO.getYield());
		
		return investorProfile;
	}
}
