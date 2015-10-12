package com.proyecto.rest.resource.user.dto;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class InvertarUserDTO {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("portfolios")
	private List<PortfolioDTO> portfolios;

	@JsonProperty("username")
	private String username;

	@JsonProperty("mail")
	private String mail;

	@JsonProperty("password")
	private String password;
	
	@JsonProperty("investorProfiles")
	private List<InvestorProfileDTO> investorProfilesDTO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<PortfolioDTO> getPortfolios() {
		return this.portfolios;
	}

	public void setPortfolios(List<PortfolioDTO> portfoliosDTO) {
		this.portfolios = portfoliosDTO;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public List<InvestorProfileDTO> getInvestorProfilesDTO() {
		return investorProfilesDTO;
	}

	public void setInvestorProfileDTO(List<InvestorProfileDTO> investorProfilesDTO) {
		this.investorProfilesDTO = investorProfilesDTO;
	}

}
