package br.com.intelliapps.digitalsutor.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.intelliapps.digitalsutor.models.Role;
import br.com.intelliapps.digitalsutor.models.Usuario;
import br.com.intelliapps.digitalsutor.repositories.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		boolean enabled = false;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		
		Usuario usuario = usuarioRepository.findByUsername(username);
		if(usuario == null)
			throw new UsernameNotFoundException(username);
		
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		for(Role role : usuario.getRoles()) {
			roles.add(new SimpleGrantedAuthority(role.getNome()));
		}
		
		if(!usuario.getActivated())
			return new User(usuario.getUsername(), usuario.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, roles);
		
		return new User(usuario.getUsername(), usuario.getPassword(), roles);
	}

}
