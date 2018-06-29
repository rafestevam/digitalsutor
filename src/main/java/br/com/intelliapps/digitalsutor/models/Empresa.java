package br.com.intelliapps.digitalsutor.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
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
	
	private String cNPJ;
	
	@ElementCollection
	private List<Aliquota> aliquotas = new ArrayList<Aliquota>();
	
	@ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name="empresa_usuarios", joinColumns=@JoinColumn(name="empresa_id"), inverseJoinColumns=@JoinColumn(name="usuario_id"))
	private List<Usuario> usuarios = new ArrayList<Usuario>();

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

	public List<Aliquota> getAliquotas() {
		return aliquotas;
	}

	public void setAliquotas(List<Aliquota> aliquota) {
		this.aliquotas = aliquota;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
}
