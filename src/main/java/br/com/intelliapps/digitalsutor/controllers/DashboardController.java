package br.com.intelliapps.digitalsutor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.intelliapps.digitalsutor.models.Usuario;
import br.com.intelliapps.digitalsutor.repositories.UsuarioRepository;

@Controller
public class DashboardController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@RequestMapping("/dashboard")
	public String acessaDash(Model model) {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Usuario usuario = usuarioRepository.findByUsername(user.getUsername());
		
		model.addAttribute("nome", usuario.getNome());
		model.addAttribute("ultimonome", usuario.getUltimonome());
		return "dashboard";
	}
	
}
