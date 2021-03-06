package br.com.intelliapps.digitalsutor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.intelliapps.digitalsutor.models.Usuario;
import br.com.intelliapps.digitalsutor.services.UsuarioLogadoService;

@Controller
public class DashboardController {

//	@Autowired
//	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioLogadoService usuarioLogadoService;
	
	@RequestMapping("/dashboard")
	public String acessaDash(Model model) {
//		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		Usuario usuario = usuarioRepository.findByUsername(user.getUsername());
		
		Usuario usuario = usuarioLogadoService.usuarioLogado();
		
		model.addAttribute("nome", usuario.getNome());
		model.addAttribute("ultimonome", usuario.getUltimonome());
		return "dashboard";
	}
	
}
