package br.com.intelliapps.digitalsutor.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.intelliapps.digitalsutor.models.Empresa;

public class EmpresaValidation implements Validator{

	public boolean supports(Class<?> clazz) {
		return Empresa.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "nome", "field.error.empty", new String[] {"Nome"});
	}

	
	
}
