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
		this.id = id;
	}

	public LocalDate getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(LocalDate dataCompra) {
		this.dataCompra = dataCompra;
	}

	public BigDecimal getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(BigDecimal valorCompra) {
		this.valorCompra = valorCompra;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public Ativo getAtivo() {
		return ativo;
	}

	public void setAtivo(Ativo ativo) {
		this.ativo = ativo;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getAtivo(), getQuantidade(), getDataCompra(), getValorCompra(), getValorPago());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Compra))
			return false;

		Compra obj = (Compra) o;

		return getId() == obj.getId() && Objects.equals(getAtivo(), obj.getAtivo())
				&& Objects.equals(getQuantidade(), obj.getQuantidade())
				&& Objects.equals(getDataCompra(), obj.getDataCompra())
				&& Objects.equals(getValorCompra(), obj.getValorCompra())
				&& Objects.equals(getValorPago(), obj.getValorPago());
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
