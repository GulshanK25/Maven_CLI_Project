package ui.styles;

import javafx.animation.FadeTransition;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import model.Currency;

public class AppStyles {

    public static final String BG_DARK      = "#0f1923";
    public static final String BG_CARD      = "#1a2634";
    public static final String BG_INPUT     = "#243447";
    public static final String ACCENT       = "#00d4aa";
    public static final String ACCENT_DARK  = "#00a884";
    public static final String TEXT_PRIMARY = "#e8edf2";
    public static final String TEXT_MUTED   = "#6b8097";
    public static final String ERROR_COLOR  = "#ff6b6b";

    public static void styleFieldLabel(Label label) {
        label.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_MUTED + ";");
    }

    public static void styleTextField(TextField field) {
        field.setStyle("-fx-background-color: " + BG_INPUT + "; -fx-text-fill: " + TEXT_PRIMARY + "; -fx-prompt-text-fill: " + TEXT_MUTED + "; -fx-font-size: 15px; -fx-padding: 10 14; -fx-background-radius: 8px; -fx-border-color: transparent;");
        field.setMaxWidth(Double.MAX_VALUE);
    }

    public static void styleComboBox(ComboBox<Currency> combo) {
        combo.setMaxWidth(Double.MAX_VALUE);
        combo.setStyle("-fx-background-color: " + BG_INPUT + "; -fx-font-size: 13px; -fx-background-radius: 8px; -fx-border-color: transparent;");
        combo.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Currency item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.toString());
                    setStyle("-fx-text-fill: white; -fx-font-size: 13px; -fx-background-color: " + BG_INPUT + ";");
                }
            }
        });
    }

    public static void styleConvertButton(Button btn) {
        String base  = "-fx-background-color: " + ACCENT + "; -fx-text-fill: " + BG_DARK + "; -fx-font-size: 15px; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 8px; -fx-padding: 13 0;";
        String hover = "-fx-background-color: " + ACCENT_DARK + "; -fx-text-fill: " + BG_DARK + "; -fx-font-size: 15px; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 8px; -fx-padding: 13 0;";
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e  -> btn.setStyle(base));
    }

    public static void styleClearButton(Button btn) {
        String base  = "-fx-background-color: transparent; -fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 14px; -fx-cursor: hand; -fx-background-radius: 8px; -fx-padding: 13 0; -fx-border-color: " + BG_INPUT + "; -fx-border-radius: 8px; -fx-border-width: 1.5px;";
        String hover = "-fx-background-color: " + BG_INPUT + "; -fx-text-fill: " + TEXT_PRIMARY + "; -fx-font-size: 14px; -fx-cursor: hand; -fx-background-radius: 8px; -fx-padding: 13 0; -fx-border-color: " + TEXT_MUTED + "; -fx-border-radius: 8px; -fx-border-width: 1.5px;";
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e  -> btn.setStyle(base));
    }

    public static void styleSwapButton(Button btn) {
        String base  = "-fx-background-color: " + BG_INPUT + "; -fx-text-fill: " + ACCENT + "; -fx-font-size: 18px; -fx-cursor: hand; -fx-background-radius: 50%; -fx-min-width: 40px; -fx-min-height: 40px; -fx-border-color: " + ACCENT + "; -fx-border-radius: 50%; -fx-border-width: 1.5px;";
        String hover = "-fx-background-color: " + ACCENT + "; -fx-text-fill: " + BG_DARK + "; -fx-font-size: 18px; -fx-cursor: hand; -fx-background-radius: 50%; -fx-min-width: 40px; -fx-min-height: 40px; -fx-border-radius: 50%;";
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e  -> btn.setStyle(base));
    }

    public static Region separator() {
        Region sep = new Region();
        sep.setPrefHeight(1);
        sep.setStyle("-fx-background-color: " + BG_INPUT + ";");
        return sep;
    }

    public static void showError(Label label, String message) {
        label.setText(message);
        label.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(200), label);
        ft.setFromValue(0); ft.setToValue(1); ft.play();
    }

    public static String formatNumber(double value) {
        if (value == Math.floor(value) && value < 1_000_000) return String.format("%.0f", value);
        return String.format("%,.2f", value);
    }
}