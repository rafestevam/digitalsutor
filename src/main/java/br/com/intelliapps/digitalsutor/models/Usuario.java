package br.com.intelliapps.digitalsutor.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;

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
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Role> roles = new ArrayList<Role>();

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
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.roles;
	}
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	
}
