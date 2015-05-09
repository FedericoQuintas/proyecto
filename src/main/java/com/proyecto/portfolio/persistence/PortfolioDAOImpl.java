package com.proyecto.portfolio.persistence;

import java.util.ArrayList;
import java.util.List;

import com.proyecto.portfolio.domain.Portfolio;

public class PortfolioDAOImpl implements PortfolioDAO {

	List<Portfolio> portfolios = new ArrayList<Portfolio>();

	Long portfoliosSequence = new Long(1);

	@Override
	public Long nextID() {
		return portfoliosSequence++;
	}

	@Override
	public Portfolio store(Portfolio portfolio) {

		portfolios.add(portfolio);

		return portfolio;
	}

	@Override
	public void flush() {
		portfolios.clear();
		
	}

}
