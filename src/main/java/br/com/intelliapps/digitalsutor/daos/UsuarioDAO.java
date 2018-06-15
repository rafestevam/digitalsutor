package br.com.intelliapps.digitalsutor.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.intelliapps.digitalsutor.models.Usuario;

@Repository
@Transactional
public class UsuarioDAO implements UserDetailsService {

	@PersistenceContext
	private EntityManager manager;
	
	public Usuario loadUserByUsername(String login) throws UsernameNotFoundException {
		
		List<Usuario> usuarios = manager.createQuery("select u from Usuario u where username = :username", Usuario.class)
			.setParameter("username", login)
			.getResultList();
		
		if(usuarios.isEmpty())
			throw new UsernameNotFoundException("Usuário " + login + " não encontrado");
		
		return usuarios.get(0);
	}
	
	public void gravarUsuario(Usuario usuario) {
		manager.persist(usuario);
	}

}
