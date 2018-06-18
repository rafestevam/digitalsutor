package br.com.intelliapps.digitalsutor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.intelliapps.digitalsutor.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
