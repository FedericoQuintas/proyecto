package com.proyecto.user.domain;

import java.util.ArrayList;
import java.util.List;

import com.proyecto.portfolio.domain.Portfolio;

public class InvertarUser {

	private Long id;
	private List<Portfolio> portfolios = new ArrayList<Portfolio>();

	public InvertarUser(Long userId) {
		this.id = userId;
	}

	public Long getId() {
		return this.id;
	}

	public List<Portfolio> getPortfolios() {
		return this.portfolios;
	}

}
