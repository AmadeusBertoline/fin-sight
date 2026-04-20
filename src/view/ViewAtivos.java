package view;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import controller.AtivoController;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Ativo;

public class ViewAtivos {

	private TextField txtId = new TextField("Id");
	private TextField txtTicker = new TextField("Ticker");
	private TextField txtNome = new TextField("Nome");
	private TextField txtTipo = new TextField("Tipo");
	private TextField txtQuantidade = new TextField("Quantidade");
	private TextField txtValorCompra = new TextField("Valor da Compra");
	private TextField txtDataCompra = new TextField("Data");
	private TextField txtTotalInvestido = new TextField("Total Investido");
	private TextField txtQuantidadeAtivos = new TextField("Quantidade de Ativos");
	private TextField txtPrecoMedioAtivo = new TextField("Preço médio do ativo");
	private Button btnSalvar = new Button("Salvar");
	private Button btnLimpar = new Button("Limpar");
	private Button btnPesquisar = new Button("Pesquisar");
	private Button btnExcluir = new Button("Excluir");

	private TableView<Ativo> table = new TableView<>();

	private AtivoController control = new AtivoController();

	@SuppressWarnings({ "unchecked", "deprecation" })
	public Pane render() {

		GridPane pane = new GridPane();
		pane.setPadding(new Insets(20));
		pane.setHgap(10);
		pane.setVgap(10);
		pane.setAlignment(Pos.CENTER);

		txtId.setEditable(false);
		txtId.setDisable(true);
		txtId.setVisible(false);
		txtId.setManaged(false);

//		pane.add(new Label("ID:"), 0, 0);
		pane.add(txtId, 1, 0);

		pane.add(new Label("Ticker"), 0, 1);
		pane.add(txtTicker, 1, 1);

		pane.add(new Label("Nome"), 0, 2);
		pane.add(txtNome, 1, 2);

		pane.add(new Label("Tipo"), 0, 3);
		pane.add(txtTipo, 1, 3);

		pane.add(new Label("Quantidade"), 0, 4);
		pane.add(txtQuantidade, 1, 4);

		pane.add(new Label("Valor da compra"), 0, 5);
		pane.add(txtValorCompra, 1, 5);

		// botões
		HBox hBotoes = new HBox(10);

		hBotoes.getChildren().addAll(btnSalvar, btnLimpar, btnPesquisar, btnExcluir);
		pane.add(hBotoes, 1, 6);

		Bindings.bindBidirectional(txtId.textProperty(), control.idProperty(),
				new javafx.util.converter.NumberStringConverter());
		Bindings.bindBidirectional(txtTicker.textProperty(), control.tickerProperty());
		Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
		Bindings.bindBidirectional(txtTipo.textProperty(), control.tipoProperty());
		Bindings.bindBidirectional(txtQuantidade.textProperty(), control.quantidadeProperty());
		Bindings.bindBidirectional(txtValorCompra.textProperty(), control.valorCompraProperty(),
				new javafx.util.converter.BigDecimalStringConverter());
		
		//bind do total investido
		Bindings.bindBidirectional(txtTotalInvestido.textProperty(), control.totalInvestidoProperty(), new javafx.util.converter.BigDecimalStringConverter());
		
		// botão de salvar
		btnSalvar.setOnAction(e -> {

			Ativo a = control.paraEntidade();
			String dataString = txtDataCompra.getText();

			control.salvar(a, dataString);

			System.out.println("Ativo cadastrado com sucesso!");

			txtId.clear();
			txtTicker.clear();
			txtNome.clear();
			txtTipo.clear();
			txtQuantidade.clear();
			txtValorCompra.clear();
			txtDataCompra.clear();

		});

		// botao de excluir
		btnExcluir.setOnAction(e -> {

			long id = control.idProperty().get();

			if (id > 0) {
				control.excluir(id);
				txtId.clear();
				txtTicker.clear();
				txtNome.clear();
				txtTipo.clear();
				txtQuantidade.clear();
				txtValorCompra.clear();
				txtDataCompra.clear();

				System.out.println("Ativo excluído com sucesso");
			} else {
				System.out.println("Selecione um ativo para excluir");
			}

		});

		// botão de pesquisar
		btnPesquisar.setOnAction(e -> {

			String nome = txtNome.getText();
			control.pesquisar(nome);

		});

		// botão de limpar os campos
		btnLimpar.setOnAction(e -> {

			txtId.clear();
			txtNome.clear();
			txtTicker.clear();
			txtTipo.clear();
			txtQuantidade.clear();
			txtValorCompra.clear();
			txtDataCompra.clear();

			table.getSelectionModel().clearSelection();

			control.atualizarLista();

		});

		BorderPane panePrincipal = new BorderPane();

		TableColumn<Ativo, String> colNome = new TableColumn<>("Nome");
		colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

		TableColumn<Ativo, String> colTicker = new TableColumn<>("Ticker");
		colTicker.setCellValueFactory(new PropertyValueFactory<>("ticker"));

		TableColumn<Ativo, String> colTipo = new TableColumn<>("Tipo");
		colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

		TableColumn<Ativo, Double> colQuantidade = new TableColumn<>("Quantidade");
		colQuantidade.setCellValueFactory(new PropertyValueFactory<>("Quantidade"));

		TableColumn<Ativo, BigDecimal> colValor = new TableColumn<>("Valor");
		colValor.setCellValueFactory(new PropertyValueFactory<>("valorCompra"));

		TableColumn<Ativo, LocalDate> colData = new TableColumn<>("Data");
		colData.setCellValueFactory(new PropertyValueFactory<>("dataCompra"));
		
		TableColumn<Ativo, BigDecimal> colTotal = new TableColumn<>("Total da Compra");
		colTotal.setCellValueFactory(new PropertyValueFactory<>("totalCompra"));
		
		colTotal.setCellFactory(column -> new TableCell<Ativo, BigDecimal>() {
		    // Configura o formato para o padrão brasileiro (R$)
		    private final NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

		    @Override
		    protected void updateItem(BigDecimal item, boolean empty) {
		        super.updateItem(item, empty);
		        
		        if (empty || item == null) {
		            setText(null);
		        } else {
		            // Aplica a formatação e define o texto da célula
		            setText(format.format(item));
		        }
		    }
		});

		colData.setCellFactory(col -> new TableCell<Ativo, LocalDate>() {

			private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			protected void updateItem(LocalDate item, boolean empty) {

				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(dtf.format(item));
				}

			}

		});

		table.getColumns().clear();
		table.getColumns().addAll(colNome, colTicker, colTipo, colQuantidade, colValor, colData, colTotal);

		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		table.setItems(control.getLista());
		
		BigDecimal totalGeral = control.totalGeral();
		
		NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		String valorFormatado = format.format(totalGeral);
		
		// dashboard
		GridPane gridDash = new GridPane();
		gridDash.setPadding(new Insets(20));
		VBox dash = new VBox(10);
		dash.setPadding(new Insets(20));
		dash.setStyle("-fx-background-color: #e8e8e8;");
		dash.getChildren().add(gridDash);
		gridDash.add(new Label("RESUMO DA CARTEIRA"), 1, 1);
		gridDash.add(new Label("Total Investido: "), 1, 2);
		gridDash.add(new Label(valorFormatado), 2, 2);
		gridDash.add(new Label("Quantidade Ativos: "), 1, 3);
		gridDash.add(new Label(""), 2, 3);
		gridDash.add(new Label("Preço médio por ativo: "), 1, 4);
		gridDash.add(new Label(""), 2, 4);
		
		txtTotalInvestido.setEditable(false);
		

		// navegação
		TabPane tabPane = new TabPane();

		Tab tabAtivos = new Tab("Lista de Ativos");
		tabAtivos.setContent(table);
		tabAtivos.setClosable(false);

		Tab tabDash = new Tab("Dashboard");
		tabDash.setContent(dash);
		tabDash.setClosable(false);

		tabPane.getTabs().addAll(tabAtivos, tabDash);

		// panePrincipal.setRight(dash);

		panePrincipal.setTop(pane);
		panePrincipal.setBottom(table);
		panePrincipal.setCenter(tabPane);

		control.atualizarLista();

		return panePrincipal;

	}

}