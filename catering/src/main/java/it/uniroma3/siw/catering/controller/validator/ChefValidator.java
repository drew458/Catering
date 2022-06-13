package it.uniroma3.siw.catering.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.catering.model.Chef;

public class ChefValidator implements Validator {
	
	final Integer MAX_NAME_LENGTH = 100;
    final Integer MIN_NAME_LENGTH = 2;

	@Override
	public boolean supports(Class<?> clazz) {
		return Chef.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Chef chef = (Chef) target;
		String nome = chef.getNome().trim();
		String cognome = chef.getCognome().trim();
		
		if (nome.isEmpty())
            errors.rejectValue("nome", "required");
        else if (nome.length() < MIN_NAME_LENGTH || nome.length() > MAX_NAME_LENGTH)
            errors.rejectValue("nome", "size");

        if (cognome.isEmpty())
            errors.rejectValue("cognome", "required");
        else if (cognome.length() < MIN_NAME_LENGTH || cognome.length() > MAX_NAME_LENGTH)
            errors.rejectValue("cognome", "size");
		
	}

}
