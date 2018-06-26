package br.com.intelliapps.digitalsutor.controllers;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
import br.com.intelliapps.digitalsutor.services.UsuarioLogadoService;
import br.com.intelliapps.digitalsutor.services.UsuarioService;
import br.com.intelliapps.digitalsutor.validation.UsuarioValidation;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EmailService mailService;
	
	@Autowired
	private UsuarioLogadoService usuarioLogadoService;
	
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
		
		if(usuarioService.existsByUsername(usuario.getEmail())) {
			binding.rejectValue("email", "field.error.email.exists");
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
		
		rAttr.addFlashAttribute("registrationMessage", "Usuário confirmado! Você já pode se logar no Digital Sutor.");
		
		usuarioService.save(usuario);
		
		return "redirect:login";
		
	}
	
	@RequestMapping(value="/forget", method=RequestMethod.GET)
	public String forgetPassword(Usuario usuario) {
		return "esquecisenha";
	}
	
	@RequestMapping(value="/forget", method=RequestMethod.POST)
	public String sendForgetMail(Usuario usuario, Model model, HttpServletRequest req) {
		
		if(usuarioService.existsByEmail(usuario.getEmail())) {
			
			Usuario usuarioPers = usuarioService.findByEmail(usuario.getEmail());
			usuarioPers.setLocked(true);
			usuarioService.save(usuarioPers);
			
			String appUrl = req.getScheme() + "://" + req.getServerName();
			
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("digitalsutor@gmail.com");
			message.setTo(usuario.getEmail());
			message.setSubject("Digital Sutor :: Recuperação de Senha");
			message.setText("Para reiniciar sua senha, acesse o link abaixo: \n"
					+ appUrl + "/renew?token=" + usuarioPers.getToken());
			mailService.sendMail(message);
			model.addAttribute("successForgetMessage", "Um e-mail com instruções para reinício "
					+ "de senha foi enviado ");
			
		}else {
			model.addAttribute("errorForgetMessage", "Endereço de e-mail não cadastrado!");
		}
		
		return "esquecisenha";
	}
	
	@RequestMapping(value="/renew", method=RequestMethod.GET)
	public String renewPassword(@RequestParam("token") String token, Model model) {
		
		Usuario usuario = new Usuario();
		usuario.setToken(token);
		
		Usuario usuarioPers = usuarioService.findByToken(token);
		if(!usuarioPers.getLocked())
			return "confirmarError";
		
		if(!model.containsAttribute("usuario"))
			model.addAttribute("usuario", usuario);
		
		return "novasenha";
	}
	
	@RequestMapping(value="/renew", method=RequestMethod.POST)
	public String renewPassword(Usuario usuarioToken, RedirectAttributes rAttr, BindingResult binding, Model model) {

		Usuario usuario = usuarioService.findByToken(usuarioToken.getToken());
		
		if(usuario != null) {
			usuario.setPassword(usuarioToken.getPassword());
			usuario.setConfPass(usuarioToken.getConfPass());
		}else {
			return "confirmarError";
		}
		
		UsuarioValidation usuarioValidation = new UsuarioValidation();
		usuarioValidation.validatePassword(usuario, binding);
		
		if(binding.hasErrors())
			return renewPassword(usuario.getToken(), model);
			
		usuario.setLocked(false);
		usuarioService.save(usuario);
		rAttr.addFlashAttribute("registrationMessage", "Senha alterada com sucesso!");
		
		return "redirect:login";
	}
	
	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public String changeProfile(Usuario usuario, Model model) {
		Usuario usuarioLogado = usuarioLogadoService.usuarioLogado();
		model.addAttribute("nome", usuarioLogado.getNome());
		model.addAttribute("ultimonome", usuarioLogado.getUltimonome());
		
		if(usuario.getUsername() == null)
			model.addAttribute("usuario", usuarioLogado);

		if(usuario.getUsername() != null)
			model.addAttribute("usuario", usuario);
		
		return "perfil";
	}
	
	@RequestMapping(value="/profile", method=RequestMethod.POST)
	@CacheEvict("usuarioLogado")
	public String changeProfile(@Valid Usuario usuario, BindingResult binding, Model model) {
		
		if(binding.hasFieldErrors("nome") || binding.hasFieldErrors("email"))
			return changeProfile(usuario, model);
		
		Usuario usuarioPers = usuarioLogadoService.usuarioLogado();
		usuarioPers.setNome(usuario.getNome());
		usuarioPers.setUltimonome(usuario.getUltimonome());
		usuarioPers.setEmail(usuario.getEmail());
		usuarioService.save(usuarioPers);
		model.addAttribute("profileMessage", "Perfil alterado com sucesso!");
		
		return "perfil";
	}
	
	@RequestMapping(value="/password", method=RequestMethod.GET)
	public String changePassword(Usuario usuario, Model model) {
		Usuario usuarioLogado = usuarioLogadoService.usuarioLogado();
		model.addAttribute("nome", usuarioLogado.getNome());
		model.addAttribute("ultimonome", usuarioLogado.getUltimonome());
		
		if(usuario.getUsername() == null)
			model.addAttribute("usuario", usuarioLogado);

		if(usuario.getUsername() != null)
			model.addAttribute("usuario", usuario);
		
		return "alterarsenha";
	}
	
	@RequestMapping(value="/password", method=RequestMethod.POST)
	@CacheEvict("usuarioLogado")
	public String changePassword(@Valid Usuario usuario, BindingResult binding, Model model) {
		
		if(binding.hasFieldErrors("senha") || binding.hasFieldErrors("confPass"))
			return changePassword(usuario, model);
		
		Usuario usuarioPers = usuarioLogadoService.usuarioLogado();
		usuarioPers.setPassword(usuario.getPassword());
		usuarioPers.setConfPass(usuario.getConfPass());
		usuarioService.save(usuarioPers);
		model.addAttribute("passwordMessage", "Senha alterada com sucesso!");
		
		return "alterarsenha";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
