package controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.chart.PieChart;
import model.Dashboard;
import model.TipoInvestimento;

import java.sql.SQLException;
import java.util.List;

import dao.dashboard.DashboardDAO;
import view.ViewDashboard;
import view.components.Alerta;

public class ControllerDashboard {

	private final ViewDashboard view;
	private final DashboardDAO dao;

	private final StringProperty totalProperty = new SimpleStringProperty();
	private final StringProperty valorizacaoProperty = new SimpleStringProperty();
	private final StringProperty desvalorizacaoProperty = new SimpleStringProperty();

	public ControllerDashboard(ViewDashboard view, DashboardDAO dao) {
		this.view = view;
		this.dao = dao;

		this.view.getTotalCard().valueProperty().bind(this.totalProperty);
		this.view.getValorizacaoCard().valueProperty().bind(this.valorizacaoProperty);
		this.view.getDesvalorizacaoCard().valueProperty().bind(this.desvalorizacaoProperty);
	}

	public void carregarDados() {
		new Thread(() -> {
			try {
				Dashboard resumo = dao.getResumoCarteira();
				List<TipoInvestimento> dadosDoBanco = dao.getDistribuicaoPorTipo();
				List<String> listaAtivosFormatada = dao.getTopAtivos();

				if (resumo == null) {
					Platform.runLater(() -> Alerta.erro("Dados Inexistentes",
							"Não há registros financeiros para exibir no resumo."));
					return;
				}

				List<PieChart.Data> dadosGrafico = dadosDoBanco.stream()
						.map(t -> new PieChart.Data(t.getNome(), t.getValor().doubleValue())).toList();

				Platform.runLater(() -> {
					updateDashboard(resumo);
					view.getPieChart().getData().setAll(dadosGrafico);
					view.getTopAtivosList().getItems().setAll(listaAtivosFormatada);
				});

			} catch (SQLException e) {
				e.printStackTrace();

				Platform.runLater(() -> {
					Alerta.erro("Erro de Banco", "Falha ao comunicar com o banco de dados: " + e.getMessage());
				});
			} catch (Exception e) {
				e.printStackTrace();

				Platform.runLater(() -> {
					Alerta.erro("Erro de Carregamento", "Não foi possível buscar os dados do banco: " + e.getMessage());
				});
			}
		}).start();
	}

	public void updateDashboard(Dashboard dados) {
		if (dados.getTotalInvestido() != null) {
			this.totalProperty.set("R$ " + String.format("%.2f", dados.getTotalInvestido()));
		} else {
			this.totalProperty.set("R$ 0,00");
		}

		this.valorizacaoProperty.set(String.format("%.2f", dados.getValorizacao()) + "%");
		this.desvalorizacaoProperty.set(String.format("%.2f", dados.getDesvalorizacao()) + "%");
	}
}