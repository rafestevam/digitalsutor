package br.com.intelliapps.digitalsutor.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.intelliapps.digitalsutor.models.Usuario;

public class UsuarioValidation implements Validator{
	
	public boolean supports(Class<?> clazz) {
		return Usuario.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "username", "field.error.empty", new String[] {"Login"});
		ValidationUtils.rejectIfEmpty(errors, "nome", "field.error.empty", new String[] {"Primeiro Nome"});
		ValidationUtils.rejectIfEmpty(errors, "email", "field.error.empty", new String[] {"E-mail"});
		ValidationUtils.rejectIfEmpty(errors, "password", "field.error.empty", new String[] {"Senha"});
		ValidationUtils.rejectIfEmpty(errors, "confPass", "field.error.empty", new String[] {"Senha"});
		//ValidationUtils.rejectIfEmpty(errors, "accept", "field.error.noaccept");
		
		Usuario usuario = (Usuario) target;
		if(!usuario.getPassword().equals(usuario.getConfPass())) {
			errors.rejectValue("password", "field.error.passwordconflict");
			errors.rejectValue("confPass", "field.error.passwordconflict");
		}
		if(!usuario.getAccept())
			errors.rejectValue("accept", "field.error.noaccept");
		
	}

}
