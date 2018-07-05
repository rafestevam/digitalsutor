package br.com.intelliapps.digitalsutor.services;

import java.util.List;

import br.com.intelliapps.digitalsutor.models.Empresa;

public interface EmpresaService {
	
	public void save(Empresa empresa);
	
	public Empresa findByCNPJ(String cnpj);
	
	public boolean existsByCNPJ(String cnpj);
	
	public List<Empresa> findByUsername(String username);

}
