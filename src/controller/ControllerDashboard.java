package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Dashboard;
import view.ViewDashboard;

public class ControllerDashboard {
    
    private final ViewDashboard view;

    private final StringProperty totalProperty = new SimpleStringProperty();
    private final StringProperty valorizacaoProperty = new SimpleStringProperty();
    private final StringProperty desvalorizacaoProperty = new SimpleStringProperty();

    public ControllerDashboard(ViewDashboard view) {
        this.view = view;
        
        this.view.getTotalCard().valueProperty().bind(this.totalProperty);
        this.view.getValorizacaoCard().valueProperty().bind(this.valorizacaoProperty);
        this.view.getDesvalorizacaoCard().valueProperty().bind(this.desvalorizacaoProperty);
    }

    public void updateDashboard(Dashboard dados) {
        this.totalProperty.set("R$ " + dados.getTotalInvestido());
        this.valorizacaoProperty.set(dados.getValorizacao() + "%");
        this.desvalorizacaoProperty.set(dados.getDesvalorizacao() + "%");
    }
}