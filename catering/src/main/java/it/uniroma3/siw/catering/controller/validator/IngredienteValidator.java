package it.uniroma3.siw.catering.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.catering.model.Ingrediente;

public class IngredienteValidator implements Validator {
	
	final Integer MAX_NAME_LENGTH = 100;
    final Integer MIN_NAME_LENGTH = 2;

	@Override
	public boolean supports(Class<?> clazz) {
		return Ingrediente.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Ingrediente ingrediente = (Ingrediente) target;
		String nome = ingrediente.getNome();
		
		if (nome.isEmpty())
            errors.rejectValue("nome", "required");
        else if (nome.length() < MIN_NAME_LENGTH || nome.length() > MAX_NAME_LENGTH)
            errors.rejectValue("nome", "size");
	}

}
