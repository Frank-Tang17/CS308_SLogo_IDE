package slogo.View.Info;

import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

/**
 * Class that wraps a toggle button and help button within a VBox, displayed in SlogoViews BorderPane.
 * Toggle Button holds data regarding colors, variables, commands, and history.
 * Help Button links to a directory of commands.
 * @author Eric Doppelt
 */
public class InfoViews {

    private HistoryInfoView myHistoryView;
    private VariableInfoView myVariableView;
    private ColorInfoView myColorView;
    private CommandInfoView myCommandView;


    // TODO: duplicated with space below?
    private static final String WHITESPACE = "\\s+";

    private static final String HEADER_BUNDLE = "headers";

    private VBox myInfoPanel;

    private ToggleGroup myToggleGroup = new ToggleGroup();
    private HBox myButtonPanel;
    private ToggleButton myHistoryToggle;
    private ToggleButton myCommandToggle;
    private ToggleButton myVariableToggle;
    private ToggleButton myColorsToggle;

    private final String HELP_IMAGE_PATH = "infoGraphic.png";
    private Button myHelpButton;

    private ScrollPane myScrollPane;

    private ResourceBundle myLanguageBundle;
    private ResourceBundle myHeadersBundle;

    private Consumer<List<String>> myParserCommand;

    private static final String COMMAND_TEXT_DEFAULT = "Enter Value";
    private static final String COMMAND_TEXT_HEADER = "Specify a New Value for the Variable";

    private static final String COLON_REGEX = ":";
    private static final int VARIABLE_NAME_INDEX = 1;
    private static final String VARIABLE_SETUP = "make :";
    private static final String SPACE = " ";

    private Property<String> myLanguage;

    private static final String NUMBER_REGEX = "^-?\\d+\\.\\d+$";
    private static final String TRANSLATION_SPLITTER = "\\|";
    private static final int DISPLAYED_TRANSLATION_INDEX = 0;

    /**
     * Constructor that initializes the Toggle Group and Help Button. Displayed info is stored in user data of Toggle Button.
     * @param parserCommand is a consumer that accepts strings to execute via the backend. This is passed to myHistoryView, myVariableView, and myCommandView.
     * @param language is a String Property bound to the language held in Input. On a change, updateLanguage() is called.
     */
    public InfoViews(Consumer<List<String>> parserCommand, Property<String> language) {
        myParserCommand = parserCommand;
        myLanguage = new SimpleObjectProperty<String>();
        myLanguage.bind(language);
        myLanguage.addListener((observable, oldValue, newValue) -> updateLanguage());
        myLanguageBundle = ResourceBundle.getBundle(language.getValue());

        myHeadersBundle = ResourceBundle.getBundle(HEADER_BUNDLE);

        initInfoPanel();

        initButtons();
        initProperties();

        setButtonActions();
    }

    /**
     * Getter method that returns variable info via a Property. Called in Slogoview to bind to the backend.
     * @return Property myVariables held in myVariablesView that is specifically a Map Property of all variables.
     */
    public Property getVariableProperty() {
        return myVariableView.getBindableInfo();
    }

    /**
     * Getter method that returns history info via a Property. Called in Slogoview to bind to the backend.
     * @return Property myHistory held in myHistoryView that is specifically a List Property of all variables.
     */
    public Property getHistoryProperty() {return myHistoryView.getHistoryProperty();}

    /**
     * Getter method that returns command info via a Property. Called in Slogoview to bind to the backend.
     * @return Property myInfo held in myCommandView that is specifically a Map Property of all commands.
     */
    public Property getCommandProperty() {
        return myCommandView.getInfoProperty();
    }

    /**
     * Getter method that returns color info via a MapProperty. Called in Slogoview to bind to the backend.
     * @return Property myInfo held in myColorView that is specifically a Map Property of all colors.
     */
    public MapProperty getColorsProperty() {
        return myColorView.getInfoProperty();
    }

    /**
     * Getter method that returns the VBox wrapping the Toggle Button, its User Data, and the Help Button. This is displayed in SlogoView's BorderPane.
     * @return VBox to display info in SlogoView.
     */
    public VBox getCompletePanel() {
        return myInfoPanel;
    }

    private void initInfoPanel() {
        myButtonPanel = new HBox();
        myScrollPane = new ScrollPane();
        myScrollPane.setBackground(new Background(new BackgroundFill(Color.CORAL, CornerRadii.EMPTY, Insets.EMPTY)));

        myInfoPanel = new VBox();
        myInfoPanel.getChildren().addAll(myButtonPanel, myScrollPane);
        myInfoPanel.setVgrow(myScrollPane, Priority.ALWAYS);
    }

    //TODO: REMOVE DUPLICATION
    private void initProperties() {
        myHistoryView = new HistoryInfoView(myParserCommand, myLanguage);
        myHistoryToggle = createToggleButton(myHeadersBundle.getString("historyHeader"));
        myHistoryToggle.setUserData(myHistoryView.getInfoVBox());

        myVariableView = new VariableInfoView(myParserCommand);
        myVariableToggle = createToggleButton(myHeadersBundle.getString("variableHeader"));
        myVariableToggle.setUserData(myVariableView.getInfoVBox());

        myColorView = new ColorInfoView();
        myColorsToggle = createToggleButton(myHeadersBundle.getString("colorHeader"));
        myColorsToggle.setUserData(myColorView.getInfoVBox());

        myCommandView = new CommandInfoView(myParserCommand, myLanguage);
        myCommandToggle = createToggleButton(myHeadersBundle.getString("commandHeader"));
        myCommandToggle.setUserData(myCommandView.getInfoVBox());
    }

    private void passCommand(String command) {
        myParserCommand.accept(Arrays.asList(command.split(WHITESPACE)));
    }

    private void initButtons() {
        myHelpButton = createHelpButton();
    }

    private ToggleButton createToggleButton(String s) {
        ToggleButton tempToggle = new ToggleButton(s);
        tempToggle.setToggleGroup(myToggleGroup);
        myButtonPanel.getChildren().add(tempToggle);
        return tempToggle;
    }

    private Button createHelpButton() {
        Button tempButton = new Button();
        tempButton.setPadding(new Insets(0));
        myButtonPanel.getChildren().add(tempButton);
        return tempButton;
    }


    private void setButtonActions() {
        for (Toggle tempToggle : myToggleGroup.getToggles()) {
            ToggleButton tempToggleButton = (ToggleButton) tempToggle;
            tempToggleButton.setOnAction(event -> myScrollPane.setContent((Node) tempToggleButton.getUserData()));
        }
        myHelpButton.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(new URL("https://www2.cs.duke.edu/courses/compsci308/current/assign/03_parser/commands.php").toURI());
            } catch (IOException | URISyntaxException e) {
                System.out.println("Bad Help Button URL!");
            }
        });
    }

    private void updateLanguage() {
        myLanguageBundle = ResourceBundle.getBundle(myLanguage.getValue());
    }

    private String changeCommandLanguage(String oldLanguage) {
        StringBuilder translatedCommand = new StringBuilder();
        for (String commandBlock : oldLanguage.split(WHITESPACE)) {
            if (commandBlock.matches(NUMBER_REGEX)) translatedCommand.append(commandBlock + SPACE);
            else {
                String fullTranslation = myLanguageBundle.getString(commandBlock);
                String displayedTranslation = fullTranslation.split(TRANSLATION_SPLITTER)[DISPLAYED_TRANSLATION_INDEX];
                translatedCommand.append(displayedTranslation + SPACE);
            }
        }
        return translatedCommand.toString();
    }
}
