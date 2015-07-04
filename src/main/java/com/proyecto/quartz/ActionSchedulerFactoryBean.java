package com.proyecto.quartz;

import javax.annotation.Resource;

import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class ActionSchedulerFactoryBean extends SchedulerFactoryBean {

	@Resource
	private ScheduledActionRunnerJobDetailFactory jobDetailFactory;

	@Resource
	private ActionCronTriggerFactoryBean triggerFactory;

	@Override
	public void afterPropertiesSet() throws Exception {
		setTriggers(triggerFactory.getObject());
		super.afterPropertiesSet();
	}

}
