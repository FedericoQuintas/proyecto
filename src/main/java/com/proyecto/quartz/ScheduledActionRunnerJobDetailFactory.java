package com.proyecto.quartz;

import javax.annotation.Resource;

import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Component;

import com.proyecto.yahoofinance.service.YahooFinanceInformationService;

@Component
public class ScheduledActionRunnerJobDetailFactory extends JobDetailFactoryBean {

	@Resource
	private YahooFinanceInformationService yahooFinanceInformationService;

	@Override
	public void afterPropertiesSet() {
		setJobClass(yahooFinanceInformationService.getClass());
		this.setDurability(true);
		super.afterPropertiesSet();
	}
}