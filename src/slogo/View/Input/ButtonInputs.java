package slogo.View.Input;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import slogo.Model.FileManager.FileReader;
import slogo.Model.FileManager.FileWriter;
import slogo.View.SlogoView;

/**
 * ButtonInputs holds the Buttons used in the UI to take input from the user, including setting a turtle image, setting preferences, saving preferences, and creating a new workspace.
 * The class extends Inputs, but the inheritance hierarchy is limited since the author ran out of time implementing it.
 * The class interacts with XML Reading and Writing via FileReaders and FileWriters to save and load preferences.
 * All buttons are held in an HBox which is added to InputView.
 */
public class ButtonInputs extends Inputs {

    private HBox myButtons;

    private static final String BUTTONS_BUNDLE = "buttons";
    private ResourceBundle myButtonsBundle = ResourceBundle.getBundle(BUTTONS_BUNDLE);
    private static final List<String> ALL_BUTTONS = new ArrayList<>(Arrays.asList("turtle", "preferences", "save", "workspace"));

    private static final String ACCEPTABLE_TURTLE_FILE = "*.png";
    private static final String TURTLE_FILE_EXTENSION = ".png";
    private static final String TYPE_OF_TURTLE_FILE = "PNG";
    private static final String INITIAL_DIRECTORY = "user.dir";
    private static final String TURTLEIMAGE_PACKAGE = "turtleImages/";
    private static final String DEFAULT_TURTLE = "perfectTurtle";
    private static final String DEFAULT_TURTLE_FILE = TURTLEIMAGE_PACKAGE + DEFAULT_TURTLE + TURTLE_FILE_EXTENSION;
    private final FileChooser TURTLE_FILE_CHOOSER = createFileChooser(ACCEPTABLE_TURTLE_FILE, TYPE_OF_TURTLE_FILE, INITIAL_DIRECTORY);

    private static final String ACCEPTABLE_PREF_FILE = "*.xml";
    private static final String TYPE_OF_PREF_FILE = "XML";
    private final FileChooser PREF_FILE_CHOOSER = createFileChooser(ACCEPTABLE_PREF_FILE, TYPE_OF_PREF_FILE, INITIAL_DIRECTORY);

    private static final int VBOX_LABEL_INDEX = 0;
    private static final int BUTTON_TEXT_INDEX = 1;
    private static final int BUTTON_METHOD_INDEX = 2;
    private static final String PROPERTIES_REGEX_SPLITTER = ", ";

    private static final String SAVE_CONFIG_DEFAULT = "Configuration File Name";
    private static final String SAVE_CONFIG_HEADER = "Enter a Name for This Preference File";

    private FileReader myReader;
    private FileWriter myWriter;

    //TODO: duplicated in InputView
    ObjectProperty turtleProperty;
    ObjectProperty backgroundProperty;
    ObjectProperty languageProperty;

    /**
     * Constructor that initializes the HBox holding all buttons, the Properties that relate to each button, and the FileReader and FileWriter to save/load preferences.
     * @param background is the background color property that is bound to the background of the program in SlogoView. This automatically updates when loading preferences.
     * @param language is the string property representing the language of the program. This is bound to the information displayed in InfoView and the language in which commands are parsed. It is updated on loading preferences.
     * @param turtle is the file property representing the turtle image. It is bound to the image displayed in TurtleView which is updated when loading preferences or a new turtle image.
     */
    public ButtonInputs(ObjectProperty background,ObjectProperty language, ObjectProperty<File> turtle) {
        turtleProperty = new SimpleObjectProperty<File>();
        turtleProperty.bindBidirectional(turtle);
        turtleProperty.setValue(new File(DEFAULT_TURTLE_FILE));

        backgroundProperty = new SimpleObjectProperty<Color>();
        backgroundProperty.bindBidirectional(background);
        backgroundProperty.setValue(Color.RED);

        languageProperty = new SimpleObjectProperty<String>();
        languageProperty.bindBidirectional(language);
        languageProperty.setValue("English");

        myButtons = new HBox();
        for (String buttonType : ALL_BUTTONS) makeButtonVBox(buttonType);
        myButtons = formatButtons(myButtons);

        myReader = new FileReader();
        myWriter = new FileWriter();
    }

    /**
     * Basic getter method that returns an HBox holding all buttons and their labels corresponding to their functionality.
     * @return myButtons HBox to be displayed in InputView.
     */
    public HBox getButtonsHBox() {return myButtons;}

    private void makeButtonVBox(String buttonType) {
        VBox addedVBox = new VBox();
        addedVBox.setAlignment(Pos.CENTER);
        String[] buttonInfo = myButtonsBundle.getString(buttonType).split(PROPERTIES_REGEX_SPLITTER);
        Label addedLabel = new Label(buttonInfo[VBOX_LABEL_INDEX]);
        Button addedButton = new Button(buttonInfo[BUTTON_TEXT_INDEX]);
        // FIXME: this is bad code
        addedButton.setOnAction(e -> {
            try {
                System.out.println(buttonInfo[BUTTON_METHOD_INDEX]);
                this.getClass().getDeclaredMethod(buttonInfo[BUTTON_METHOD_INDEX], null).invoke(this);
            } catch(Exception ex) {
                System.out.println("a" + ex);
            }
        });
        addedVBox.getChildren().addAll(addedLabel, addedButton);
        myButtons.getChildren().add(addedVBox);
    }


    private void inputTurtleFile() {
        File turtleFile = TURTLE_FILE_CHOOSER.showOpenDialog(new Stage());
        //TODO: Handle this error if the Turtle File selected is null and check type of image
        if (turtleFile == null) {
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
       File prefFile = PREF_FILE_CHOOSER.showOpenDialog(new Stage());
       if (prefFile == null) {
           return;
       }
       Map<String, String> newProperties = myReader.getConfigMap(prefFile);

        backgroundProperty.setValue(Color.web(newProperties.get("background")));
        languageProperty.setValue(newProperties.get("language"));
        turtleProperty.setValue(new File(newProperties.get("turtle")));
    }

     private void saveProperties() {
        Map<String, String> savedPreferences = new HashMap<String, String>();
        System.out.println("URL: " + turtleProperty.getValue().toString());

        savedPreferences.put("turtle", turtleProperty.getValue().toString());
        savedPreferences.put("language", languageProperty.getValue().toString());
        savedPreferences.put("background", backgroundProperty.getValue().toString());

         TextInputDialog configName = new TextInputDialog(SAVE_CONFIG_DEFAULT);
        configName.setHeaderText(SAVE_CONFIG_HEADER);
        configName.showAndWait();

        // TODO: THROW ERROR
        if (configName.getEditor().getText().equals(SAVE_CONFIG_DEFAULT)) {
            System.out.println("no value entered");
            return;
        };

        myWriter.saveConfig(savedPreferences, configName.getResult());
    }

    private void createNewWindow() {
        SlogoView newWindow = new SlogoView(new Stage());
    }
}
