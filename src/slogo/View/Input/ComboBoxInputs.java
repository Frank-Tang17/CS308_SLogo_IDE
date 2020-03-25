package slogo.View.Input;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * ComboBoxInputs holds the ComboBox used in the UI to take input from the user, including setting the language.
 * The class extends Inputs, but the inheritance hierarchy is limited since the author ran out of time implementing it.
 * The Language ComboBox is held in an HBox that is added to InputView.
 */
public class ComboBoxInputs extends Inputs {

    private static final String COMBOBOXES = "comboBoxes";
    private ResourceBundle myComboBoxesBundle  = ResourceBundle.getBundle(COMBOBOXES);

    private ObservableList allLanguages = initLanguageOptions();
    private static final String PATH_TO_RESOURCE_LANGUAGES = "././././resources/languages";
    private static final int LENGTH_OF_FILE_ENDING = 11;
    private static final String LANGUAGE_KEY = "language";

    private ComboBox myLanguageBox;

    private HBox myComboBoxes;

    /**
     * Constructor that creates the Language Combo Box using information from Combo Box Resource Bundle.
     * Language is bound to the language used to parse commands and display text.
     * Language Combo Box is wrapped in an HBox with an informative label.
     * @param language is the Object Property representing language for the whole program, held in InputView.
     */
    public ComboBoxInputs(ObjectProperty<String> language) {
        myComboBoxes = new HBox();
        makeComboVBox(LANGUAGE_KEY);
        myLanguageBox.valueProperty().bindBidirectional(language);
        myComboBoxes = formatButtons(myComboBoxes);
    }

    /**
     * Basic getter method to return the HBox holding the Language Combo Box and its description.
     * @return HBox myComboBoxes which holds the language Combo Box.
     */
    public HBox getComboBoxesHBox() {
        return myComboBoxes;
    }

    private void makeComboVBox(String comboBoxType) {
        VBox addedVBox = new VBox();
        addedVBox.setAlignment(Pos.CENTER);

        String[] ComboBoxInfo  = myComboBoxesBundle.getString(comboBoxType).split(PROPERTIES_REGEX_SPLITTER);

        Label addedLabel = new Label(ComboBoxInfo[VBOX_LABEL_INDEX]);
        ComboBox addedComboBox = new ComboBox(allLanguages);

        if (comboBoxType.equals(LANGUAGE_KEY)) myLanguageBox = addedComboBox;
        myLanguageBox.setValue("English");

        addedVBox.getChildren().addAll(addedLabel, addedComboBox);
        myComboBoxes.getChildren().add(addedVBox);
    }

    private ObservableList initLanguageOptions() {
        File languageDirectory = new File(PATH_TO_RESOURCE_LANGUAGES);
        List languageNames = new ArrayList<String>();
        for (File tempFile : languageDirectory.listFiles()) {
            String fileName = tempFile.getName();
            fileName = fileName.substring(0, fileName.length() - LENGTH_OF_FILE_ENDING);
            languageNames.add(fileName);
        }
        return FXCollections.observableArrayList(languageNames);
    }
}
