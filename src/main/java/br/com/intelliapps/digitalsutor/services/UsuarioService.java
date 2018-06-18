package br.com.intelliapps.digitalsutor.services;

import br.com.intelliapps.digitalsutor.models.Usuario;

public interface UsuarioService {

	public void save(Usuario usuario);
	
	public Usuario findeByUsername(String username);
	
}
