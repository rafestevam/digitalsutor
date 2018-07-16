package br.com.intelliapps.digitalsutor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.intelliapps.digitalsutor.models.Produto;
import br.com.intelliapps.digitalsutor.models.TipoPreco;
import br.com.intelliapps.digitalsutor.models.Usuario;
import br.com.intelliapps.digitalsutor.services.UsuarioLogadoService;

@Controller
public class ProdutoController {

	@Autowired
	private UsuarioLogadoService usuarioLogadoService;
	
	@RequestMapping(value="/novoproduto", method=RequestMethod.GET)
	public String produtoForm(Produto produto, Model model) {
		
		Usuario usuario = usuarioLogadoService.usuarioLogado();
		model.addAttribute("nome", usuario.getNome());
		model.addAttribute("ultimonome", usuario.getUltimonome());
		
		TipoPreco[] tipoPrecos = TipoPreco.values();
		model.addAttribute("tipoPrecos", tipoPrecos);
		
		return "novoproduto";
	}
	
}
