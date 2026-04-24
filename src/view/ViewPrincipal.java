package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class ViewPrincipal extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		ViewCompras viewCompras = new ViewCompras();
		ViewAtivos viewAtivos = new ViewAtivos();

		TabPane tabPane = new TabPane();

		Tab tabCompras = new Tab("Compras", viewCompras.render());
		Tab tabAtivos = new Tab("Ativos", viewAtivos.render());

		tabCompras.setClosable(false);
		tabAtivos.setClosable(false);

		tabPane.getTabs().addAll(tabCompras, tabAtivos);

		Scene scn = new Scene(tabPane, 800, 600);
		stage.setTitle("Carteira de Investimentos");
		stage.setScene(scn);
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch();
	}

}
