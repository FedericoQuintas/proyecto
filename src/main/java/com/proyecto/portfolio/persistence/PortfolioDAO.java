package com.proyecto.portfolio.persistence;

import com.proyecto.common.persistence.GenericDAO;
import com.proyecto.portfolio.domain.Portfolio;

public interface PortfolioDAO extends GenericDAO {

	public Portfolio store(Portfolio portfolio);

}
