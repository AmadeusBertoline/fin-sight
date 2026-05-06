package view;

import controller.AtivoController;
import controller.CompraController;
import controller.ControllerDashboard;
import dao.ativo.AtivoDAOImpl;
import dao.compra.CompraDAOImpl;
import dao.dashboard.DashboarDAOImpl;
import dao.dashboard.DashboardDAO;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class ViewPrincipal extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		CompraController compraController = new CompraController(new CompraDAOImpl(), new AtivoDAOImpl());
		AtivoController ativoController = new AtivoController(new AtivoDAOImpl());
		
		ViewCompras viewCompras = new ViewCompras(compraController);
		ViewAtivos viewAtivos = new ViewAtivos(ativoController);
		ViewDashboard viewDashboard = new ViewDashboard();

		var rootDashboard = viewDashboard.render();

		DashboardDAO dashboardDao = new DashboarDAOImpl();
		ControllerDashboard dashboardController = new ControllerDashboard(viewDashboard, dashboardDao);

		TabPane tabPane = new TabPane();

		Tab tabCompras = new Tab("Compras", viewCompras.render());
		Tab tabAtivos = new Tab("Ativos", viewAtivos.render());
		Tab tabDashboard = new Tab("Dashboard", rootDashboard);

		tabCompras.setClosable(false);
		tabAtivos.setClosable(false);
		tabDashboard.setClosable(false);

		tabPane.getTabs().addAll(tabCompras, tabAtivos, tabDashboard);

		tabDashboard.setOnSelectionChanged(event -> {
			if (tabDashboard.isSelected()) {
				dashboardController.carregarDados();
			}
		});

		tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {

			if (newTab != null && newTab.getContent() != null) {

				var content = newTab.getContent();

				content.setOpacity(0);
				content.setTranslateX(20);

				var fade = new javafx.animation.FadeTransition(javafx.util.Duration.millis(250), content);
				fade.setToValue(1);

				var slide = new javafx.animation.TranslateTransition(javafx.util.Duration.millis(250), content);
				slide.setFromX(20);
				slide.setToX(0);

				fade.play();
				slide.play();
			}
		});

		Scene scn = new Scene(tabPane, 1000, 700);

		scn.getStylesheets().add(getClass().getResource("/css/global.css").toExternalForm());

		tabCompras.setOnSelectionChanged(e -> {
			if (tabCompras.isSelected()) {
				viewCompras.atualizarComboAtivos();
			}
		});

		stage.setTitle("FinSight");
		stage.setScene(scn);
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch();
	}
}