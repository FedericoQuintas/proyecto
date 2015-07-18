package com.proyecto.rest.resource.user.dto;

import com.proyecto.user.domain.InvestorProfile;

public class InvestorProfileDTO {
	
	InvestorProfile investorProfile;
	TheoreticalPortfolioDTO theoricalPortfolios;
	
	public InvestorProfile getInvestorProfile() {
		return investorProfile;
	}
	public void setInvestorProfile(InvestorProfile investorProfile) {
		this.investorProfile = investorProfile;
	}
	public TheoreticalPortfolioDTO getTheoricalPortfolios() {
		return theoricalPortfolios;
	}
	public void setTheoreticalPortfolios(TheoreticalPortfolioDTO theoricalPortfolios) {
		this.theoricalPortfolios = theoricalPortfolios;
	}
	
	
}
