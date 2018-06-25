package br.com.intelliapps.digitalsutor.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.intelliapps.digitalsutor.models.Role;
import br.com.intelliapps.digitalsutor.models.Usuario;
import br.com.intelliapps.digitalsutor.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder passEncoder;
	
	public void save(Usuario usuario) {
		Set<Role> roles = new HashSet<Role>();
		roles.add(new Role("ROLE_ADMIN"));
		
		if(usuario.getPassword().length() < 11) {
			String encodedPassword = passEncoder.encode(usuario.getPassword());
			usuario.setPassword(encodedPassword);
			usuario.setConfPass(encodedPassword);
			usuario.setRoles(roles);
		}
		
		usuarioRepository.save(usuario);
	}

	public Usuario findByUsername(String username) {
		return usuarioRepository.findByUsername(username);
	}

	public boolean existsByUsername(String username) {
		return usuarioRepository.existsByUsername(username);
	}
	
	public boolean existsByEmail(String email) {
		return usuarioRepository.existsByEmail(email);
	}

	public Usuario findByToken(String token) {
		return usuarioRepository.findByToken(token);
	}

	public Usuario findByEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}

}
