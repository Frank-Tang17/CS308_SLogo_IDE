package slogo.View.Input;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Basic shell to support an inheritance model across the Input classes. Common methods and instance variables are held here.
 * Future extensions could build upon this, moving duplicated code into this class.
 * Subclasses are ButtonInputs, ColorPickerInputs, and ComboBoxInputs.
 */
public class Inputs {

    protected static final String PROPERTIES_REGEX_SPLITTER = ", ";
    protected static final int VBOX_LABEL_INDEX = 0;

    protected VBox createInputVBox() {
        VBox addedVBox = new VBox();
        addedVBox.setAlignment(Pos.CENTER);
        return addedVBox;
    }

    protected HBox formatButtons(HBox inputs) {
        for (Node input : inputs.getChildren()) {
            inputs.setHgrow(input, Priority.ALWAYS);
        }
        return inputs;
    }
}
