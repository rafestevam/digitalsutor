package br.com.intelliapps.digitalsutor.services;

import br.com.intelliapps.digitalsutor.models.Empresa;

public interface EmpresaService {
	
	public void save(Empresa empresa);
	
	public Empresa findByCNPJ(String cnpj);

}
