package model;

import java.math.BigDecimal;

public class Dashboard {

	private BigDecimal totalInvestido;
	private BigDecimal valorAtual;
	private BigDecimal valorizacao;
	private BigDecimal desvalorizacao;
	
	
	public BigDecimal getValorizacao() {
		return valorizacao;
	}
	public void setValorizacao(BigDecimal lucroPrejuizo) {
		this.valorizacao= lucroPrejuizo;
	}
	public BigDecimal getValorAtual() {
		return valorAtual;
	}
	public void setValorAtual(BigDecimal valorAtual) {
		this.valorAtual = valorAtual;
	}
	public BigDecimal getTotalInvestido() {
		return totalInvestido;
	}
	public void setTotalInvestido(BigDecimal totalInvestido) {
		this.totalInvestido = totalInvestido;
	}
	public BigDecimal getDesvalorizacao() {
		return desvalorizacao;
	}
	public void setDesvalorizacao(BigDecimal desvalorizacao) {
		this.desvalorizacao = desvalorizacao;
	}
	
}
