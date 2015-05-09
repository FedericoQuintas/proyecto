package com.proyecto.config;

import org.apache.log4j.BasicConfigurator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.proyecto.user.service.UserService;

public class Main {
	public static void main(String args[]) {

		BasicConfigurator.configure();

		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext contexto = new AnnotationConfigApplicationContext();
		contexto.register(AppConfig.class);
		contexto.refresh();

		UserService servicioA = (UserService) contexto
				.getBean(UserService.class);
		System.out.println(servicioA.getClass());

	}
}
