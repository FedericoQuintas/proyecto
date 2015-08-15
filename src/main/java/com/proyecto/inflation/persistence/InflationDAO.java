package com.proyecto.inflation.persistence;

import com.proyecto.inflation.domain.Inflation;

public interface InflationDAO {

	void flush();

	Inflation store(Inflation inflation);

}
