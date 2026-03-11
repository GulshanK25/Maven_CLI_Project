package ui.components;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.Currency;
import model.CurrencyPair;
import service.FavouriteConversion;
import ui.styles.AppStyles;

import java.util.function.BiConsumer;

public class FavouritesPanel extends VBox {

    private final FavouriteConversion favourites = new FavouriteConversion();
    private final ListView<CurrencyPair> favouritesList = new ListView<>();
    private final BiConsumer<Currency, Currency> onFavouriteSelected;

    public FavouritesPanel(BiConsumer<Currency, Currency> onFavouriteSelected) {
        super(8);
        this.onFavouriteSelected = onFavouriteSelected;

        Label titleLabel = new Label("FAVOURITE CONVERSIONS");
        AppStyles.styleFieldLabel(titleLabel);

        favouritesList.setPrefHeight(140);
        favouritesList.setStyle("-fx-background-color: " + AppStyles.BG_INPUT + "; -fx-background-radius: 8px; -fx-border-color: transparent;");
        favouritesList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(CurrencyPair item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color: " + AppStyles.BG_INPUT + ";");
                } else {
                    setText(item.toString());
                    setStyle("-fx-background-color: " + AppStyles.BG_INPUT + "; -fx-text-fill: " + AppStyles.TEXT_PRIMARY + "; -fx-font-size: 13px; -fx-padding: 8 12;");
                }
            }
        });

        Label emptyLabel = new Label("No favourites added yet");
        emptyLabel.setStyle("-fx-text-fill: " + AppStyles.TEXT_MUTED + "; -fx-font-size: 12px;");
        favouritesList.setPlaceholder(emptyLabel);

        // Select favourite to auto-fill form
        favouritesList.setOnMouseClicked(e -> {
            CurrencyPair selected = favouritesList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                onFavouriteSelected.accept(selected.getFrom(), selected.getTo());
            }
        });

        // Remove button
        AppButton removeBtn = new AppButton("Remove", AppButton.Type.CLEAR);
        removeBtn.setMaxWidth(Double.MAX_VALUE);
        removeBtn.setOnAction(e -> {
            CurrencyPair selected = favouritesList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                favourites.removeFavourite(selected.getFrom(), selected.getTo());
                refreshList();
                if (favourites.getFavourites().isEmpty()) setVisible(false);
            }
        });

        setVisible(false);
        getChildren().addAll(titleLabel, favouritesList, removeBtn);
    }

    public void addFavourite(Currency from, Currency to) {
        favourites.addFavourite(from, to);
        refreshList();
        if (!isVisible()) {
            setVisible(true);
            FadeTransition ft = new FadeTransition(Duration.millis(300), this);
            ft.setFromValue(0); ft.setToValue(1); ft.play();
        }
    }

    private void refreshList() {
        favouritesList.getItems().setAll(favourites.getFavourites());
    }
}