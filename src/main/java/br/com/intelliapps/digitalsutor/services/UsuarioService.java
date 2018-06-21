package br.com.intelliapps.digitalsutor.services;

import br.com.intelliapps.digitalsutor.models.Usuario;

public interface UsuarioService {

	public void save(Usuario usuario);
	
	public Usuario findByUsername(String username);
	
	public Usuario findByToken(String token);
	
	public boolean existsByUsername(String username);
	
}
