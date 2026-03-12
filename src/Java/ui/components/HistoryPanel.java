package ui.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import ui.styles.AppStyles;

public class HistoryPanel extends VBox {

    private final ObservableList<String> historyItems = FXCollections.observableArrayList();

    public HistoryPanel() {
        super(8);

        Label historyTitleLabel = new Label("RECENT CONVERSIONS");
        AppStyles.styleFieldLabel(historyTitleLabel);

        ListView<String> historyList = new ListView<>(historyItems);
        historyList.setPrefHeight(140);
        historyList.setStyle("-fx-background-color: " + AppStyles.BG_INPUT + "; -fx-background-radius: 8px; -fx-border-color: transparent;");
        historyList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color: " + AppStyles.BG_INPUT + ";");
                } else {
                    setText(item);
                    setStyle("-fx-background-color: " + AppStyles.BG_INPUT + "; -fx-text-fill: " + AppStyles.TEXT_PRIMARY + "; -fx-font-size: 13px; -fx-padding: 8 12;");
                }
            }
        });

        Label emptyLabel = new Label("No conversions yet");
        emptyLabel.setStyle("-fx-text-fill: " + AppStyles.TEXT_MUTED + "; -fx-font-size: 12px;");
        historyList.setPlaceholder(emptyLabel);

        setVisible(false);
        getChildren().addAll(historyTitleLabel, historyList);
    }

    public void addEntry(String entry) {
        historyItems.add(0, entry);
        if (historyItems.size() > 5) historyItems.remove(historyItems.size() - 1);
    }
}