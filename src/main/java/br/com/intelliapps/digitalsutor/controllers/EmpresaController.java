package br.com.intelliapps.digitalsutor.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.intelliapps.digitalsutor.models.Empresa;
import br.com.intelliapps.digitalsutor.models.TipoAliq;
import br.com.intelliapps.digitalsutor.models.Usuario;
import br.com.intelliapps.digitalsutor.services.UsuarioLogadoService;

@Controller
public class EmpresaController {
	
	@Autowired
	private UsuarioLogadoService usuarioLogadoService;
	
	@RequestMapping(value="/empresa", method=RequestMethod.GET)
	public String empresaForm(Empresa empresa, Model model) {
		
		Usuario usuario = usuarioLogadoService.usuarioLogado();
		Set<Usuario> usuarios = new HashSet<Usuario>();
		usuarios.add(usuario);
		empresa.setUsuarios(usuarios);
		
		model.addAttribute("nome", usuario.getNome());
		model.addAttribute("ultimonome", usuario.getUltimonome());
		model.addAttribute("empresa", empresa);
		model.addAttribute("tipoAliq", TipoAliq.values());
		
		return "empresa";
	}
	
	@ResponseBody
	@RequestMapping(value="/empresa", method=RequestMethod.POST)
	public String criaEmpresa(Empresa empresa) {
		return "Logo mais, você criará a sua empresa!";
	}
	
	
	
}
