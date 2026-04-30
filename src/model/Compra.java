package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Compra {

	private int id;
	private Ativo ativo;
	private BigDecimal quantidade;
	private LocalDate dataCompra = LocalDate.now();
	private BigDecimal valorCompra;
	private BigDecimal valorPago;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if (id < 0) {
			throw new IllegalArgumentException("Id inválido");
		}
		this.id = id;
	}

	public LocalDate getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(LocalDate dataCompra) {
		if (dataCompra == null || dataCompra.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("Data inválida");
		}
		this.dataCompra = dataCompra;
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

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		if (quantidade == null || quantidade.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Quantidade inválida");
		}
		this.quantidade = quantidade;
	}

	public Ativo getAtivo() {
		return ativo;
	}

	public void setAtivo(Ativo ativo) {
		if (ativo == null) {
			throw new IllegalArgumentException("Ativo inválido");
		}
		this.ativo = ativo;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		if (valorPago == null || valorPago.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Valor pago inválido");
		}
		this.valorPago = valorPago;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Compra)) return false;
		Compra compra = (Compra) o;
		return id == compra.id;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getId()).append(" - ");
		sb.append(getAtivo()).append(" - ");
		sb.append(getQuantidade()).append(" - ");
		sb.append(getDataCompra()).append(" - ");
		sb.append(getValorCompra()).append(" - ");
		sb.append(getValorPago());
		return sb.toString();
	}
}