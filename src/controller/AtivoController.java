package controller;

import java.math.BigDecimal;
import dao.AtivoDAO;
import dao.AtivoDAOImpl;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Ativo;
import util.Alerta;

public class AtivoController {

	// propertys para fazer bindings nos TextFields
	private final IntegerProperty id = new SimpleIntegerProperty(0);
	private final StringProperty ticker = new SimpleStringProperty("");
	private final StringProperty nome = new SimpleStringProperty("");
	private final StringProperty tipo = new SimpleStringProperty("");
	private final StringProperty quantidade = new SimpleStringProperty("");
	private final ObjectProperty<BigDecimal> valorCompra = new SimpleObjectProperty<>(BigDecimal.ZERO);
	private final StringProperty pesquisa = new SimpleStringProperty();
	private ObservableList<Ativo> lista = FXCollections.observableArrayList();
	// passar o valor para dentro da property

	private AtivoDAO dao = new AtivoDAOImpl();

	// retorno da PROPRIEDADE
	public IntegerProperty idProperty() {
		return id;
	}

	public StringProperty tickerProperty() {
		return ticker;
	}

	public StringProperty nomeProperty() {
		return nome;
	}

	public StringProperty tipoProperty() {
		return tipo;
	}

	public StringProperty quantidadeProperty() {
		return quantidade;
	}

	public ObjectProperty<BigDecimal> valorCompraProperty() {
		return valorCompra;
	}

	public StringProperty pesquisaProperty() {
		return pesquisa;
	}

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
		} catch (Exception e) {
			return BigDecimal.ZERO;
		}
	}

	public Ativo paraEntidade() {

		Ativo a = new Ativo();
		a.setId(id.get());
		a.setNome(nome.get());
		a.setTipo(tipo.get());
		a.setTicker(ticker.get());
		a.setValorCompra(valorCompra.get());

		return a;

	}

	public void paraTela(Ativo a) {

		id.set(a.getId());
		ticker.set(a.getTicker());
		nome.set(a.getNome());
		tipo.set(a.getTipo());
		valorCompra.set((a.getValorCompra()));

	}

	public void salvar(Ativo a) {

		if (a.getId() == 0) {
			dao.salvar(a);
		} else {
			dao.atualizar(a, a.getId());

		}

		atualizarLista();
		limparCampos();

	}

	public void atualizar(Ativo a, int id) {

		dao.atualizar(a, id);
		atualizarLista();
		limparCampos();

	}

	public void atualizarLista() {

		lista.clear();
		lista.addAll(dao.listar());

	}

	public ObservableList<Ativo> getLista() {
		return lista;
	}

	public void excluir(int id) {

		boolean confirmou = Alerta.confirmar("Excluir Ativo", "Tem certeza que deseja excluir o ativo?");

		if (dao.ativoEmUso(id) == false) {

			if (confirmou) {
				dao.excluir(id);
				Alerta.sucesso("Sucesso", "Ativo removido com sucesso");
				atualizarLista();
				limparCampos();
			}

		} else {
			Alerta.erro("Erro", "Você não pode excluir um ativo que já foi registrado em uma compra");
		}

	}

	public Ativo pesquisar(String nome) {

		Ativo a = new Ativo();

		return a;
	}

	public void limparCampos() {
		id.set(0);
		ticker.set("");
		nome.set("");
		tipo.set("");
		valorCompra.set(null);
		pesquisa.set("");
	}

}
