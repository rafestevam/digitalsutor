package br.com.intelliapps.digitalsutor.models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Empresa {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String nome;
	
	@Lob
	private String descricao;
	
	@NotNull
	private String cNPJ;
	
	private Aliquota aliquota;
	
	@ManyToMany
	@JoinTable(name="empresa_usuarios", joinColumns=@JoinColumn(name="empresa_id"), inverseJoinColumns=@JoinColumn(name="usuario_id"))
	private Set<Usuario> usuarios; // = new ArrayList<Usuario>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCnpj() {
		return cNPJ;
	}

	public void setCnpj(String cnpj) {
		this.cNPJ = cnpj;
	}

	public Aliquota getAliquota() {
		return aliquota;
	}

	public void setAliquota(Aliquota aliquota) {
		this.aliquota = aliquota;
	}

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
}
