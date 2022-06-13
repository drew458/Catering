package it.uniroma3.siw.catering.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.uniroma3.siw.catering.controller.validator.BuffetValidator;
import it.uniroma3.siw.catering.controller.validator.ChefValidator;
import it.uniroma3.siw.catering.controller.validator.PiattoValidator;

@Configuration
public class AppConfiguration {

	@Bean
	public ChefValidator chefValidator() {
		return new ChefValidator();
	}
	
	@Bean
	public BuffetValidator buffetValidator() {
		return new BuffetValidator();
	}
	
	@Bean
	public PiattoValidator piattoValidator() {
		return new PiattoValidator();
	}
}
