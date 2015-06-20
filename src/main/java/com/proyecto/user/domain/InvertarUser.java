package com.proyecto.user.domain;

import java.util.ArrayList;
import java.util.List;

import com.proyecto.user.exception.PortfolioNotFoundException;

public class InvertarUser {

	private Long id;
	private List<Portfolio> portfolios = new ArrayList<Portfolio>();
	private String mail;
	private String username;

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

	public void addPortfolio(Portfolio portfolio) {
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

}
