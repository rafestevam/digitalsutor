package br.com.intelliapps.digitalsutor.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.intelliapps.digitalsutor.models.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

	Empresa findByCNPJ(String cnpj);
	
	@Query("select case when count(*) > 0 then 'true' else 'false' end from Empresa e where e.cNPJ = ?1")
	boolean existsByCNPJ(String cnpj);
	
	@Query("select distinct(e) from Empresa e join fetch e.usuarios u where u.username = ?1")
	List<Empresa> findByUsername(String username);
	
}
