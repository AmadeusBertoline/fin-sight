package controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import dao.AtivoDAO;
import dao.AtivoDAOImpl;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Ativo;

public class AtivoController {
	
	//propertys para fazer bindings nos TextFields
	private final LongProperty id = new SimpleLongProperty(0);
	private final StringProperty ticker = new SimpleStringProperty("");
	private final StringProperty nome = new SimpleStringProperty("");
	private final StringProperty tipo = new SimpleStringProperty("");
	private final StringProperty quantidade = new SimpleStringProperty("");
	private final ObjectProperty<BigDecimal> valorCompra = new SimpleObjectProperty<>(BigDecimal.ZERO);
	private ObjectProperty<LocalDate> dataCompra = new SimpleObjectProperty<>(LocalDate.now());
	private ObservableList<Ativo> lista = FXCollections.observableArrayList();
	private final ObjectProperty<BigDecimal> totalInvestido = new SimpleObjectProperty<>(BigDecimal.ZERO);
	//passar o valor para dentro da property
	
	private AtivoDAO dao = new AtivoDAOImpl();
	
	//retorno da PROPRIEDADE
	public LongProperty idProperty() {return id;}
	public StringProperty tickerProperty() {return ticker;}
	public StringProperty nomeProperty() {return nome;}
	public StringProperty tipoProperty() {return tipo;}
	public StringProperty quantidadeProperty() {return quantidade;}
	public ObjectProperty<BigDecimal> valorCompraProperty() {return valorCompra;}
	public ObjectProperty<LocalDate> datProperty() {return dataCompra;}
	public ObjectProperty<BigDecimal> totalInvestidoProperty() {return totalInvestido;}
	
	public String getTicker() {
		return ticker.get();
	}
	
	public String getNome() {
		return ticker.get();
	}
	
	public double getQuantidade() {
		return Double.parseDouble(quantidade.get());
	}
	
	public BigDecimal getValorCompra() {
		try {
			return valorCompra.get();
		}catch(Exception e) {
			return BigDecimal.ZERO;
		}
	}
	
	public BigDecimal getTotalInvestido() {
		try {
			return valorCompra.get();
		}catch(Exception e){
			return BigDecimal.ZERO;
		}
	}
	
	public Ativo paraEntidade() {
		
		Ativo a = new Ativo();
		a.setNome(nome.get());
		a.setTicker(ticker.get());
		a.setQuantidade(BigDecimal.valueOf(getQuantidade()));
		a.setValorCompra(valorCompra.get());
		
		return a;
		
	}
	
	public void paraTela(Ativo a) {
		
		id.set(a.getId());
		ticker.set(a.getTicker());
		nome.set(a.getNome());
		quantidade.set(String.valueOf(a.getQuantidade()));
		valorCompra.set((a.getValorCompra()));
		
	}
	
	public void salvar(Ativo a, String dataString) {
		
		if(a.getId() == 0) {
			dao.salvar(a);
		}else {
			dao.atualizar(a, a.getId());
		}
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		try{
			
			LocalDate data = LocalDate.parse(dataString, dtf);
			a.setDataCompra(data);
			
		}catch(Exception e) {
			System.out.println("Data inválida, use o formato dd/MM/yyyy");
		}
		
		atualizarLista();
		
	}
	
	public void atualizarLista() {
		
		lista.clear();
		lista.addAll(dao.listar());
		
	}
	
	public ObservableList<Ativo> getLista(){
		return lista;
	}
	
	public void excluir(long id){
		
		dao.excluir(id);
		atualizarLista();
		
	}

	public Ativo pesquisar(String nome) {
		
		Ativo a = new Ativo();
	
		return a;
	}
	
	public BigDecimal totalGeral() {
		return dao.totalGeral();
	}
	

	
}
