package model;

import java.math.BigDecimal;

public class Dashboard {

	private BigDecimal totalInvestido;
	private BigDecimal valorAtual;
	private BigDecimal valorizacao;
	private BigDecimal desvalorizacao;

	public Dashboard(BigDecimal totalInvestido, BigDecimal valorizacao, BigDecimal desvalorizacao) {
		if (totalInvestido == null || totalInvestido.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Total investido inválido");
		}
		if (valorizacao == null) {
			throw new IllegalArgumentException("Valorização inválida");
		}
		if (desvalorizacao == null) {
			throw new IllegalArgumentException("Desvalorização inválida");
		}
		this.totalInvestido = totalInvestido;
		this.valorizacao = valorizacao;
		this.desvalorizacao = desvalorizacao;
	}

	public BigDecimal getValorizacao() {
		return valorizacao;
	}

	public void setValorizacao(BigDecimal valorizacao) {
		if (valorizacao == null) {
			throw new IllegalArgumentException("Valorização inválida");
		}
		this.valorizacao = valorizacao;
	}

	public BigDecimal getValorAtual() {
		return valorAtual;
	}

	public void setValorAtual(BigDecimal valorAtual) {
		if (valorAtual == null || valorAtual.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Valor atual inválido");
		}
		this.valorAtual = valorAtual;
	}

	public BigDecimal getTotalInvestido() {
		return totalInvestido;
	}

	public void setTotalInvestido(BigDecimal totalInvestido) {
		if (totalInvestido == null || totalInvestido.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Total investido inválido");
		}
		this.totalInvestido = totalInvestido;
	}

	public BigDecimal getDesvalorizacao() {
		return desvalorizacao;
	}

	public void setDesvalorizacao(BigDecimal desvalorizacao) {
		if (desvalorizacao == null) {
			throw new IllegalArgumentException("Desvalorização inválida");
		}
		this.desvalorizacao = desvalorizacao;
	}
}