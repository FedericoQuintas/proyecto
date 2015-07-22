package com.proyecto.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringMvcInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { AppConfig.class };
	}

	@Override
	public void onStartup(ServletContext servletContext) {

		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(AppConfig.class);
		servletContext.addListener(new ContextLoaderListener(rootContext));

		FilterRegistration.Dynamic securityFilter = servletContext.addFilter(
				"springSecurityFilterChain", DelegatingFilterProxy.class);
		securityFilter.addMappingForUrlPatterns(null, false, "/*");

		servletContext.addListener(new QuartzInitializerListener());

		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
				"dispatcher", new DispatcherServlet(rootContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");

		FilterRegistration.Dynamic fr = servletContext.addFilter("cors",
				new CorsFilter());
		fr.addMappingForUrlPatterns(null, true, "/*");

		FilterRegistration.Dynamic frEncoding = servletContext.addFilter(
				"encodingFilter", new CharacterEncodingFilter());
		frEncoding.setInitParameter("encoding", "UTF-8");
		frEncoding.setInitParameter("forceEncoding", "true");
		frEncoding.addMappingForUrlPatterns(null, true, "/*");

	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// return new Class[] { SecurityConfig.class };
		return new Class[] {};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
