package br.com.intelliapps.digitalsutor.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String nome;
	
	@Lob
	private String descricao;
	
	@ElementCollection
	private List<Preco> precos = new ArrayList<Preco>();
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="produto_id")
	private Empresa empresa;
	
	@OneToOne(mappedBy="produto", cascade= {CascadeType.PERSIST, CascadeType.MERGE}, optional=false, fetch=FetchType.LAZY)
	private Receita receita;

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

	public List<Preco> getPrecos() {
		return precos;
	}

	public void setPrecos(List<Preco> precos) {
		this.precos = precos;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Receita getReceita() {
		return receita;
	}

	public void setReceita(Receita receita) {
		if(receita == null) {
			if(this.receita != null) {
				this.receita.setProduto(null);
			}
		}else {
			receita.setProduto(this);
		}
		
		this.receita = receita;
	}
	
}
