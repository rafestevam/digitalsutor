package br.com.intelliapps.digitalsutor.services;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.intelliapps.digitalsutor.models.Aliquota;
import br.com.intelliapps.digitalsutor.models.Empresa;
import br.com.intelliapps.digitalsutor.repositories.EmpresaRepository;

@Service
public class EmpresaServiceImpl implements EmpresaService{

	@Autowired
	private EmpresaRepository empresaRepository;
	
	public void save(Empresa empresa) {
		
		for(Aliquota aliq : empresa.getAliquotas()) {
			
			if(!Objects.nonNull(aliq.getAliquota())) {
				aliq.setAliquota(BigDecimal.ZERO);
//				empresa.getAliquotas().remove(aliq);
//				continue;
			}
			
//			if(aliq.getAliquota().equals(BigDecimal.ZERO))
//				empresa.getAliquotas().remove(aliq);
		}
		
		empresaRepository.save(empresa);
	}

	public Empresa findByCNPJ(String cnpj) {
		return empresaRepository.findByCNPJ(cnpj);
	}

	public boolean existsByCNPJ(String cnpj) {
		return empresaRepository.existsByCNPJ(cnpj);
	}
	

}
