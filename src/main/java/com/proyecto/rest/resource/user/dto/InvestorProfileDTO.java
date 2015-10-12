package com.proyecto.rest.resource.user.dto;

import java.util.List;

import com.proyecto.user.domain.InvestorProfileEnum;

public class InvestorProfileDTO {
	
	List<TheoreticalPortfolioDTO> theoricalPortfolios;
	
	private String name;
	private String description;
	private Integer amount;
	private Integer period;
	private Double yield;
	private Integer score;
	private InvestorProfileEnum profile;
	private String profileString;
	
	public List<TheoreticalPortfolioDTO> getTheoricalPortfolios() {
		return theoricalPortfolios;
	}
	public void setTheoreticalPortfolios(List<TheoreticalPortfolioDTO> theoricalPortfolios) {
		this.theoricalPortfolios = theoricalPortfolios;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public Double getYield() {
		return yield;
	}
	public void setYield(Double yield) {
		this.yield = yield;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public InvestorProfileEnum getProfile() {
		return profile;
	}
	public void setProfile(InvestorProfileEnum profile) {
		this.profile = profile;
	}
	public String getProfileString() {
		return profileString;
	}
	public void setProfileString(String profileString) {
		this.profileString = profileString;
	}
	
	
}
