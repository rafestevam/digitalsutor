package br.com.intelliapps.digitalsutor.controllers;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.intelliapps.digitalsutor.models.Usuario;
import br.com.intelliapps.digitalsutor.services.EmailService;
import br.com.intelliapps.digitalsutor.services.UsuarioService;
import br.com.intelliapps.digitalsutor.validation.UsuarioValidation;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EmailService mailService;
	
//	@Autowired
//	private SecurityService securityService;
	
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
	public String criaUsuario(@Valid Usuario usuario, BindingResult binding, RedirectAttributes rAttr, HttpServletRequest req) {
		
		if(binding.hasErrors())
			return formUsuario(usuario);
		
		if(usuarioService.existsByUsername(usuario.getUsername())) {
			binding.rejectValue("username", "field.error.username.exists");
			return formUsuario(usuario);
		}
		
		usuario.setToken(UUID.randomUUID().toString());
		
		usuarioService.save(usuario);
		
		String appUrl = req.getScheme() + "://" + req.getServerName();
		
		SimpleMailMessage registrationMessage = new SimpleMailMessage();
		registrationMessage.setTo(usuario.getEmail());
		registrationMessage.setSubject("Digital Sutor :: Confirmação de Registro");
		registrationMessage.setText("Para confirmar seu endereço de e-mail, favor clicar no link abaixo:\n"
				+ appUrl + "/confirmar?token=" + usuario.getToken());
		registrationMessage.setFrom("digitalsutor@gmail.com");
		
		mailService.sendMail(registrationMessage);
		
		rAttr.addFlashAttribute("registrationMessage", "Usuário cadastrado com sucesso! \n "
				+ "Um e-mail de validação será enviado ao endereço cadastrado.");
				
		//securityService.autologin(usuario.getUsername(), usuario.getPassword());
		
		return "redirect:login";
	}
	
	@RequestMapping(value="/confirmar", method=RequestMethod.GET)
	public String showConfirmationPage(Model model, @RequestParam("token") String token) {
		
		Usuario usuario = usuarioService.findByToken(token);
		if(usuario != null)
			model.addAttribute("username", usuario.getUsername());
		
		if(usuario == null)
			return "confirmarError";
		
		if(usuario.getActivated())
			return "confirmarError";
		
		//model.addAttribute("usuario", usuario);
		model.addAttribute(usuario);
		return "confirmar";
		
	}
	
	@RequestMapping(value="/confirmar", method=RequestMethod.POST)
	public String confirmUser(Usuario usuarioToken, RedirectAttributes rAttr, Model model) {
		
		Usuario usuario = usuarioService.findByToken(usuarioToken.getToken());
		
		usuario.setActivated(true);
		
		rAttr.addFlashAttribute("registrationMessage", "Usuário confirmado! Você já pode logar no Digital Sutor.");
		
		usuarioService.save(usuario);
		
		return "redirect:login";
		
	}
	
}
