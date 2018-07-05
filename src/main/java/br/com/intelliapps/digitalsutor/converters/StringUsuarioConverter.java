package br.com.intelliapps.digitalsutor.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import br.com.intelliapps.digitalsutor.models.Usuario;
import br.com.intelliapps.digitalsutor.services.UsuarioService;

public class StringUsuarioConverter implements Converter<String, Usuario> {

	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public Usuario convert(String username) {
		return usuarioService.findByUsername(username);
	}

}
