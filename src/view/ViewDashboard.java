package view;

import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import view.components.Card;

public class ViewDashboard {

	private Card totalCard;
	private Card valorizacaoCard;
	private Card desvalorizacaoCard;
	private PieChart pieChart;
	private ListView<String> topAtivos;

	public Pane render() {
		VBox root = new VBox(20);
		root.setPadding(new Insets(20));
		root.getStyleClass().add("dashboard-root");
		root.setFillWidth(true);

		Label titulo = new Label("Dashboard");
		titulo.getStyleClass().add("label-title");

		HBox cards = new HBox(20);

		this.totalCard = new Card("Total Investido", "R$ 0,00");
		this.valorizacaoCard = new Card("Valorização", "0,00%");
		this.desvalorizacaoCard = new Card("Desvalorização", "0,00%");

		totalCard.setMaxWidth(Double.MAX_VALUE);
		valorizacaoCard.setMaxWidth(Double.MAX_VALUE);
		desvalorizacaoCard.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(totalCard, Priority.ALWAYS);
		HBox.setHgrow(valorizacaoCard, Priority.ALWAYS);
		HBox.setHgrow(desvalorizacaoCard, Priority.ALWAYS);

		cards.getChildren().addAll(totalCard, valorizacaoCard, desvalorizacaoCard);

		this.pieChart = new PieChart();
		this.pieChart.setTitle("Distribuição por Tipo");
		this.pieChart.setPrefWidth(400);

		this.topAtivos = new ListView<>();
		HBox.setHgrow(this.topAtivos, Priority.ALWAYS);

		HBox content = new HBox(20, this.pieChart, this.topAtivos);
		VBox.setVgrow(content, Priority.ALWAYS);

		root.getChildren().addAll(titulo, cards, content);

		try {
			root.getStylesheets().add(getClass().getResource("/resources/css/dashboard.css").toExternalForm());
		} catch (Exception e) {
			System.err.println("Erro ao carregar CSS da Dashboard: " + e.getMessage());
		}

		return root;
	}

	public Card getTotalCard() {
		return totalCard;
	}

	public Card getValorizacaoCard() {
		return valorizacaoCard;
	}

	public Card getDesvalorizacaoCard() {
		return desvalorizacaoCard;
	}

	public PieChart getPieChart() {
		return pieChart;
	}

	public ListView<String> getTopAtivosList() {
		return topAtivos;
	}
}