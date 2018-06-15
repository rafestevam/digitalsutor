package br.com.intelliapps.digitalsutor.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Usuario {

	@Id
	private String username;
	
	@NotNull
	@Size(max=500)
	private String nome;
	
	@Size(max=500)
	private String ultimonome;
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	@Size(min=6, max=10)
	private String password;
	
	@NotNull
	@Size(min=6, max=10)
	private String confPass;
	
	private Boolean accept;

	public String getUsername() {
		return username;
	}
	public void setUsername(String login) {
		this.username = login;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String senha) {
		this.password = senha;
	}
	public String getConfPass() {
		return confPass;
	}
	public void setConfPass(String confSenha) {
		this.confPass = confSenha;
	}
	public String getUltimonome() {
		return ultimonome;
	}
	public void setUltimonome(String ultimonome) {
		this.ultimonome = ultimonome;
	}
	public Boolean getAccept() {
		return accept;
	}
	public void setAccept(Boolean accept) {
		this.accept = accept;
	}
	
	
}
