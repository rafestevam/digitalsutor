package br.com.intelliapps.digitalsutor.services;

import br.com.intelliapps.digitalsutor.models.Usuario;

public interface UsuarioService {

	public void save(Usuario usuario);
	
	public Usuario findByUsername(String username);
	
	public Usuario findByToken(String token);
	
	public Usuario findByEmail(String email);
	
	public boolean existsByUsername(String username);
	
	public boolean existsByEmail(String email);
	
}
