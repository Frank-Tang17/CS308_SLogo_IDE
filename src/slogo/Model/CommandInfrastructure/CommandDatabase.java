package slogo.Model.CommandInfrastructure;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Stack;
import java.util.function.Function;
import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.paint.Color;
import slogo.Model.ModelParser;
import slogo.Model.TurtleData;

public class CommandDatabase {

  /**
   * CommmandDatabase class whose role is to act as a database of information for making commands
   *
   * @author Frank Tang
   */

  private String targetVariable;
  private Stack<Number> parameterStack = new Stack<>();
  private MapProperty<String, Number> VARIABLE_MAP = new SimpleMapProperty(
      FXCollections.observableMap(new LinkedHashMap<String, Number>()));

  private MapProperty<String, String> COMMAND_MAP = new SimpleMapProperty(
      FXCollections.observableMap(new LinkedHashMap<String, String>()));

  private Function<List<String>, Number> parseFunction;
  private Function<List<String>, Number> listFunction;
  private TurtleData targetTurtle;
  private List<TurtleData> turtleList;

  private MapProperty<Integer, List<Integer>> COLOR_MAP = new SimpleMapProperty(
      FXCollections.observableMap(new LinkedHashMap<Integer, List<Integer>>()));
  private ObjectProperty<Color> backgroundColorProperty;
  private ObjectProperty<Color> penColorProperty;


  private ModelParser originParser;
  private List<String> currentLineArray;

  public CommandDatabase(List<TurtleData> turtles) {
    turtleList = turtles;
    targetTurtle = turtleList.get(0);
    backgroundColorProperty = new SimpleObjectProperty<>();
    penColorProperty = new SimpleObjectProperty<>();
  }

  /**
   * Sets the pen color based on an RGB value
   */
  public void setPenColor(List<Integer> rgbList) {
    Color color = Color.rgb(rgbList.get(0), rgbList.get(1), rgbList.get(2));
    penColorProperty.setValue(color);
  }

  /**
   * Binds the pen color data to the front end
   */
  public void bindPenColor(Property viewBackground) {
    viewBackground.bindBidirectional(penColorProperty);
  }

  /**
   * Gets the list of all turtles present in program
   */
  public List<TurtleData> getTurtleList() {
    return turtleList;
  }

  /**
   * Sets the list of all possible turtles
   */
  public void setTurtleList(List<TurtleData> newTurtleList) {
    turtleList = newTurtleList;
  }

  /**
   * Sets the active turtle for implementation of multiple turtles
   */
  public void setActiveTurtle(TurtleData activeTurtle) {
    targetTurtle = activeTurtle;
  }

  /**
   * Returns the TurtleData object of a turtle
   */
  public TurtleData getTurtle() {
    return targetTurtle;
  }

  /**
   * Sets the background color via an RGB value
   */
  public void setBackgroundColor(List<Integer> rgbList) {
    Color color = Color.rgb(rgbList.get(0), rgbList.get(1), rgbList.get(2));
    backgroundColorProperty.setValue(color);
  }

  /**
   * Binds the background color data to the frontend
   */
  public void bindBackgroundColor(Property viewBackground) {
    viewBackground.bindBidirectional(backgroundColorProperty);
  }

  /**
   * Returns the current stack of parameters set by the CommandProducer
   */
  public Stack<Number> getParameterStack() {
    return parameterStack;
  }

  /**
   * Sets the current String array being parsed into the database
   */
  public void setListArray(List<String> array) {
    currentLineArray = array;
  }

  /**
   * Returns the Function for the parseText method
   */
  public Function<List<String>, Number> getParseFunction() {
    return parseFunction;
  }

  /**
   * Returns the Function for the findListEnd method
   */
  public Function<List<String>, Number> getListFunction() {
    return listFunction;
  }

  /**
   * Returns the current String array being parsed
   */
  public List<String> getCurrentLineArray() {
    return currentLineArray;
  }

  /**
   * Adds the two parser function of parsing text and finding the index of the end of a list to the
   * database
   */
  public void addParser(ModelParser parser) {
    originParser = parser;
    parseFunction = d -> originParser.parseText(d);
    listFunction = l -> originParser.findListEnd(l);
  }


  /**
   * Sets the variable name in the database to the most recently called variable name
   */
  public void setVariableName(String targetCommand) {
    targetVariable = targetCommand;
  }


  /**
   * Returns the map of variables and their values
   */
  public MapProperty getVariableMap() {
    return this.VARIABLE_MAP;
  }

  /**
   * Binds the variable map to the MapProperty
   */
  public void bindVariables(MapProperty displayedVariables) {
    displayedVariables.bind(VARIABLE_MAP);
  }

  /**
   * Adds an entry into the variable map
   */
  public void addToVariableMap(String command, Number expression) {
    this.VARIABLE_MAP.getValue().putIfAbsent(command, expression);
    this.VARIABLE_MAP.getValue().put(command, expression);
  }

  /**
   * Returns the last variable name input into the database
   */
  public String getVariableName() {
    return targetVariable;
  }

  /**
   * Adds an entry into the command map
   */
  public void addToCommandMap(String command, String commandLine) {
    this.COMMAND_MAP.getValue().putIfAbsent(command, commandLine);
    this.COMMAND_MAP.getValue().put(command, commandLine);
  }

  /**
   * Binds the Command Map to the Map Property to be displayed in the front end
   */
  public void bindCommands(MapProperty displayedCommands) {
    displayedCommands.bind(COMMAND_MAP);
  }

  /**
   * Returns the command map
   */
  public MapProperty<String, String> getCOMMAND_LIST() {
    return this.COMMAND_MAP;
  }

  /**
   * Adds to the color map
   */
  public void addToColorMap(int index, List<Integer> color) {
    this.COLOR_MAP.putIfAbsent(index, color);
    this.COLOR_MAP.put(index, color);
  }

  /**
   * Binds the Color Map to the MapProperty
   */
  public void bindColors(MapProperty viewColors) {
    viewColors.bind(COLOR_MAP);
  }

  /**
   * Returns the color map
   */
  public MapProperty getColorMap() {

    return COLOR_MAP;
  }

}
