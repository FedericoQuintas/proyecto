package com.proyecto.rest.resource.user.dto;

import java.util.List;

import com.proyecto.user.domain.InvestorProfileEnum;

public class InvestorProfileDTO {
	
	InvestorProfileEnum investorProfile;
	List<TheoreticalPortfolioDTO> theoricalPortfolios;
	
	public InvestorProfileEnum getInvestorProfile() {
		return investorProfile;
	}
	public void setInvestorProfile(InvestorProfileEnum investorProfile) {
		this.investorProfile = investorProfile;
	}
	public List<TheoreticalPortfolioDTO> getTheoricalPortfolios() {
		return theoricalPortfolios;
	}
	public void setTheoreticalPortfolios(List<TheoreticalPortfolioDTO> theoricalPortfolios) {
		this.theoricalPortfolios = theoricalPortfolios;
	}
	
	
}
