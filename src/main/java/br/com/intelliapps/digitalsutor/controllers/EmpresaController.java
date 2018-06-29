package br.com.intelliapps.digitalsutor.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.intelliapps.digitalsutor.models.Empresa;
import br.com.intelliapps.digitalsutor.models.TipoAliq;
import br.com.intelliapps.digitalsutor.models.Usuario;
import br.com.intelliapps.digitalsutor.services.UsuarioLogadoService;
import br.com.intelliapps.digitalsutor.validation.EmpresaValidation;

@Controller
public class EmpresaController {
	
	@Autowired
	private UsuarioLogadoService usuarioLogadoService;
	
	@Autowired
	private MessageSource messageSource;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new EmpresaValidation());
	}
	
	@RequestMapping(value="/empresa", method=RequestMethod.GET)
	public String empresaForm(Empresa empresa, Model model) {
		
		Usuario usuario = usuarioLogadoService.usuarioLogado();
//		Set<Usuario> usuarios = new HashSet<Usuario>();
//		usuarios.add(usuario);
//		
//		Set<Aliquota> aliquotas = new HashSet<Aliquota>();
//		for(int i = 0; i < TipoAliq.values().length; i++) {
//			aliquotas.add(new Aliquota());
//		}		
//		
//		if(empresa.getAliquotas() == null)
//			empresa.setAliquotas(aliquotas);
//		
//		if(empresa.getUsuarios() == null)
//			empresa.setUsuarios(usuarios);
//		
//		if(!empresa.getUsuarios().contains(usuario))
//			empresa.setUsuarios(usuarios);
		
		model.addAttribute("nome", usuario.getNome());
		model.addAttribute("ultimonome", usuario.getUltimonome());
		model.addAttribute("empresa", empresa);
		model.addAttribute("tipoAliq", TipoAliq.values());
		
		return "empresa";
	}
	
	@RequestMapping(value="/empresa", method=RequestMethod.POST)
	public String criaEmpresa(@Valid Empresa empresa, BindingResult binding, Model model, RedirectAttributes rAttr) {
		model.addAttribute("empresa", empresa);
		
		if(binding.hasErrors())
			return empresaForm(empresa, model);
		
		if(empresa.getAliquotas().size() > 1) {
			model.addAttribute("errorMessage", messageSource.getMessage("field.error.aliquota.morethanone", new String[] {}, LocaleContextHolder.getLocale()));
			return empresaForm(empresa, model);
		}
		
		//model.addAttribute("registrationMessage", "Empresa cadastrada com sucesso!");
		rAttr.addFlashAttribute("registrationMessage", "Empresa cadastrada com sucesso!");

		return "redirect:dashboard";
	}
	
	
	
}
