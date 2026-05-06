package controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import dao.ativo.AtivoDAO;
import dao.compra.CompraDAOImpl;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Ativo;
import model.Compra;
import view.components.Alerta;

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
		c.setValorPago(valorUnitario.get());
		return c;
	}

	public void paraTela(Compra c) {

		id.set(c.getId());
		ativo.set(c.getAtivo());
		quantidade.set(c.getQuantidade());
		dataCompra.set(c.getDataCompra());
		valorUnitario.set(c.getValorPago());

		if (c.getAtivo() != null) {
			valorUnitario.set(c.getAtivo().getValorCompra());
		} else {
			valorUnitario.set(BigDecimal.ZERO);
		}

	}

	public void salvar(Compra c, String dataString, Ativo a) {

		if (a == null) {
			Alerta.erro("Validação", "Selecione um ativo para registrar a compra.");
			return;
		}

		if (c.getQuantidade() == null || c.getQuantidade().compareTo(BigDecimal.ZERO) <= 0) {
			Alerta.erro("Validação", "A quantidade deve ser maior que zero.");
			return;
		}

		if (c.getValorPago() == null || c.getValorPago().compareTo(BigDecimal.ZERO) <= 0) {
			Alerta.erro("Validação", "O valor unitário deve ser maior que zero.");
			return;
		}

		if (quantidade.get() == null) {
			Alerta.erro("Entrada Inválida",
					"O campo Quantidade contém caracteres inválidos (como #). Use apenas números.");
			return;
		}

		if (quantidade.get().compareTo(BigDecimal.ZERO) <= 0) {
			Alerta.erro("Validação", "A quantidade deve ser maior que zero.");
			return;
		}

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		try {
			LocalDate data = LocalDate.parse(dataString, dtf);
			c.setAtivo(a);
			c.setDataCompra(data);

			if (data.isAfter(LocalDate.now())) {
				Alerta.erro("Validação", "A data da compra não pode ser uma data futura.");
				return;
			}

		} catch (Exception e) {
			Alerta.erro("Validação", "Data inválida. Use o formato dd/mm/aaaa.");
			return;
		}

		try {
			if (c.getId() == 0) {
				dao.salvar(c);
				Alerta.sucesso("Sucesso", "Compra registrada com sucesso!");
			} else {
				dao.atualizar(c, c.getId());
				Alerta.sucesso("Sucesso", "Compra atualizada com sucesso!");
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

	public void excluir(int id) {

		try {
			if (id > 0) {

				boolean confirmou = Alerta.confirmar("Excluir compra",
						"Tem certeza de que deseja excluir essa compra?");

				if (confirmou) {
					dao.excluir(id);
					Alerta.sucesso("Sucesso", "Compra removida com sucesso");
					atualizarLista();
					limparCampos();
				}

			} else {
				Alerta.erro("Erro", "Selecione uma compra válida para exclusão.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			Alerta.erro("Erro de Banco", "Falha ao comunicar com o banco de dados: " + e.getMessage());
		} catch (Exception e) {
			Alerta.erro("Erro Inesperado", "Ocorreu um erro sistêmico: " + e.getMessage());
		}

	}

	public void atualizarLista() {
		lista.clear();
		try {
			lista.addAll(dao.listar());
		} catch (SQLException e) {
			e.printStackTrace();
			Alerta.erro("Erro de Banco", "Não foi possível carregar a lista: " + e.getMessage());
		}
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

		if (c.getAtivo() == null || c.getQuantidade().compareTo(BigDecimal.ZERO) <= 0) {
			Alerta.erro("Validação", "Dados insuficientes para atualizar a compra.");
			return;
		}

		try {
			dao.atualizar(c, id);
			Alerta.sucesso("Sucesso", "Compra atualizada com sucesso!");
			atualizarLista();
			limparCampos();
		} catch (SQLException e) {
			e.printStackTrace();
			Alerta.erro("Erro de Banco", "Falha ao comunicar com o banco de dados: " + e.getMessage());
		} catch (Exception e) {
			Alerta.erro("Erro Inesperado", "Ocorreu um erro sistêmico: " + e.getMessage());
		}

	}

	public void busca() {

		lista.clear();
		try {
			lista.addAll(dao.busca(pesquisa.get()));
		} catch (SQLException e) {
			e.printStackTrace();
			Alerta.erro("Erro de Banco", "Falha ao realizar a busca: " + e.getMessage());
		}

	}
}