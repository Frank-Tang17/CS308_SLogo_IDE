package slogo.View.Input;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.property.Property;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import slogo.DisplayError;
import slogo.Model.FileManager.FileReader;
import slogo.Model.FileManager.FileWriter;
import slogo.View.SlogoView;

/**
 * ButtonInputs holds the Buttons used in the UI to take input from the user, including setting a turtle image, setting preferences, saving preferences, and creating a new workspace.
 * The class interacts with XML Reading and Writing via FileReaders and FileWriters to save and load preferences.
 * All buttons are held in an HBox, inherited from InputType, which is then added to InputView.
 */
public class ButtonInputs extends InputType {

    private static final String BUTTONS_BUNDLE = "buttons";
    private ResourceBundle myButtonsBundle = ResourceBundle.getBundle(BUTTONS_BUNDLE);
    private static final List<String> ALL_BUTTONS = new ArrayList<>(Arrays.asList("turtle", "preferences", "save", "workspace"));

    private static final String ACCEPTABLE_TURTLE_FILE = "*.png";
    private static final String TURTLE_FILE_EXTENSION = ".png";
    private static final String TYPE_OF_TURTLE_FILE = "PNG";
    private static final String INITIAL_DIRECTORY = "user.dir";
    private static final String TURTLEIMAGE_PACKAGE = "turtleImages/";

    private static final String ACCEPTABLE_PREF_FILE = "*.xml";
    private static final String TYPE_OF_PREF_FILE = "XML";

    private static final String DEFAULT_TURTLE = "perfectTurtle";
    private static final String DEFAULT_TURTLE_PATH = TURTLEIMAGE_PACKAGE + DEFAULT_TURTLE + TURTLE_FILE_EXTENSION;
    private static final File DEFAULT_TURTLE_FILE = new File(DEFAULT_TURTLE_PATH);
    private static final String DEFAULT_LANGUAGE = "English";
    private static final Color DEFAULT_BACKGROUND_COLOR = Color.RED;

    private static final int TEXT_INDEX = 1;
    private static final int METHOD_INDEX = 2;

    private static final String SAVE_CONFIG_DEFAULT = "Configuration File Name";
    private static final String SAVE_CONFIG_HEADER = "Enter a Name for This Preference File";

    private static final String REFLECTION_ERROR_KEY = "ButtonReflectionException";
    private static final String NULL_TURTLE_KEY = "NullTurtleException";
    private static final String NULL_PREF_KEY = "NullPrefException";

    private static final String TURTLE_PREF_KEY = "turtle";
    private static final String LANGUAGE_PREF_KEY = "language";
    private static final String BACKGROUND_PREF_KEY = "background";

    private FileReader myReader;
    private FileWriter myWriter;

    private FileChooser myTurtleChooser;
    private FileChooser myPreferencesChooser;

    private Property turtleProperty;
    private Property backgroundProperty;
    private Property languageProperty;

    /**
     * Constructor that initializes the HBox holding all buttons, the File Chooosers, the Properties that relate to each button, and the FileReader and FileWriter to save/load preferences.
     * @param bindedBackground is the background color property that is bound to the background of the program in SlogoView. This automatically updates when loading preferences.
     * @param bindedLanguage is the string property representing the language of the program. This is bound to the information displayed in InfoView and the language in which commands are parsed. It is updated on loading preferences.
     * @param bindedTurtle is the file property representing the turtle image. It is bound to the image displayed in TurtleView which is updated when loading preferences or a new turtle image.
     */
    public ButtonInputs(Property bindedBackground, Property bindedLanguage, Property bindedTurtle) {
        super();

        turtleProperty = createBindedProperty(bindedTurtle, DEFAULT_TURTLE_FILE);
        backgroundProperty = createBindedProperty(bindedBackground, DEFAULT_BACKGROUND_COLOR);
        languageProperty = createBindedProperty(bindedLanguage, DEFAULT_LANGUAGE);

        myTurtleChooser = createFileChooser(ACCEPTABLE_TURTLE_FILE, TYPE_OF_TURTLE_FILE, INITIAL_DIRECTORY);
        myPreferencesChooser = createFileChooser(ACCEPTABLE_PREF_FILE, TYPE_OF_PREF_FILE, INITIAL_DIRECTORY);

        for (String buttonType : ALL_BUTTONS) makeAndAddButtonBox(buttonType);
        myInputs = formatButtons(myInputs);

        myReader = new FileReader();
        myWriter = new FileWriter();
    }

    private void makeAndAddButtonBox(String buttonType) {

        String[] buttonInfo = myButtonsBundle.getString(buttonType).split(PROPERTIES_REGEX_SPLITTER);
        Button addedButton = new Button(buttonInfo[TEXT_INDEX]);
        addedButton.setOnAction(e -> {
            try {
                System.out.println(buttonInfo[METHOD_INDEX]);
                this.getClass().getDeclaredMethod(buttonInfo[METHOD_INDEX], null).invoke(this);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                DisplayError reflectionErrorPopUp = new DisplayError(REFLECTION_ERROR_KEY);
            }
        });
        createAndAddInputBox(buttonInfo[LABEL_INDEX], addedButton);
    }

    private void inputTurtleFile() {
        File turtleFile = myTurtleChooser.showOpenDialog(new Stage());
        if (turtleFile == null) {
            DisplayError fileErrorPopUp = new DisplayError(NULL_TURTLE_KEY);
            return;
        }
        turtleProperty.setValue(turtleFile);
    }

    private FileChooser createFileChooser(String extension, String fileType, String directory) {
        FileChooser returnedChooser = new FileChooser();
        returnedChooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter(fileType, extension));
        returnedChooser.setInitialDirectory(new File(System.getProperty(directory)));
        return returnedChooser;
    }

    private void loadProperties() {
       File prefFile = myPreferencesChooser.showOpenDialog(new Stage());
       if (prefFile == null) {
           DisplayError PrefLoadingPopUp = new DisplayError(NULL_PREF_KEY);
           return;
       }

       Map<String, String> newProperties = myReader.getConfigMap(prefFile);

       backgroundProperty.setValue(Color.web(newProperties.get(BACKGROUND_PREF_KEY)));
       languageProperty.setValue(newProperties.get(LANGUAGE_PREF_KEY));
       turtleProperty.setValue(new File(newProperties.get(TURTLE_PREF_KEY)));
    }

     private void saveProperties() {
        Map<String, String> savedPreferences = new HashMap<>();

        TextInputDialog configName = new TextInputDialog(SAVE_CONFIG_DEFAULT);
        configName.setHeaderText(SAVE_CONFIG_HEADER);
        configName.showAndWait();

        if (configName.getEditor().getText().equals(SAVE_CONFIG_DEFAULT)) {
            DisplayError PrefSavingPopUpError = new DisplayError(NULL_PREF_KEY);
            return;
        };

        savedPreferences.put(TURTLE_PREF_KEY, turtleProperty.getValue().toString());
        savedPreferences.put(LANGUAGE_PREF_KEY, languageProperty.getValue().toString());
        savedPreferences.put(BACKGROUND_PREF_KEY, backgroundProperty.getValue().toString());

        myWriter.saveConfig(savedPreferences, configName.getResult());
    }

    private void createNewWindow() {
        SlogoView newWindow = new SlogoView(new Stage());
    }
}
