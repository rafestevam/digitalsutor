package br.com.intelliapps.digitalsutor.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		
		
		Usuario usuario = (Usuario) target;
		System.out.println(usuario.getUsername());
		System.out.println(usuario.getPassword());
		System.out.println(usuario.getConfPass());
		if(!usuario.getPassword().equals(usuario.getConfPass())) {
			errors.rejectValue("password", "field.error.passwordconflict");
			errors.rejectValue("confPass", "field.error.passwordconflict");
		}
		if(!usuario.getAccept())
			errors.rejectValue("accept", "field.error.noaccept");
		
		if(usuario.getPassword().length() > 10) {
			errors.rejectValue("password", "field.error.password.size");
			errors.rejectValue("confPass", "field.error.password.size");
		}
		
		Pattern patternSpace = Pattern.compile("[,\\s]");
		Matcher matcherSpace = patternSpace.matcher(usuario.getUsername());
		if(matcherSpace.find())
			errors.rejectValue("username", "field.error.username.blanks");
		
		Pattern pattern = Pattern.compile("\\p{Alnum}+");
		Matcher matcher = pattern.matcher(usuario.getPassword());
		Matcher matcher2 = pattern.matcher(usuario.getConfPass());
		if (!matcher.matches() && !matcher2.matches()) {
			errors.rejectValue("password", "field.error.password.charInvalid");
			errors.rejectValue("confPass", "field.error.password.charInvalid");
		}
		
	}

}
