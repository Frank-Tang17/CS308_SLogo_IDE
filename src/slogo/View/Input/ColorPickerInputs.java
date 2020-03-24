package slogo.View.Input;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * ColorPickerInputs holds the ColorPicker used in the UI to take input from the user, including setting the background color.
 * The code does support functionality to bind the pen color but was commented out since that functionality was switched to TurtleView.
 * The class extends Inputs, but the inheritance hierarchy is limited since the author ran out of time implementing it.
 * The Background ColorPicker is held in an HBox that is added to InputView.
 */
public class ColorPickerInputs extends Inputs {

    private HBox myColorPickers;

    private static final String COLORPICKERS_BUNDLE = "colorPickers";
    private ResourceBundle myColorPickersBundle = ResourceBundle.getBundle(COLORPICKERS_BUNDLE);

    private static final String BACKGROUND_KEY = "background";
    // private static final String PEN_KEY = "pen";
    private static final List<String> ALL_COLOR_PICKERS = new ArrayList<>(Arrays.asList(BACKGROUND_KEY));

    private ColorPicker myBackGroundPicker;
    // private ColorPicker myPenPicker;

    /**
     * Constructor that creates Color Pickers based off of information from the color picker resource bundle.
     * Background Color Picker is bound to the background color and wrapped in an HBox with its corresponding label.
     * @param background is the background property in SlogoView to bind the ColorPicker's value to.
     */
    public ColorPickerInputs(ObjectProperty<Color> background) {
        myColorPickers = new HBox();
        for (String pickerType : ALL_COLOR_PICKERS) makeColorVBox(pickerType);

        myBackGroundPicker.valueProperty().bindBidirectional(background);
        // myPenPicker.valueProperty().bindBidirectional(pen);
        myColorPickers = formatButtons(myColorPickers);
    }

    /**
     * Basic getter method to obtain all ColorPickers, which is currently just the Background Picker, that are wrapped in an HBox.
     * @return myColorPickers HBox to display in InptView.
     */
    public HBox getColorPickersHBox() {return myColorPickers;}

    private void makeColorVBox(String pickerType) {
        VBox addedVBox = new VBox();
        addedVBox.setAlignment(Pos.CENTER);

        String[] ColorPickerInfo = myColorPickersBundle.getString(pickerType).split(PROPERTIES_REGEX_SPLITTER);

        Label addedLabel = new Label(ColorPickerInfo[VBOX_LABEL_INDEX]);
        ColorPicker addedColorPicker = new ColorPicker();

        if (pickerType.equals(BACKGROUND_KEY)) myBackGroundPicker = addedColorPicker;
        // else if (pickerType.equals(PEN_KEY)) myPenPicker = addedColorPicker;

        addedVBox.getChildren().addAll(addedLabel, addedColorPicker);
        myColorPickers.getChildren().add(addedVBox);
    }
}
