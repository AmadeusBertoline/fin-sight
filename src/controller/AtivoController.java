package controller;

import java.math.BigDecimal;
import java.sql.SQLException;

import dao.ativo.AtivoDAO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Ativo;
import view.components.Alerta;

public class AtivoController {

	private final IntegerProperty id = new SimpleIntegerProperty(0);
	private final StringProperty ticker = new SimpleStringProperty("");
	private final StringProperty nome = new SimpleStringProperty("");
	private final StringProperty tipo = new SimpleStringProperty("");
	private final StringProperty quantidade = new SimpleStringProperty("");
	private final ObjectProperty<BigDecimal> valorCompra = new SimpleObjectProperty<>(BigDecimal.ZERO);
	private final StringProperty pesquisa = new SimpleStringProperty();
	private ObservableList<Ativo> lista = FXCollections.observableArrayList();

	private AtivoDAO dao;

	public AtivoController(AtivoDAO dao) {
		this.dao = dao;
	}

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

		if (valorCompra.get() == null) {
			Alerta.erro("Entrada Inválida",
					"O campo Valor contém caracteres inválidos. Use apenas números e ponto/vírgula.");
			return;
		}

		if (a.getTicker() == null || a.getTicker().isBlank()) {
			Alerta.erro("Validação", "O campo Ticker é obrigatório.");
			return;
		}

		if (a.getNome() == null || a.getNome().isBlank()) {
			Alerta.erro("Validação", "O campo Nome é obrigatório.");
			return;
		}

		if (a.getTipo() == null || a.getTipo().isBlank()) {
			Alerta.erro("Validação", "O campo Tipo é obrigatório.");
			return;
		}

		if (a.getValorCompra().compareTo(BigDecimal.ZERO) < 0) {
			Alerta.erro("Validação", "O valor unitário não pode ser negativo.");
			return;
		}

		try {
			if (a.getId() == 0) {
				dao.salvar(a);
				Alerta.sucesso("Sucesso", "Ativo salvo com sucesso!");
			} else {
				dao.atualizar(a, a.getId());
				Alerta.sucesso("Sucesso", "Ativo atualizado com sucesso!");
			}

			atualizarLista();
			limparCampos();

		} catch (SQLException e) {
			e.printStackTrace();
			Alerta.erro("Erro de Banco", "Falha ao comunicar com o banco de dados: " + e.getMessage());
		} catch (Exception e) {
			Alerta.erro("Erro Inesperado", "Ocorreu um erro sistêmico: " + e.getMessage());
		}
	}

	public void atualizar(Ativo a, int id) {

		if (a.getTicker() == null || a.getTicker().isBlank() || a.getNome() == null || a.getNome().isBlank()) {
			Alerta.erro("Validação", "Preencha todos os campos obrigatórios.");
			return;
		}

		try {
			dao.atualizar(a, id);
			Alerta.sucesso("Sucesso", "Ativo atualizado com sucesso!");
			atualizarLista();
			limparCampos();
		} catch (SQLException e) {
			e.printStackTrace();
			Alerta.erro("Erro de Banco", "Falha ao comunicar com o banco de dados: " + e.getMessage());
		} catch (Exception e) {
			Alerta.erro("Erro Inesperado", "Ocorreu um erro sistêmico: " + e.getMessage());
		}
	}

	public void atualizarLista() {

		try {
			lista.clear();
			lista.addAll(dao.listar());
		} catch (SQLException e) {
			e.printStackTrace();
			Alerta.erro("Erro de Banco", "Falha ao comunicar com o banco de dados: " + e.getMessage());
		} catch (Exception e) {
			Alerta.erro("Erro Inesperado", "Ocorreu um erro sistêmico: " + e.getMessage());
		}
	}

	public ObservableList<Ativo> getLista() {
		return lista;
	}

	public void excluir(int id) {

		try {

			if (id <= 0) {
				Alerta.erro("Erro", "Selecione um ativo válido para exclusão.");
				return;
			}

			boolean confirmou = Alerta.confirmar("Excluir Ativo", "Tem certeza que deseja excluir o ativo?");

			if (confirmou) {
				if (!dao.ativoEmUso(id)) {
					dao.excluir(id);
					Alerta.sucesso("Sucesso", "Ativo removido com sucesso");
					atualizarLista();
					limparCampos();
				} else {
					Alerta.erro("Erro", "Você não pode excluir um ativo que já foi registrado em uma compra");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			Alerta.erro("Erro de Banco", "Falha ao comunicar com o banco de dados: " + e.getMessage());
		} catch (Exception e) {
			Alerta.erro("Erro Inesperado", "Ocorreu um erro sistêmico: " + e.getMessage());
		}
	}

	public void pesquisar(String nome) {

		lista.clear();
		try {
			lista.addAll(dao.pesquisarPorNome(pesquisa.get()));
		} catch (SQLException e) {
			e.printStackTrace();
			Alerta.erro("Erro de Banco", "Falha ao comunicar com o banco de dados: " + e.getMessage());
		} catch (Exception e) {
			Alerta.erro("Erro Inesperado", "Ocorreu um erro sistêmico: " + e.getMessage());
		}
	}

	public void limparCampos() {
		id.set(0);
		ticker.set("");
		nome.set("");
		tipo.set("");
		valorCompra.set(BigDecimal.ZERO);
		pesquisa.set("");
	}
}