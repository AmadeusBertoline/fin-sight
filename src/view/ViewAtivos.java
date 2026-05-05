package view;

import java.math.BigDecimal;
import controller.AtivoController;
import dao.ativo.AtivoDAOImpl;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.Ativo;
import view.components.Alerta;

public class ViewAtivos {

	private TextField txtTicker = new TextField();
	private TextField txtEmpresa = new TextField();
	private TextField txtTipo = new TextField();
	private TextField txtValorCompra = new TextField();
	private TextField txtPesquisa = new TextField();

	private Button btnSalvar = new Button("Salvar");
	private Button btnLimpar = new Button("Limpar");
	private Button btnExcluir = new Button("Excluir");

	AtivoController control = new AtivoController();
	AtivoDAOImpl dao = new AtivoDAOImpl();

	private TableView<Ativo> table = new TableView<>();

	public Pane render() {

		GridPane pane = new GridPane();
		pane.setPadding(new Insets(20));
		pane.setHgap(10);
		pane.setVgap(10);
		pane.setAlignment(Pos.TOP_CENTER);
		pane.setMaxWidth(400);

		pane.add(new Label("Ticker:"), 0, 1);
		pane.add(txtTicker, 1, 1);

		pane.add(new Label("Nome:"), 0, 2);
		pane.add(txtEmpresa, 1, 2);

		pane.add(new Label("Tipo:"), 0, 3);
		pane.add(txtTipo, 1, 3);

		pane.add(new Label("Valor do ativo:"), 0, 4);
		pane.add(txtValorCompra, 1, 4);

		pane.add(new Label("Pesquisar:"), 0, 6);
		pane.add(txtPesquisa, 1, 6);

		HBox botoes = new HBox(10, btnSalvar, btnLimpar, btnExcluir);
		pane.add(botoes, 1, 7);

		txtPesquisa.textProperty().addListener((obs, antigo, novo) -> {

			if (novo != null) {
				control.pesquisar(novo);
			}

		});

		Bindings.bindBidirectional(txtTicker.textProperty(), control.tickerProperty());
		Bindings.bindBidirectional(txtEmpresa.textProperty(), control.nomeProperty());
		Bindings.bindBidirectional(txtTipo.textProperty(), control.tipoProperty());
		Bindings.bindBidirectional(txtValorCompra.textProperty(), control.valorCompraProperty(),
				new javafx.util.converter.BigDecimalStringConverter() {
					@Override
					public BigDecimal fromString(String value) {
						try {
							if (value == null || value.isBlank()) {
								return BigDecimal.ZERO;
							}
							String limpando = value.replace(",", ".");
							return new BigDecimal(limpando);
						} catch (NumberFormatException e) {
							return null;
						}
					}
				});

		Bindings.bindBidirectional(txtPesquisa.textProperty(), control.pesquisaProperty());

		table.getSelectionModel().selectedItemProperty().addListener((obs, old, selecionado) -> {

			if (selecionado != null) {
				control.paraTela(selecionado);
			}

		});

		btnSalvar.setOnAction(e -> {

			Ativo a = control.paraEntidade();

			if (a.getId() == 0) {
				control.salvar(a);
			} else {
				control.atualizar(a, a.getId());
			}

		});

		btnLimpar.setOnAction(e -> {

			control.limparCampos();

		});

		btnExcluir.setOnAction(e -> {

			Ativo selecionado = table.getSelectionModel().getSelectedItem();

			if (selecionado != null) {
				control.excluir(selecionado.getId());
			} else {
				Alerta.erro("Erro", "Selecione um ativo para excluir");
			}

		});

		table.getColumns().clear();
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

		TableColumn<Ativo, String> colTicker = new TableColumn<>("Ticker");
		colTicker.setCellValueFactory(new PropertyValueFactory<>("ticker"));

		TableColumn<Ativo, String> colEmpresa = new TableColumn<>("Empresa");
		colEmpresa.setCellValueFactory(new PropertyValueFactory<>("nome"));

		TableColumn<Ativo, String> colTipo = new TableColumn<>("Tipo");
		colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

		TableColumn<Ativo, BigDecimal> colValor = new TableColumn<>("Valor");
		colValor.setCellValueFactory(new PropertyValueFactory<>("valorCompra"));

		control.atualizarLista();

		table.getColumns().addAll(colTicker, colEmpresa, colTipo, colValor);
		table.setItems(control.getLista());

		BorderPane panePrincipal = new BorderPane();
		panePrincipal.setTop(pane);
		panePrincipal.setCenter(table);
		panePrincipal.setMargin(table, new Insets(20));
		BorderPane.setAlignment(pane, Pos.CENTER);
		control.atualizarLista();
		panePrincipal.getStyleClass().add("ativos-root");

		panePrincipal.getStylesheets().add(
		    getClass().getResource("/css/ativos.css").toExternalForm()
		);
		
		btnExcluir.getStyleClass().add("button-danger");
		txtPesquisa.getStyleClass().add("search");
		pane.getStyleClass().add("form-card");
		
		return panePrincipal;
	}

}
