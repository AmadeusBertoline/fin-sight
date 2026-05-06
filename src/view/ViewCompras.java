package view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import controller.CompraController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.Ativo;
import model.Compra;
import view.components.Alerta;

public class ViewCompras {

	private TextField txtValorUnitario = new TextField();
	private TextField txtTicker = new TextField();
	private TextField txtEmpresa = new TextField();
	private TextField txtTipo = new TextField();
	private TextField txtQuantidade = new TextField();
	private TextField txtValorCompra = new TextField();
	private TextField txtDataCompra = new TextField();
	private TextField txtPesquisa = new TextField();
	private ComboBox<Ativo> cbAtivos = new ComboBox<>();
	private Button btnSalvar = new Button("Salvar");
	private Button btnLimpar = new Button("Limpar");
	private Button btnExcluir = new Button("Excluir");

	private TableView<Compra> table = new TableView<>();
	private CompraController control;

	public ViewCompras(CompraController control) {
		this.control = control;
	}

	public void atualizarComboAtivos() {
		cbAtivos.getItems().setAll(control.listarAtivos());
	}

	public Pane render() {

		int width = 260;

		cbAtivos.setPrefWidth(width);
		txtEmpresa.setPrefWidth(width);
		txtQuantidade.setPrefWidth(width);
		txtValorUnitario.setPrefWidth(width);
		txtTipo.setPrefWidth(width);
		txtValorCompra.setPrefWidth(width);
		txtDataCompra.setPrefWidth(width);
		txtPesquisa.setPrefWidth(width);

		GridPane pane = new GridPane();
		pane.setPadding(new Insets(20));
		pane.setHgap(15);
		pane.setVgap(12);
		pane.setAlignment(Pos.TOP_CENTER);
		pane.setMaxWidth(500);

		cbAtivos.setConverter(new javafx.util.StringConverter<Ativo>() {
			@Override
			public String toString(Ativo ativo) {
				return (ativo == null) ? "" : ativo.getTicker();
			}

			@Override
			public Ativo fromString(String string) {
				return null;
			}
		});

		cbAtivos.getSelectionModel().selectedItemProperty().addListener((observable, antigo, novoAtivo) -> {
			if (novoAtivo != null) {
				txtEmpresa.setText(novoAtivo.getNome());
				txtTipo.setText(novoAtivo.getTipo());
				control.valorUnitarioProperty().set(novoAtivo.getValorCompra());
			} else {
				txtTicker.clear();
				txtTipo.clear();
			}
		});

		txtPesquisa.textProperty().addListener((obs, antigo, novo) -> {
			if (novo != null) {
				control.busca();
			}
		});

		cbAtivos.getItems().addAll(control.listarAtivos());

		txtValorCompra.setEditable(false);
		txtValorCompra.setFocusTraversable(false);
		txtValorCompra.getStyleClass().add("readonly");
		txtValorUnitario.setEditable(false);
		txtTipo.setEditable(false);
		txtEmpresa.setEditable(false);

		pane.add(new Label("Ativo:"), 0, 0);
		pane.add(cbAtivos, 1, 0);

		pane.add(new Label("Empresa:"), 0, 1);
		pane.add(txtEmpresa, 1, 1);

		pane.add(new Label("Quantidade:"), 0, 2);
		pane.add(txtQuantidade, 1, 2);

		pane.add(new Label("Valor unitário:"), 0, 3);
		pane.add(txtValorUnitario, 1, 3);

		pane.add(new Label("Tipo:"), 0, 4);
		pane.add(txtTipo, 1, 4);

		pane.add(new Label("Valor da compra:"), 0, 5);
		pane.add(txtValorCompra, 1, 5);

		pane.add(new Label("Data (dd/mm/aaaa):"), 0, 6);
		pane.add(txtDataCompra, 1, 6);

		pane.add(new Label("Pesquisar:"), 0, 7);
		pane.add(txtPesquisa, 1, 7);

		HBox hBotoes = new HBox(10, btnSalvar, btnLimpar, btnExcluir);
		hBotoes.setAlignment(Pos.CENTER_LEFT);
		hBotoes.setPadding(new Insets(10, 0, 0, 0));

		Bindings.bindBidirectional(txtValorCompra.textProperty(), control.valorCompraProperty(),
				new javafx.util.converter.BigDecimalStringConverter() {
					@Override
					public BigDecimal fromString(String value) {
						if (value == null || value.isBlank())
							return BigDecimal.ZERO;
						value = value.replace(",", ".");
						return new BigDecimal(value);
					}
				});

		Bindings.bindBidirectional(txtQuantidade.textProperty(), control.quantidadeProperty(),
				new javafx.util.converter.BigDecimalStringConverter() {
					@Override
					public BigDecimal fromString(String value) {
						try {
							if (value == null || value.isBlank())
								return BigDecimal.ZERO;
							String limpando = value.replace(",", ".");
							return new BigDecimal(limpando);
						} catch (NumberFormatException e) {
							return null;
						}
					}
				});

		Bindings.bindBidirectional(txtValorUnitario.textProperty(), control.valorUnitarioProperty(),
				new javafx.util.converter.BigDecimalStringConverter() {
					@Override
					public BigDecimal fromString(String value) {
						if (value == null || value.isBlank())
							return BigDecimal.ZERO;
						value = value.replace(",", ".");
						return new BigDecimal(value);
					}
				});

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Bindings.bindBidirectional(txtDataCompra.textProperty(), control.dataCompraProperty(),
				new javafx.util.converter.LocalDateStringConverter(dtf, dtf) {
					@Override
					public LocalDate fromString(String value) {
						try {
							if (value == null || value.trim().isEmpty()) {
								return null;
							}
							return LocalDate.parse(value, dtf);
						} catch (java.time.format.DateTimeParseException e) {
							return null;
						}
					}
				});

		Bindings.bindBidirectional(cbAtivos.valueProperty(), control.ativoProperty());
		Bindings.bindBidirectional(txtEmpresa.textProperty(), control.empresaProperty());
		Bindings.bindBidirectional(txtPesquisa.textProperty(), control.pesquisaProperty());

		btnSalvar.setOnAction(e -> {
			Compra c = control.paraEntidade();

			control.salvar(c, txtDataCompra.getText(), control.ativoProperty().get());

		});

		btnExcluir.setOnAction(e -> {
			Compra selecionada = table.getSelectionModel().getSelectedItem();
			if (selecionada != null) {
				control.excluir(selecionada.getId());
			} else {
				Alerta.erro("Erro", "Selecione uma compra para excluir");
			}
		});

		btnLimpar.setOnAction(e -> {
			control.limparCampos();
			control.atualizarLista();
		});

		table.getColumns().clear();
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

		table.getSelectionModel().selectedItemProperty().addListener((obs, old, selecionado) -> {
			if (selecionado != null) {
				control.paraTela(selecionado);
			}
		});

		TableColumn<Compra, String> colNome = new TableColumn<>("Nome Ativo");
		colNome.setCellValueFactory(data -> {
			Compra compra = data.getValue();
			if (compra.getAtivo() != null) {
				return new javafx.beans.property.SimpleStringProperty(compra.getAtivo().getNome());
			} else {
				return new javafx.beans.property.SimpleStringProperty("");
			}
		});

		TableColumn<Compra, String> colTicker = new TableColumn<>("Ticker");
		colTicker.setCellValueFactory(data -> {
			Compra compra = data.getValue();
			if (compra.getAtivo() != null) {
				return new javafx.beans.property.SimpleStringProperty(compra.getAtivo().getTicker());
			} else {
				return new javafx.beans.property.SimpleStringProperty("");
			}
		});

		TableColumn<Compra, BigDecimal> colValorUnitario = new TableColumn<>("Preço Unit.");
		colValorUnitario.setCellValueFactory(new PropertyValueFactory<>("valorPago"));

		TableColumn<Compra, String> colTipo = new TableColumn<>("Tipo");
		colTipo.setCellValueFactory(cellData -> {
			Compra c = cellData.getValue();
			Ativo a = c.getAtivo();
			if (a != null && a.getValorCompra() != null) {
				return new ReadOnlyObjectWrapper<>(a.getTipo());
			}
			return new ReadOnlyObjectWrapper<>("");
		});

		TableColumn<Compra, BigDecimal> colQuantidade = new TableColumn<>("Qtd");
		colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

		TableColumn<Compra, LocalDate> colData = new TableColumn<>("Data");
		colData.setCellValueFactory(new PropertyValueFactory<>("dataCompra"));

		colData.setCellFactory(col -> new TableCell<>() {
			private DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			protected void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : dtf2.format(item));
			}
		});

		TableColumn<Compra, BigDecimal> colTotal = new TableColumn<>("Total");
		colTotal.setCellValueFactory(new PropertyValueFactory<>("valorCompra"));

		table.getColumns().addAll(colNome, colTicker, colQuantidade, colValorUnitario, colTipo, colData, colTotal);
		table.setItems(control.getLista());

		BorderPane panePrincipal = new BorderPane();
		panePrincipal.setTop(pane);
		panePrincipal.setCenter(table);
		BorderPane.setAlignment(pane, Pos.CENTER);
		panePrincipal.setMargin(table, new Insets(20));
		txtPesquisa.getStyleClass().add("search");

		control.atualizarLista();

		panePrincipal.getStyleClass().add("compras-root");
		panePrincipal.getStylesheets().add(getClass().getResource("/css/compras.css").toExternalForm());

		btnExcluir.getStyleClass().add("button-danger");
		txtPesquisa.getStyleClass().add("search");

		return panePrincipal;
	}
}