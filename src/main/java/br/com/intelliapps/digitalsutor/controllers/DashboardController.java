package br.com.intelliapps.digitalsutor.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController {

	@RequestMapping("/dashboard")
	public String acessaDash() {
		return "dashboard";
	}
	
}
