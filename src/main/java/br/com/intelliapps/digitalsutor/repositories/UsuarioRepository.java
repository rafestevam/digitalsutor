package br.com.intelliapps.digitalsutor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.intelliapps.digitalsutor.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Usuario findByUsername(String username);
}
