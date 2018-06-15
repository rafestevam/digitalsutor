package br.com.intelliapps.digitalsutor.controllers;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.intelliapps.digitalsutor.daos.UsuarioDAO;
import br.com.intelliapps.digitalsutor.models.Role;
import br.com.intelliapps.digitalsutor.models.Usuario;
import br.com.intelliapps.digitalsutor.validation.UsuarioValidation;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
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
		
		usuario.setRoles(Arrays.asList(new Role("ROLE_ADMIN")));
		
		usuarioDAO.gravarUsuario(usuario);
		
		return "redirect:login";
	}
	
}
