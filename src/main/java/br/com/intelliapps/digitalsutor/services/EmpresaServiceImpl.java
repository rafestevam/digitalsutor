package br.com.intelliapps.digitalsutor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.intelliapps.digitalsutor.models.Empresa;
import br.com.intelliapps.digitalsutor.repositories.EmpresaRepository;

@Service
public class EmpresaServiceImpl implements EmpresaService{

	@Autowired
	private EmpresaRepository empresaRepository;
	
	public void save(Empresa empresa) {
		empresaRepository.save(empresa);
	}

	public Empresa findByCNPJ(String cnpj) {
		return empresaRepository.findByCNPJ(cnpj);
	}

}
