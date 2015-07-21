package com.proyecto.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

public class SpringSecurityInitializer extends
		AbstractSecurityWebApplicationInitializer {

	@Override
	protected String getDispatcherWebApplicationContextSuffix() {
		return AbstractDispatcherServletInitializer.DEFAULT_SERVLET_NAME;
	}

	public SpringSecurityInitializer() {
		super(SecurityConfig.class);
	}
}
