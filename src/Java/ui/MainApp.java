package ui;

import model.ConversionResult;
import model.Currency;
import service.CurrencyConverterService;
import service.ExchangeRateRepository;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * JavaFX GUI for the Currency Converter application.
 *
 * To run: mvn javafx:run
 *
 * The GUI delegates all logic to CurrencyConverterService.
 * No business logic lives in this class — making it easy to test the logic separately.
 */
public class MainApp extends Application {

    private final CurrencyConverterService service =
            new CurrencyConverterService(new ExchangeRateRepository());

    private final ObservableList<String> historyItems = FXCollections.observableArrayList();

    private static final String BG_DARK      = "#0f1923";
    private static final String BG_CARD      = "#1a2634";
    private static final String BG_INPUT     = "#243447";
    private static final String ACCENT       = "#00d4aa";
    private static final String ACCENT_DARK  = "#00a884";
    private static final String TEXT_PRIMARY = "#e8edf2";
    private static final String TEXT_MUTED   = "#6b8097";
    private static final String ERROR_COLOR  = "#ff6b6b";

    @Override
    public void start(Stage stage) {

        // Title
        Label titleLabel = new Label("Currency Converter");
        titleLabel.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");
        Label subtitleLabel = new Label("Real-time exchange rate calculator");
        subtitleLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: " + TEXT_MUTED + ";");
        VBox titleBox = new VBox(4, titleLabel, subtitleLabel);
        titleBox.setAlignment(Pos.CENTER);

        // Amount
        Label amountLabel = new Label("AMOUNT");
        styleFieldLabel(amountLabel);
        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount...");
        styleTextField(amountField);
        VBox amountBox = new VBox(6, amountLabel, amountField);

        // From
        Label fromLabel = new Label("FROM");
        styleFieldLabel(fromLabel);
        ComboBox<Currency> fromCombo = new ComboBox<>();
        fromCombo.getItems().addAll(Currency.values());
        fromCombo.setValue(Currency.USD);
        styleComboBox(fromCombo);
        VBox fromBox = new VBox(6, fromLabel, fromCombo);

        // To
        Label toLabel = new Label("TO");
        styleFieldLabel(toLabel);
        ComboBox<Currency> toCombo = new ComboBox<>();
        toCombo.getItems().addAll(Currency.values());
        toCombo.setValue(Currency.EUR);
        styleComboBox(toCombo);
        VBox toBox = new VBox(6, toLabel, toCombo);

        // Swap button
        Button swapBtn = new Button("⇅");
        styleSwapButton(swapBtn);
        swapBtn.setOnAction(e -> {
            Currency temp = fromCombo.getValue();
            fromCombo.setValue(toCombo.getValue());
            toCombo.setValue(temp);
            ScaleTransition st = new ScaleTransition(Duration.millis(150), swapBtn);
            st.setFromX(1); st.setFromY(1);
            st.setToX(1.3); st.setToY(1.3);
            st.setAutoReverse(true); st.setCycleCount(2); st.play();
        });

        // Currency row
        fromBox.setMaxWidth(Double.MAX_VALUE);
        toBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(fromBox, Priority.ALWAYS);
        HBox.setHgrow(toBox, Priority.ALWAYS);
        VBox swapWrapper = new VBox(swapBtn);
        swapWrapper.setAlignment(Pos.BOTTOM_CENTER);
        swapWrapper.setPadding(new Insets(0, 0, 8, 0));
        HBox currencyRow = new HBox(12, fromBox, swapWrapper, toBox);
        currencyRow.setAlignment(Pos.CENTER);

        // Buttons
        Button convertBtn = new Button("Convert");
        convertBtn.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(convertBtn, Priority.ALWAYS);
        styleConvertButton(convertBtn);

        Button clearBtn = new Button("Clear");
        clearBtn.setMinWidth(90);
        styleClearButton(clearBtn);

        HBox buttonRow = new HBox(10, convertBtn, clearBtn);
        buttonRow.setAlignment(Pos.CENTER);

        // Result panel
        VBox resultPanel = new VBox(8);
        resultPanel.setAlignment(Pos.CENTER);
        resultPanel.setPadding(new Insets(16));
        resultPanel.setStyle("-fx-background-color: " + BG_INPUT + "; -fx-background-radius: 10px;");
        resultPanel.setVisible(false);

        Label resultAmountLabel = new Label("");
        resultAmountLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: " + ACCENT + ";");
        Label resultDetailLabel = new Label("");
        resultDetailLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: " + TEXT_MUTED + ";");
        Label rateLabel = new Label("");
        rateLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: " + TEXT_MUTED + "; -fx-padding: 4 10; -fx-background-color: " + BG_CARD + "; -fx-background-radius: 20px;");
        resultPanel.getChildren().addAll(resultAmountLabel, resultDetailLabel, rateLabel);

        // Error label
        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: " + ERROR_COLOR + "; -fx-font-size: 13px;");
        errorLabel.setVisible(false);

        // History panel
        Label historyTitleLabel = new Label("RECENT CONVERSIONS");
        styleFieldLabel(historyTitleLabel);

        ListView<String> historyList = new ListView<>(historyItems);
        historyList.setPrefHeight(140);
        historyList.setStyle("-fx-background-color: " + BG_INPUT + "; -fx-background-radius: 8px; -fx-border-color: transparent;");
        historyList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color: " + BG_INPUT + ";");
                } else {
                    setText(item);
                    setStyle("-fx-background-color: " + BG_INPUT + "; -fx-text-fill: " + TEXT_PRIMARY + "; -fx-font-size: 13px; -fx-padding: 8 12;");
                }
            }
        });
        Label emptyLabel = new Label("No conversions yet");
        emptyLabel.setStyle("-fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 12px;");
        historyList.setPlaceholder(emptyLabel);

        VBox historyPanel = new VBox(8, historyTitleLabel, historyList);
        historyPanel.setVisible(false);

        // Convert action
        convertBtn.setOnAction(e -> {
            errorLabel.setVisible(false);
            String text = amountField.getText().trim();
            if (text.isEmpty()) {
                showError(errorLabel, "Please enter an amount.");
                return;
            }
            try {
                double amount = Double.parseDouble(text);
                Currency from = fromCombo.getValue();
                Currency to   = toCombo.getValue();
                ConversionResult result = service.convert(amount, from, to);

                resultAmountLabel.setText(String.format("%s %s", formatNumber(result.getConvertedAmount()), to.getSymbol()));
                resultDetailLabel.setText(String.format("%s %s  →  %s %s", formatNumber(result.getInputAmount()), from.name(), formatNumber(result.getConvertedAmount()), to.name()));
                rateLabel.setText(service.getFormattedRate(from, to));

                resultPanel.setVisible(true);
                FadeTransition ft = new FadeTransition(Duration.millis(300), resultPanel);
                ft.setFromValue(0); ft.setToValue(1); ft.play();

                // Add to history (newest first, max 5)
                String entry = String.format("%s %s  →  %s %s",
                        formatNumber(amount), from.name(),
                        formatNumber(result.getConvertedAmount()), to.name());
                historyItems.add(0, entry);
                if (historyItems.size() > 5) historyItems.remove(historyItems.size() - 1);

                if (!historyPanel.isVisible()) {
                    historyPanel.setVisible(true);
                    FadeTransition hft = new FadeTransition(Duration.millis(300), historyPanel);
                    hft.setFromValue(0); hft.setToValue(1); hft.play();
                }

            } catch (NumberFormatException ex) {
                showError(errorLabel, "Invalid amount — please enter a number.");
            } catch (IllegalArgumentException ex) {
                showError(errorLabel, ex.getMessage());
            }
        });

        // Clear action
        clearBtn.setOnAction(e -> {
            amountField.clear();
            fromCombo.setValue(Currency.USD);
            toCombo.setValue(Currency.EUR);
            resultPanel.setVisible(false);
            errorLabel.setVisible(false);
            amountField.requestFocus();
        });

        amountField.setOnAction(e -> convertBtn.fire());

        // Main card
        VBox card = new VBox(16);
        card.setPadding(new Insets(32));
        card.setStyle("-fx-background-color: " + BG_CARD + "; -fx-background-radius: 16px;");
        card.getChildren().addAll(titleBox, separator(), amountBox, currencyRow, buttonRow, errorLabel, resultPanel, historyPanel);
        card.setMaxWidth(460);

        ScrollPane scrollPane = new ScrollPane(card);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: " + BG_DARK + "; -fx-background-color: " + BG_DARK + ";");

        StackPane root = new StackPane(scrollPane);
        root.setStyle("-fx-background-color: " + BG_DARK + ";");
        root.setPadding(new Insets(30));

        Scene scene = new Scene(root, 540, 700);
        stage.setTitle("Currency Converter");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    private void styleFieldLabel(Label label) {
        label.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_MUTED + ";");
    }

    private void styleTextField(TextField field) {
        field.setStyle("-fx-background-color: " + BG_INPUT + "; -fx-text-fill: " + TEXT_PRIMARY + "; -fx-prompt-text-fill: " + TEXT_MUTED + "; -fx-font-size: 15px; -fx-padding: 10 14; -fx-background-radius: 8px; -fx-border-color: transparent;");
        field.setMaxWidth(Double.MAX_VALUE);
    }

    private void styleComboBox(ComboBox<Currency> combo) {
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

    private void styleConvertButton(Button btn) {
        String base  = "-fx-background-color: " + ACCENT + "; -fx-text-fill: " + BG_DARK + "; -fx-font-size: 15px; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 8px; -fx-padding: 13 0;";
        String hover = "-fx-background-color: " + ACCENT_DARK + "; -fx-text-fill: " + BG_DARK + "; -fx-font-size: 15px; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 8px; -fx-padding: 13 0;";
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e  -> btn.setStyle(base));
    }

    private void styleClearButton(Button btn) {
        String base  = "-fx-background-color: transparent; -fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 14px; -fx-cursor: hand; -fx-background-radius: 8px; -fx-padding: 13 0; -fx-border-color: " + BG_INPUT + "; -fx-border-radius: 8px; -fx-border-width: 1.5px;";
        String hover = "-fx-background-color: " + BG_INPUT + "; -fx-text-fill: " + TEXT_PRIMARY + "; -fx-font-size: 14px; -fx-cursor: hand; -fx-background-radius: 8px; -fx-padding: 13 0; -fx-border-color: " + TEXT_MUTED + "; -fx-border-radius: 8px; -fx-border-width: 1.5px;";
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e  -> btn.setStyle(base));
    }

    private void styleSwapButton(Button btn) {
        String base  = "-fx-background-color: " + BG_INPUT + "; -fx-text-fill: " + ACCENT + "; -fx-font-size: 18px; -fx-cursor: hand; -fx-background-radius: 50%; -fx-min-width: 40px; -fx-min-height: 40px; -fx-border-color: " + ACCENT + "; -fx-border-radius: 50%; -fx-border-width: 1.5px;";
        String hover = "-fx-background-color: " + ACCENT + "; -fx-text-fill: " + BG_DARK + "; -fx-font-size: 18px; -fx-cursor: hand; -fx-background-radius: 50%; -fx-min-width: 40px; -fx-min-height: 40px; -fx-border-radius: 50%;";
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e  -> btn.setStyle(base));
    }

    private Region separator() {
        Region sep = new Region();
        sep.setPrefHeight(1);
        sep.setStyle("-fx-background-color: " + BG_INPUT + ";");
        return sep;
    }

    private void showError(Label label, String message) {
        label.setText(message);
        label.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(200), label);
        ft.setFromValue(0); ft.setToValue(1); ft.play();
    }

    private String formatNumber(double value) {
        if (value == Math.floor(value) && value < 1_000_000) return String.format("%.0f", value);
        return String.format("%,.2f", value);
    }

    public static void main(String[] args) { launch(args); }
}