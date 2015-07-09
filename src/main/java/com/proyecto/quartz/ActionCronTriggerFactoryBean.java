package com.proyecto.quartz;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.ParseException;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class ActionCronTriggerFactoryBean extends CronTriggerFactoryBean {

	@Resource
	private ScheduledActionRunnerJobDetailFactory jobDetailFactory;

	@Value("*/10 * * * * ?")
	private String pattern;

	@Override
	public void afterPropertiesSet() throws ParseException {
		setCronExpression(pattern);
		jobDetailFactory.getObject();
		setJobDetail(jobDetailFactory.getObject());
		super.afterPropertiesSet();
	}

}