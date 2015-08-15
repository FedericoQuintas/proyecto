package com.proyecto.inflation.service;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.proyecto.inflation.persistence.InflationDAO;

@Service("inflationService")
public class InflationServiceImpl implements InflationService {

	private static final String API_INFLATION = "https://www.quandl.com/api/v1/datasets/STATESTREET/PS_AR.json";

	@Resource(name = "inflationDAO")
	private InflationDAO inflationDAO;

	@Scheduled(cron = "*/59 * * * * ?")
	public void update() {
	}

}
