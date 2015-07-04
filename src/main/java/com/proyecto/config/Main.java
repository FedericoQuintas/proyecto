package com.proyecto.config;

import java.text.ParseException;

import org.apache.log4j.BasicConfigurator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	public static void main(String args[]) throws ParseException {

		BasicConfigurator.configure();

		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext contexto = new AnnotationConfigApplicationContext();
		contexto.register(AppConfig.class);
		contexto.refresh();
		


	}
}
