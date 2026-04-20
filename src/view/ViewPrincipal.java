package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewPrincipal extends Application{
	
	@Override
	public void start(Stage stage) throws Exception {
		
		ViewAtivos tela = new ViewAtivos();
		Scene scene = new Scene(tela.render(), 600, 500);
		stage.setScene(scene);
		stage.setTitle("Carteira de Investimentos");
		stage.show();
		
	}
	
	public static void main(String[] args) {
		Application.launch();
	}

}
