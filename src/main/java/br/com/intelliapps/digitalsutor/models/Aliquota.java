package br.com.intelliapps.digitalsutor.models;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public class Aliquota {

	private BigDecimal aliquota;
	private TipoAliq tipo;
	
	public BigDecimal getAliquota() {
		return aliquota;
	}
	public void setAliquota(BigDecimal aliquota) {
		this.aliquota = aliquota;
	}
	public TipoAliq getTipo() {
		return tipo;
	}
	public void setTipo(TipoAliq tipo) {
		this.tipo = tipo;
	}
	
}
