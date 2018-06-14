package br.com.intelliapps.digitalsutor.repositories;

import org.springframework.data.repository.CrudRepository;

import br.com.intelliapps.digitalsutor.models.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {

}
