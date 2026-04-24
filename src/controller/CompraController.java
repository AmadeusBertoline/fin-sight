package controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import dao.CompraDAOImpl;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Ativo;
import model.Compra;

public class CompraController {

	private final IntegerProperty id = new SimpleIntegerProperty(0);
	private final ObjectProperty<Ativo> ativo = new SimpleObjectProperty<>();
	private final ObjectProperty<BigDecimal> quantidade = new SimpleObjectProperty<>(BigDecimal.ZERO);
	private final ObjectProperty<LocalDate> dataCompra = new SimpleObjectProperty<>(LocalDate.now());
	private final ObjectProperty<BigDecimal> valorCompra = new SimpleObjectProperty<>(BigDecimal.ZERO);
	private final ObjectProperty<BigDecimal> valorUnitario = new SimpleObjectProperty<>(BigDecimal.ZERO);
	private final StringProperty empresa = new SimpleStringProperty("");
	private final StringProperty pesquisa = new SimpleStringProperty("");

	public CompraController() {

		valorCompra.bind(Bindings.createObjectBinding(() -> {
			if (quantidade.get() == null || valorUnitario.get() == null) {
				return BigDecimal.ZERO;
			}
			return quantidade.get().multiply(valorUnitario.get());
		}, quantidade, valorUnitario));

	}

	private CompraDAOImpl dao = new CompraDAOImpl();
	private ObservableList<Compra> lista = FXCollections.observableArrayList();

	public IntegerProperty idProperty() {
		return id;
	}

	public StringProperty pesquisaProperty() {
		return pesquisa;
	}

	public StringProperty empresaProperty() {
		return empresa;
	}

	public ObjectProperty<BigDecimal> quantidadeProperty() {
		return quantidade;
	}

	public ObjectProperty<BigDecimal> valorCompraProperty() {
		return valorCompra;
	}

	public ObjectProperty<BigDecimal> valorUnitarioProperty() {
		return valorUnitario;
	}

	public ObjectProperty<LocalDate> dataCompraProperty() {
		return dataCompra;
	}

	public ObjectProperty<Ativo> ativoProperty() {
		return ativo;
	}

	public Compra paraEntidade() {
		Compra c = new Compra();
		c.setId(id.get());
		c.setAtivo(ativo.get());
		c.setQuantidade(quantidade.get());
		c.setValorCompra(valorCompra.get());
		c.setDataCompra(dataCompra.get());
		return c;
	}

	public void paraTela(Compra c) {

		id.set(c.getId());
		ativo.set(c.getAtivo());
		quantidade.set(c.getQuantidade());
		dataCompra.set(c.getDataCompra());

		if (c.getAtivo() != null) {
			valorUnitario.set(c.getAtivo().getValorCompra());
		} else {
			valorUnitario.set(BigDecimal.ZERO);
		}

	}

	public void salvar(Compra c, String dataString, Ativo a) {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		try {
			LocalDate data = LocalDate.parse(dataString, dtf);
			c.setAtivo(a);
			c.setDataCompra(data);
		} catch (Exception e) {
			System.out.println("Data inválida");
		}

		if (c.getId() == 0) {
			dao.salvar(c);
		} else {
			dao.atualizar(c, c.getId());
		}

		atualizarLista();
		limparCampos();
	}

	public void excluir(int id) {
		if (id > 0) {
			dao.excluir(id);
			atualizarLista();
			limparCampos();
		} else {
			System.out.println("Selecione um item para excluir");
		}
	}

	public void atualizarLista() {
		lista.clear();
		lista.addAll(dao.listar());
	}

	public ObservableList<Compra> getLista() {
		return lista;
	}

	public void limparCampos() {
		ativo.set(null);
		id.set(0);
		quantidade.set(BigDecimal.ZERO);
		dataCompra.set(LocalDate.now());
		valorUnitario.set(BigDecimal.ZERO);
		empresa.set("");
		pesquisa.set("");
	}

	public void atualizar(Compra c, int id) {

		dao.atualizar(c, id);
		atualizarLista();
		limparCampos();

	}

	public void busca() {

		lista.clear();
		lista.addAll(dao.busca(pesquisa.get()));

	}
}
