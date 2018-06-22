package br.com.intelliapps.digitalsutor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.intelliapps.digitalsutor.models.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

	Empresa findByCNPJ(String cnpj);
	
}
