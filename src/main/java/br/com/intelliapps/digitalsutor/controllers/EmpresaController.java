package br.com.intelliapps.digitalsutor.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.intelliapps.digitalsutor.models.Empresa;
import br.com.intelliapps.digitalsutor.models.TipoAliq;
import br.com.intelliapps.digitalsutor.models.Usuario;
import br.com.intelliapps.digitalsutor.services.EmpresaService;
import br.com.intelliapps.digitalsutor.services.UsuarioLogadoService;
import br.com.intelliapps.digitalsutor.services.UsuarioService;
import br.com.intelliapps.digitalsutor.validation.EmpresaValidation;

@Controller
public class EmpresaController {
	
	@Autowired
	private UsuarioLogadoService usuarioLogadoService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private Locale locale;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new EmpresaValidation());
	}
	
	@RequestMapping(value="/empresa", method=RequestMethod.GET)
	public String empresaForm(Empresa empresa, Model model) {
		
		Usuario usuario = usuarioLogadoService.usuarioLogado();
		empresa.getUsuarios().add(usuario);
		
		model.addAttribute("nome", usuario.getNome());
		model.addAttribute("ultimonome", usuario.getUltimonome());
		model.addAttribute("empresa", empresa);
		model.addAttribute("tipoAliq", TipoAliq.values());
		
		return "empresa";
	}
	
	@RequestMapping(value="/alterar/{id}", method=RequestMethod.GET)
	public String empresaForm(@PathVariable String id, Model model) {
		
		Usuario usuario = usuarioLogadoService.usuarioLogado();
		Optional<Empresa> empresaOpt = empresaService.findById(Long.valueOf(id).longValue());
		
		Empresa empresa = empresaOpt.get();
		
		model.addAttribute("nome", usuario.getNome());
		model.addAttribute("ultimonome", usuario.getUltimonome());
		model.addAttribute("empresa", empresa);
		model.addAttribute("tipoAliq", TipoAliq.values());
		model.addAttribute("update", true);
		
		return "empresa";
	}
	
	@RequestMapping(value="/empresa", method=RequestMethod.POST, params="action=create")
	public String criaEmpresa(@Valid Empresa empresa, BindingResult binding, Model model, RedirectAttributes rAttr) {
		model.addAttribute("empresa", empresa);
		
		if(binding.hasErrors())
			return empresaForm(empresa, model);
		
		long aliqPreenchidas = empresa.getAliquotas().stream()
			.filter(aliq -> Objects.nonNull(aliq.getAliquota()))
			.filter(aliq -> aliq.getAliquota().compareTo(BigDecimal.ZERO) > 0)
			.count();
		
		if(aliqPreenchidas > 1) {
			model.addAttribute("errorMessage", messageSource.getMessage("field.error.aliquota.morethanone", new String[] {}, locale));
			return empresaForm(empresa, model);
		}
		
		if(aliqPreenchidas < 1) {
			model.addAttribute("errorMessage", messageSource.getMessage("field.error.aliquota.novalues", new String[] {}, locale));
			return empresaForm(empresa, model);
		}
		
		if(empresaService.existsByCNPJ(empresa.getCnpj())) {
			binding.rejectValue("cnpj", "field.error.exists", new String[] {"CNPJ"}, "");
			return empresaForm(empresa, model);
		}
		
		List<Usuario> usuariosCopia = new ArrayList<Usuario>();
		empresa.getUsuarios().stream()
			.filter(user -> Objects.nonNull(user.getUsername()))
			.forEach(user -> usuariosCopia.add(usuarioService.findByUsername(user.getUsername())));
		
		empresa.getUsuarios().clear();
		empresa.setUsuarios(usuariosCopia);
		
		empresaService.save(empresa);
		rAttr.addFlashAttribute("registrationMessage", messageSource.getMessage("message.empresa.create.success", new String[] {}, locale));

		return "redirect:minhasempresas";
	}

	@RequestMapping(value="/empresa", method=RequestMethod.POST, params="action=update")
	public String alterarEmpresa(@Valid Empresa empresa, BindingResult binding, Model model, RedirectAttributes rAttr) {
		model.addAttribute("empresa", empresa);
		
		if(binding.hasErrors())
			return empresaForm(empresa, model);
		
		long aliqPreenchidas = empresa.getAliquotas().stream()
				.filter(aliq -> Objects.nonNull(aliq.getAliquota()))
				.filter(aliq -> aliq.getAliquota().compareTo(BigDecimal.ZERO) > 0)
				.count();
		
		if(aliqPreenchidas > 1) {
			model.addAttribute("errorMessage", messageSource.getMessage("field.error.aliquota.morethanone", new String[] {}, locale));
			return empresaForm(empresa, model);
		}
		
		if(aliqPreenchidas < 1) {
			model.addAttribute("errorMessage", messageSource.getMessage("field.error.aliquota.novalues", new String[] {}, locale));
			return empresaForm(empresa, model);
		}
		
		List<Usuario> usuariosCopia = new ArrayList<Usuario>();
		empresa.getUsuarios().stream()
		.filter(user -> Objects.nonNull(user.getUsername()))
		.forEach(user -> usuariosCopia.add(usuarioService.findByUsername(user.getUsername())));
		
		empresa.getUsuarios().clear();
		empresa.setUsuarios(usuariosCopia);
		
		empresaService.save(empresa);
		rAttr.addFlashAttribute("registrationMessage", messageSource.getMessage("message.empresa.update.success", new String[] {}, locale));
		
		return "redirect:minhasempresas";
	}
	
	@RequestMapping(value="/minhasempresas", method=RequestMethod.GET)
	public String listaEmpresas(Model model) {
		
		Usuario usuario = usuarioLogadoService.usuarioLogado();
		List<Empresa> empresas = empresaService.findByUsername(usuario.getUsername());
		
		model.addAttribute("nome", usuario.getNome());
		model.addAttribute("ultimonome", usuario.getUltimonome());
		model.addAttribute("empresas", empresas);
		
		return "listaempresas";
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String deleteEmpresa(@PathVariable String id, RedirectAttributes rAttr) {
		
		empresaService.delete(Long.valueOf(id).longValue());
		rAttr.addFlashAttribute("registrationMessage", messageSource.getMessage("message.empresa.delete.success", new String[] {}, locale));
		
		return "redirect:/minhasempresas";
	}
	
	
	
}
