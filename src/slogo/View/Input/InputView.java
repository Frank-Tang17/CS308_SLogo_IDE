package slogo.View.Input;

import java.io.File;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * Class that holds all input for background color, turtle file, and language. It  wraps the HBoxes created in ButtonInputs, ColorPickerInputs, and ComboBoxInputs into one HBox.
 * This HBox is displayed in SlogoView's BorderPane. The properties from these three subclasses are also made public to binding that occurs in SlogoView.
 * The class inherits Inputs to access the formatButtons() method and to access common instance variables in the package.
 */
public class InputView extends InputType {

    private HBox myInputBar;

    private ObjectProperty<Color> myBackgroundColor;
    private ObjectProperty<String> myLanguage;
    private ObjectProperty<File> myTurtleFile;

    /**
     * Constructor for InputView class that instantiates SimpleObjectProperties for background color, language, and turtle image properties.
     * Instantiates myInputBar HBox to hold the Buttons, ColorPicker, and ComboBox created in ButtonInputs, ColorPickerInputs, and ComboBoxInputs respectively.
     */
    public InputView() {
        instantiateProperties();
        myInputBar = new HBox();
        ColorPickerInputs ColorPickers = new ColorPickerInputs(myBackgroundColor);
        ComboBoxInputs ComboBoxes = new ComboBoxInputs(myLanguage);
        ButtonInputs Buttons = new ButtonInputs(myBackgroundColor, myLanguage, myTurtleFile);

        myInputBar.getChildren().addAll(ColorPickers.getColorPickersHBox(), ComboBoxes.getComboBoxesHBox(), Buttons.getButtonsHBox());
        myInputBar = formatButtons(myInputBar);
    }

    /**
     * Basic getter method to return the HBox containing buttons, color picker, and combo box.
     * @return HBox myInputBar that holds the input Nodes.
     */
    public Node getInputPanel() {return myInputBar;}

    /**
     * Basic getter method to get the background color property which is bound to the ColorPicker's value in ColorPickerInputs.
     * @return myBackgroundColor which holds the color for the background of the program.
     */
    public Property<Color> getBackgroundPropertyColor() {return myBackgroundColor;}

    /**
     * Basic getter method to get the language string property which is bound to the ComboBox's value in ComboBoxInputs.
     * @return myLanguage which holds the language for the program.
     */
    public Property<String> getLanguage() {return myLanguage;}

    /**
     * Basic getter method to get the turtle file property which is bound to the value held in ButtonInputs.
     * @return myTurtleFile which holds the file for the turtle image.
     */
    public Property<File> getTurtleFile() {
        return myTurtleFile;
    }

    private void instantiateProperties() {
        myBackgroundColor = new SimpleObjectProperty<Color>();
        myLanguage = new SimpleObjectProperty<String>();
        myTurtleFile = new SimpleObjectProperty<File>();
    }
}
