package com.proyecto.portfolio.domain;

import java.util.ArrayList;
import java.util.List;

import com.proyecto.useractive.domain.UserActive;

public class Portfolio {

	private Long id;
	private List<UserActive> userActives = new ArrayList<UserActive>();

	public Portfolio(Long portfolioID) {
		this.id = portfolioID;
	}

	public Long getId() {
		return this.id;
	}

	public List<UserActive> getUserActives() {
		return this.userActives;
	}

}
