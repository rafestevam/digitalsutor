package br.com.intelliapps.digitalsutor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.intelliapps.digitalsutor.models.Empresa;
import br.com.intelliapps.digitalsutor.models.Usuario;
import br.com.intelliapps.digitalsutor.services.UsuarioLogadoService;

@Controller
public class EmpresaController {
	
	@Autowired
	private UsuarioLogadoService usuarioLogadoService;
	
	@RequestMapping(value="/empresa", method=RequestMethod.GET)
	public String empresaForm(Empresa empresa, Model model) {
		
		Usuario usuario = usuarioLogadoService.usuarioLogado();
		
		model.addAttribute("nome", usuario.getNome());
		model.addAttribute("ultimonome", usuario.getUltimonome());
		
		return "empresa";
	}
	
	
	
}
