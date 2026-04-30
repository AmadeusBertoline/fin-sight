package model;

import java.math.BigDecimal;
import java.util.Objects;

public class Ativo {

	private int id;
	private String ticker;
	private String nome;
	private String tipo;
	private BigDecimal valorCompra;
	private BigDecimal quantidadeTotal;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if (id < 0) {
			throw new IllegalArgumentException("Id inválido");
		}
		this.id = id;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		if (ticker == null || ticker.isBlank()) {
			throw new IllegalArgumentException("Ticker inválido");
		}
		this.ticker = ticker;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		if (tipo == null || tipo.isBlank()) {
			throw new IllegalArgumentException("Tipo inválido");
		}
		this.tipo = tipo;
	}

	public BigDecimal getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(BigDecimal valorCompra) {
		if (valorCompra == null || valorCompra.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Valor de compra inválido");
		}
		this.valorCompra = valorCompra;
	}

	public BigDecimal getQuantidadeTotal() {
		return quantidadeTotal;
	}

	public void setQuantidadeTotal(BigDecimal quantidadeTotal) {
		if (quantidadeTotal == null || quantidadeTotal.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Quantidade inválida");
		}
		this.quantidadeTotal = quantidadeTotal;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getId()).append(" - ");
		sb.append(getNome()).append(" - ");
		sb.append(getTicker()).append(" - ");
		sb.append(getTipo()).append(" - ");
		sb.append(getValorCompra()).append(" - ");
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Ativo)) return false;
		Ativo ativo = (Ativo) o;
		return id == ativo.id;
	}
}