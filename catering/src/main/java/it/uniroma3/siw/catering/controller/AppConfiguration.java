package it.uniroma3.siw.catering.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.uniroma3.siw.catering.controller.validator.ChefValidator;

@Configuration
public class AppConfiguration {

	@Bean
	ChefValidator chefValidator() {
		return new ChefValidator();
	}
}
