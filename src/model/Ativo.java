package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Ativo {
	
	private int id;
	private String ticker;
	private String nome;
	private String tipo;
	private BigDecimal valorCompra;
	private LocalDate dataCompra = LocalDate.now();
	
	
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

        public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public BigDecimal getValorCompra() {
        return valorCompra;
    }
    
    
    

    public void setValorCompra(BigDecimal valorCompra) {
        this.valorCompra = valorCompra;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }
    
   
    public int hashCode() {
    	return Objects.hash(getId(), getNome(), getTipo(), getValorCompra(), getDataCompra());
    }
    
    
    public String toString() {
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append(getId() + " - ");
    	sb.append(getNome() + " - ");
    	sb.append(getTicker() + " - ");
    	sb.append(getTipo() + " - ");
    	sb.append(getValorCompra() + " - ");
    	sb.append(getDataCompra() + " - ");
    	
    	return sb.toString();
    	
    }
	
    public boolean equals(Object o) {
    	
    	if(o instanceof Ativo) {
    		Ativo obj = (Ativo)o;
    		
    		return
    				obj.getId() == getId() &&
    				obj.getNome() == getNome()&&
    				obj.getTicker() == getTicker()&&
    				obj.getTipo() == getTipo()&&
    				obj.getValorCompra() == getValorCompra()&&
    				obj.getDataCompra() == getDataCompra();    		
    	}else {
    		return false;
    	}
    	
    }

	

}
