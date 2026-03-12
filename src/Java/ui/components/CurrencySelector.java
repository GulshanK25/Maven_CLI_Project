package ui.components;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Currency;
import ui.styles.AppStyles;

public class CurrencySelector extends VBox {

    private final ComboBox<Currency> combo = new ComboBox<>();

    public CurrencySelector(String label, Currency defaultValue) {
        super(6);

        Label fieldLabel = new Label(label);
        AppStyles.styleFieldLabel(fieldLabel);

        combo.getItems().addAll(Currency.values());
        combo.setValue(defaultValue);
        AppStyles.styleComboBox(combo);

        getChildren().addAll(fieldLabel, combo);
    }

    public Currency getValue() {
        return combo.getValue();
    }

    public void setValue(Currency currency) {
        combo.setValue(currency);
    }
}