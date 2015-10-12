package com.proyecto.user.domain;

import java.util.ArrayList;
import java.util.List;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.mongojack.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.user.exception.PortfolioNameAlreadyInUseException;
import com.proyecto.user.exception.PortfolioNotFoundException;

public class InvertarUser {

	@MongoId
	@MongoObjectId
	public String objectId;
	private Long id;
	private List<Portfolio> portfolios = new ArrayList<Portfolio>();
	private String mail;
	private String username;
	private String password;
	
	private List<InvestorProfile> investorProfiles = new ArrayList<InvestorProfile>();

	public InvertarUser() {
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public InvertarUser(Long userId) {
		this.id = userId;
	}

	public Long getId() {
		return this.id;
	}

	public List<Portfolio> getPortfolios() {
		return this.portfolios;
	}

	public void addPortfolio(Portfolio portfolio)
			throws PortfolioNameAlreadyInUseException {
		for (Portfolio userPortfolio : this.portfolios) {
			if (userPortfolio.getName().equals(portfolio.getName())) {
				throw new PortfolioNameAlreadyInUseException();
			}
		}
		this.portfolios.add(portfolio);
	}

	public Portfolio getPortfolio(Long portfolioId)
			throws PortfolioNotFoundException {

		for (Portfolio portfolio : portfolios) {
			if (portfolio.getId().equals(portfolioId)) {
				return portfolio;
			}

		}
		throw new PortfolioNotFoundException();
	}
	
	public void removePortfolio(Long portfolioId) throws PortfolioNotFoundException {
		portfolios.remove(getPortfolio(portfolioId));
	}

	public void setPassword(String password) {
		this.password = password;

	}

	public String getPassword() {
		return password;
	}

	@ObjectId
	@JsonProperty("_id")
	public String getObjectId() {
		return this.objectId;
	}

	@ObjectId
	@JsonProperty("_id")
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public List<InvestorProfile> getInvestorProfiles() {
		return investorProfiles;
	}

	public void setInvestorProfiles(List<InvestorProfile> investorProfiles) {
		this.investorProfiles = investorProfiles;
	}
	
	public void addInvestorProfile(InvestorProfile investorProfile) {
		this.investorProfiles.add(investorProfile);
	}

}
