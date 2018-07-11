package br.com.intelliapps.digitalsutor.models;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public class Etapa {

	private Long ordem;
	private String nomeEtapa;
	private String descricao;
	private BigDecimal ducarao;
	
	public Long getOrdem() {
		return ordem;
	}
	
	public void setOrdem(Long ordem) {
		this.ordem = ordem;
	}
	
	public String getNomeEtapa() {
		return nomeEtapa;
	}
	
	public void setNomeEtapa(String nomeEtapa) {
		this.nomeEtapa = nomeEtapa;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getDucarao() {
		return ducarao;
	}

	public void setDucarao(BigDecimal ducarao) {
		this.ducarao = ducarao;
	}

}
