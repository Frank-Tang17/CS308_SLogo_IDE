package slogo.View.Info;

import javafx.beans.property.ListProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;


/**
 * Class representing the information displayed regarding the past commands that were successfully run.
 * Information is encoded into a VBox that is displayed in the toggle button in InfoViews.
 * The command history is bound via a ListProperty to the backend so it automatically updates.
 * @author Eric Doppelt
 */

public class HistoryInfoView {

    private static final String WHITESPACE = "\\s+";
    private static final String TRANSLATION_SPLITTER = "\\|";
    private static final int DISPLAYED_TRANSLATION_INDEX = 0;
    private static final String SPACE = " ";

    private static final String NUMBER_REGEX = "^-?\\d+\\.\\d+$";
    private ResourceBundle myLanguageBundle;
    private Property<String> myLanguage;
    private Consumer<List<String>> myParserCommand;

    private ListProperty<String> myHistory;

    private VBox displayedHistory;

    /**
     * Constructor initializes the displayed VBox, list of History, Consumer that executes commands, and langauge of the program.
     * A change listener is added to myHistory to automatically update the frontend to display a new command when it is executed in the backend.
     * A change listener is added to myLanguage to automatically update the frontend to translate old commands when language is changed.
     * Labels representing past commands are made to exectute the command on a click.
     * @param parser is a Consumer that accepts commands and executes them in the backend.
     * @param currentLanguage is the langauge of the program, changed via Input.
     */
    public HistoryInfoView(Consumer<List<String>> parser, Property<String> currentLanguage) {
        displayedHistory = new VBox();
        myParserCommand = parser;

        myLanguageBundle = ResourceBundle.getBundle(currentLanguage.getValue());
        myLanguage = new SimpleObjectProperty<String>();
        myLanguage.bind(currentLanguage);
        myLanguage.addListener((observable, oldValue, newValue) -> updateLanguage(newValue));

        myHistory = new SimpleListProperty<>();
        myHistory.addListener(((observable, oldValue, newValue) -> {
            if (newValue.size() > 0) {
                String newCommand = newValue.get(newValue.size() - 1);
                Label addedLabel = new Label(changeCommandLanguage(newCommand));
                addedLabel.setOnMouseClicked(e -> passCommand(addedLabel.getText()));
                displayedHistory.getChildren().add(addedLabel);
            }
        }));
    }

    /**
     * Basic getter method that retrns the VBox to display in InfoViews
     * @return VBox of labels representing each command
     */
    public VBox getInfoVBox() {
        return displayedHistory;
    }

    /**
     * Basic getter for the history map; this is used to bind the information to the backend.
     * @return Property holding the list of past commands stored as Strings
     */
    public Property getHistoryProperty() {
        return myHistory;
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

    private void passCommand(String command) {
        myParserCommand.accept(Arrays.asList(command.split(WHITESPACE)));
    }

    private void updateLanguage(String newLanguage) {
        myLanguageBundle = ResourceBundle.getBundle(newLanguage);
        displayedHistory.getChildren().clear();
        for (String command : myHistory.getValue()) {
            Label addedLabel = new Label(changeCommandLanguage(command));
            addedLabel.setOnMouseClicked(e -> passCommand(addedLabel.getText()));
            displayedHistory.getChildren().add(addedLabel);
        }
    }
}
