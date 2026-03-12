package ui.components;

import javafx.scene.control.Button;
import ui.styles.AppStyles;

public class AppButton extends Button {

    public enum Type {
        CONVERT, CLEAR, SWAP
    }

    public AppButton(String label, Type type) {
        super(label);

        switch (type) {
            case CONVERT -> AppStyles.styleConvertButton(this);
            case CLEAR   -> AppStyles.styleClearButton(this);
            case SWAP    -> AppStyles.styleSwapButton(this);
        }
    }
}