package slogo.View.Input;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Abstract superclass for inheritance model within the Input package. Subclasses are ButtonInputs, ColorPickerInputs, and ComboBoxInputs.
 * Abstract class holds an HBox to store input nodes (buttons, colorpickers, comboboxes) and common instance variables to access labels via ResourceBundles.
 */
public abstract class InputType {

    protected static final String PROPERTIES_REGEX_SPLITTER = ", ";
    protected static final int LABEL_INDEX = 0;

    protected HBox myInputs;

    /**
     * Very basic constructor that only initializes the HBox.
     */
    public InputType() {
        myInputs = new HBox();
    }

    /**
     * Basic getter method that returns an HBox holding all buttons and their labels corresponding to their functionality when called in subclasses.
     * @return myInputs HBox which is always displayed in InputView.
     */
    public Node getButtonsHBox() {return myInputs;}

    protected Property createBindedProperty(Property bindedProp, Object defaultValue) {
        Property returnedProp = new SimpleObjectProperty();
        returnedProp.bindBidirectional(bindedProp);
        returnedProp.setValue(defaultValue);
        return returnedProp;
    }

    protected void createAndAddInputBox(String labelText, Node inputNode) {
        VBox inputBox = new VBox();
        inputBox.setAlignment(Pos.CENTER);

        Label addedLabel = new Label(labelText);
        inputBox.getChildren().addAll(addedLabel, inputNode);
        myInputs.getChildren().add(inputBox);
    }

    protected HBox formatButtons(HBox inputNodes) {
        for (Node input : inputNodes.getChildren()) {
            inputNodes.setHgrow(input, Priority.ALWAYS);
        }
        return inputNodes;
    }
}
