package ui.components;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import ui.styles.AppStyles;

public class ResultPanel extends VBox {

    private final Label resultAmountLabel = new Label("");
    private final Label resultDetailLabel = new Label("");
    private final Label rateLabel         = new Label("");

    public ResultPanel() {
        super(8);

        resultAmountLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: " + AppStyles.ACCENT + ";");
        resultDetailLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: " + AppStyles.TEXT_MUTED + ";");
        rateLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: " + AppStyles.TEXT_MUTED + "; -fx-padding: 4 10; -fx-background-color: " + AppStyles.BG_CARD + "; -fx-background-radius: 20px;");

        setAlignment(Pos.CENTER);
        setPadding(new Insets(16));
        setStyle("-fx-background-color: " + AppStyles.BG_INPUT + "; -fx-background-radius: 10px;");
        setVisible(false);

        getChildren().addAll(resultAmountLabel, resultDetailLabel, rateLabel);
    }

    public void showResult(String amount, String detail, String rate) {
        resultAmountLabel.setText(amount);
        resultDetailLabel.setText(detail);
        rateLabel.setText(rate);

        setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(300), this);
        ft.setFromValue(0); ft.setToValue(1); ft.play();
    }
}