package br.com.intelliapps.digitalsutor.services;

public interface SecurityService {

	public String findInLoggedUsername();
	
	public void autologin(String username, String password);
}
