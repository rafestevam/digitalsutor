package br.com.intelliapps.digitalsutor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import br.com.intelliapps.digitalsutor.models.Usuario;

@Service
public class UsuarioLogadoService {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Cacheable("usuarioLogado")
	public Usuario usuarioLogado() {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return usuarioService.findByUsername(user.getUsername());
	}
	
}
