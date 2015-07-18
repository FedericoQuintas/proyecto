package com.proyecto.rest.resource.user.dto;

import com.proyecto.user.domain.InvestorProfile;

public class InvestorProfileDTO {
	
	InvestorProfile investorProfile;
	TheoricalPortfolioDTO theoricalPortfolios;
	
	public InvestorProfile getInvestorProfile() {
		return investorProfile;
	}
	public void setInvestorProfile(InvestorProfile investorProfile) {
		this.investorProfile = investorProfile;
	}
	public TheoricalPortfolioDTO getTheoricalPortfolios() {
		return theoricalPortfolios;
	}
	public void setTheoricalPortfolios(TheoricalPortfolioDTO theoricalPortfolios) {
		this.theoricalPortfolios = theoricalPortfolios;
	}
	
	
}
