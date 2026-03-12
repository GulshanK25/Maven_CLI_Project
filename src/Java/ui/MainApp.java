package ui;

import model.ConversionResult;
import model.Currency;
import service.CurrencyConverterService;
import service.ExchangeRateRepository;
import ui.components.AppButton;
import ui.components.CurrencySelector;
import ui.components.FavouritesPanel;
import ui.components.HistoryPanel;
import ui.components.ResultPanel;
import ui.components.TitleBox;
import ui.styles.AppStyles;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainApp extends Application {

    private final CurrencyConverterService service =
            new CurrencyConverterService(new ExchangeRateRepository());

    @Override
    public void start(Stage stage) {

        // Components
        TitleBox titleBox             = new TitleBox();
        ResultPanel resultPanel       = new ResultPanel();
        HistoryPanel historyPanel     = new HistoryPanel();
        CurrencySelector fromSelector = new CurrencySelector("FROM", Currency.USD);
        CurrencySelector toSelector   = new CurrencySelector("TO",   Currency.EUR);

        // Favourites panel
        FavouritesPanel favouritesPanel = new FavouritesPanel((from, to) -> {
            fromSelector.setValue(from);
            toSelector.setValue(to);
        });

        // Amount
        Label amountLabel = new Label("AMOUNT");
        AppStyles.styleFieldLabel(amountLabel);
        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount...");
        AppStyles.styleTextField(amountField);
        VBox amountBox = new VBox(6, amountLabel, amountField);

        // Swap button
        AppButton swapBtn = new AppButton("⇅", AppButton.Type.SWAP);
        swapBtn.setOnAction(e -> {
            Currency temp = fromSelector.getValue();
            fromSelector.setValue(toSelector.getValue());
            toSelector.setValue(temp);
            ScaleTransition st = new ScaleTransition(Duration.millis(150), swapBtn);
            st.setFromX(1); st.setFromY(1);
            st.setToX(1.3); st.setToY(1.3);
            st.setAutoReverse(true); st.setCycleCount(2); st.play();
        });

        // Currency row
        fromSelector.setMaxWidth(Double.MAX_VALUE);
        toSelector.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(fromSelector, Priority.ALWAYS);
        HBox.setHgrow(toSelector, Priority.ALWAYS);
        VBox swapWrapper = new VBox(swapBtn);
        swapWrapper.setAlignment(Pos.BOTTOM_CENTER);
        swapWrapper.setPadding(new Insets(0, 0, 8, 0));
        HBox currencyRow = new HBox(12, fromSelector, swapWrapper, toSelector);
        currencyRow.setAlignment(Pos.CENTER);

        // Buttons
        AppButton convertBtn = new AppButton("Convert", AppButton.Type.CONVERT);
        convertBtn.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(convertBtn, Priority.ALWAYS);

        AppButton clearBtn = new AppButton("Clear", AppButton.Type.CLEAR);
        clearBtn.setMinWidth(60);

        AppButton addFavouriteBtn = new AppButton("★", AppButton.Type.CLEAR);
        addFavouriteBtn.setMinWidth(50);
        addFavouriteBtn.setOnAction(e ->
                favouritesPanel.addFavourite(fromSelector.getValue(), toSelector.getValue())
        );

        HBox buttonRow = new HBox(10, convertBtn, clearBtn, addFavouriteBtn);
        buttonRow.setAlignment(Pos.CENTER);

        // Error label
        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: " + AppStyles.ERROR_COLOR + "; -fx-font-size: 13px;");
        errorLabel.setVisible(false);

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
                Currency from = fromSelector.getValue();
                Currency to   = toSelector.getValue();
                ConversionResult result = service.convert(amount, from, to);

                resultPanel.showResult(
                        String.format("%s %s", formatNumber(result.getConvertedAmount()), to.getSymbol()),
                        String.format("%s %s  →  %s %s", formatNumber(result.getInputAmount()), from.name(), formatNumber(result.getConvertedAmount()), to.name()),
                        service.getFormattedRate(from, to)
                );

                String entry = String.format("%s %s  →  %s %s",
                        formatNumber(amount), from.name(),
                        formatNumber(result.getConvertedAmount()), to.name());
                historyPanel.addEntry(entry);

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
            fromSelector.setValue(Currency.USD);
            toSelector.setValue(Currency.EUR);
            resultPanel.setVisible(false);
            errorLabel.setVisible(false);
            amountField.requestFocus();
        });

        amountField.setOnAction(e -> convertBtn.fire());

        // Main card
        VBox card = new VBox(16);
        card.setPadding(new Insets(32));
        card.setStyle("-fx-background-color: " + AppStyles.BG_CARD + "; -fx-background-radius: 16px;");
        card.getChildren().addAll(titleBox, AppStyles.separator(), amountBox, currencyRow, buttonRow, errorLabel, resultPanel, historyPanel, favouritesPanel);
        card.setMaxWidth(460);

        ScrollPane scrollPane = new ScrollPane(card);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: " + AppStyles.BG_DARK + "; -fx-background-color: " + AppStyles.BG_DARK + ";");

        StackPane root = new StackPane(scrollPane);
        root.setStyle("-fx-background-color: " + AppStyles.BG_DARK + ";");
        root.setPadding(new Insets(30));

        Scene scene = new Scene(root, 540, 700);
        stage.setTitle("Currency Converter");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
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