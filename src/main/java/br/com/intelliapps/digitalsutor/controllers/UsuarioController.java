package br.com.intelliapps.digitalsutor.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.intelliapps.digitalsutor.models.Usuario;
import br.com.intelliapps.digitalsutor.services.SecurityService;
import br.com.intelliapps.digitalsutor.services.UsuarioService;
import br.com.intelliapps.digitalsutor.validation.UsuarioValidation;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private SecurityService securityService;
	
//	@Autowired
//	private PasswordEncoder passEncoder;
	
//	@Autowired
//	private JdbcUserDetailsManager userDetailsManager;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new UsuarioValidation());
	}

	@RequestMapping(value="/novousuario", method=RequestMethod.GET)
	public String formUsuario(Usuario usuario) {
		return "novousuario";
	}
	
	
	@RequestMapping(value="/novousuario", method=RequestMethod.POST)
	public String criaUsuario(@Valid Usuario usuario, BindingResult binding, Model model) {
		
		if(binding.hasErrors())
			return formUsuario(usuario);
		
//		usuario.setRoles(Arrays.asList(new Role("ROLE_ADMIN")));
		
//		String encodedPass = passEncoder.encode(usuario.getPassword());
//		usuario.setPassword(encodedPass);
//		usuario.setConfPass(encodedPass);
//		
//		User user = new User(usuario.getUsername(), encodedPass, usuario.getRoles());
//		userDetailsManager.createUser(usuario);
		
//		usuarioDAO.gravarUsuario(usuario);
		
		usuarioService.save(usuario);
		
		securityService.autologin(usuario.getUsername(), usuario.getPassword());
		
		return "redirect:dashboard";
	}
	
}
