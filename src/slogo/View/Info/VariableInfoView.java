package slogo.View.Info;

import javafx.beans.property.MapProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleMapProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Class representing the information displayed regarding the variables held in the backend.
 * Information is encoded into a VBox that is displayed in the toggle button in InfoViews.
 * The variable info is bound via a Map to the backend so it automatically updates.
 * @author Eric Doppelt
 */
public class VariableInfoView  {

    private static final String COMMAND_TEXT_DEFAULT = "Enter Value";
    private static final String COMMAND_TEXT_HEADER = "Specify a New Value for the Variable";
    private static final String VARIABLE_SETUP = "make :";
    private static final String WHITESPACE = "\\s+";
    private static final String COLON_REGEX = ":";
    private static final String SPACE = " ";
    private static final int VARIABLE_NAME_INDEX = 1;

    private MapProperty<String, Integer> myVariables;
    private Consumer<List<String>> myParserCommand;
    private VBox displayedInfo;

    /**
     * Constructor initialize displayed VBox, command parser, and map of variables that is binded to the backend.
     * Change listener is added to variables to automatically display new information as it is entered into the system.
     * Labels representing variables are made such that clicking them allows the user to update their value.
     * @param parser is a consumer that accepts String commands and executes them.
     */
    public VariableInfoView(Consumer<List<String>> parser) {
        displayedInfo = new VBox();
        myParserCommand = parser;

        myVariables = new SimpleMapProperty<>();
        myVariables.addListener(((observable, oldValue, newValue) -> {
            displayedInfo.getChildren().clear();
            for (String s : newValue.keySet()) {

                Label addedLabel = new Label(s.substring(1) + ": " + newValue.get(s));
                addedLabel.setOnMouseClicked(e -> updateVariable(s));
                displayedInfo.getChildren().add(addedLabel);
            }
        }));
    }

    /**
     * Basic getter method that returns the VBox to display in InfoViews
     * @return VBox of labels representing each variable.
     */
    public VBox getInfoVBox() {
        return displayedInfo;
    }

    /**
     * Basic getter for the varaible map; this is used to bind the information to the backend.
     * @return Property holding the variable information specifically in a MapProperty.
     */
    public Property getBindableInfo() {
        return myVariables;
    }

    private void updateVariable(String varInfo) {
        TextInputDialog inputVar = new TextInputDialog(COMMAND_TEXT_DEFAULT);
        inputVar.setHeaderText(COMMAND_TEXT_HEADER);
        inputVar.showAndWait();

        int newVariableValue;
        try {
            newVariableValue = Integer.parseInt(inputVar.getEditor().getText());
        } catch (Exception e) {
            System.out.println("Error in updateVariable");
            return;
        }

        String varCommand = formatVariableCommand(varInfo, newVariableValue);
        System.out.println(varCommand);
        passCommand(varCommand);
    }

    private void passCommand(String command) {
        myParserCommand.accept(Arrays.asList(command.split(WHITESPACE)));
    }

    private String formatVariableCommand(String variableInfo, int newValue) {
        System.out.println(variableInfo);
        String variableName = variableInfo.split(COLON_REGEX)[VARIABLE_NAME_INDEX];
        return VARIABLE_SETUP + variableName + SPACE + newValue;
    }
}
