package ui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ui.styles.AppStyles;

public class TitleBox extends VBox {

    public TitleBox() {
        super(4);

        Label titleLabel = new Label("Currency Converter");
        titleLabel.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: " + AppStyles.TEXT_PRIMARY + ";");

        Label subtitleLabel = new Label("Real-time exchange rate calculator");
        subtitleLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: " + AppStyles.TEXT_MUTED + ";");

        VBox titleBox = new VBox(4, titleLabel, subtitleLabel);
        titleBox.setAlignment(Pos.CENTER);

        setAlignment(Pos.CENTER);
        getChildren().addAll(titleLabel, subtitleLabel);
    }
}