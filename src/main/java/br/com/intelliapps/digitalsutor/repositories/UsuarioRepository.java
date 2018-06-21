package br.com.intelliapps.digitalsutor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.intelliapps.digitalsutor.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Usuario findByUsername(String username);
	Usuario findByToken(String token);	
	
	@Query("select case when count(*) > 0 then 'true' else 'false' end from Usuario u where u.username = ?1")
	boolean existsByUsername(String username);
}
