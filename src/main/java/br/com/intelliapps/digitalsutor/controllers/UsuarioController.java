package br.com.intelliapps.digitalsutor.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UsuarioController {

	@RequestMapping(value="/novousuario", method=RequestMethod.GET)
	public String formUsuario() {
		return "novousuario";
	}
	
	@RequestMapping(value="/novousuario", method=RequestMethod.POST)
	public String criaUsuario() {
		
		return "login";
	}
	
}
