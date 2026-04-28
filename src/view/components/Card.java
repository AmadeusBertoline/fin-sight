package view.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Card extends VBox {
    private Label title;
    private Label value;

    private final StringProperty valueProperty = new SimpleStringProperty("");

    public Card(String titulo, String valorInicial) {
        this.title = new Label(titulo);
        this.title.getStyleClass().add("label-subtitle");

        this.value = new Label(valorInicial);
        this.value.getStyleClass().add("value-neutral");

        this.value.textProperty().bind(valueProperty);

        this.setSpacing(10);
        this.getChildren().addAll(title, value);
        this.getStyleClass().add("card");
        
        this.valueProperty.set(valorInicial);
    }

    public StringProperty valueProperty() {
        return valueProperty;
    }
}