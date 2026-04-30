package model;

import java.math.BigDecimal;
import java.util.Objects;

public class TipoInvestimento {
	private String nome;
	private BigDecimal valor;

	public TipoInvestimento(String nome, BigDecimal valor) {
		if (nome == null || nome.isBlank()) {
			throw new IllegalArgumentException("Nome inválido");
		}
		if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Valor inválido");
		}
		this.nome = nome;
		this.valor = valor;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if (nome == null || nome.isBlank()) {
			throw new IllegalArgumentException("Nome inválido");
		}
		this.nome = nome;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Valor inválido");
		}
		this.valor = valor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nome);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TipoInvestimento)) return false;
		TipoInvestimento obj = (TipoInvestimento) o;
		return Objects.equals(nome, obj.nome);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getNome()).append(" - ");
		sb.append(getValor());
		return sb.toString();
	}
}