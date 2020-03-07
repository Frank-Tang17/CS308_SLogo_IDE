package slogo.Model.CommandInfrastructure;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Stack;
import java.util.function.Function;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.paint.Color;
import jdk.dynalink.linker.support.TypeUtilities;
import slogo.Model.ModelParser;
import slogo.Model.TurtleData;

public class CommandDatabase {

  private String targetVariable;
  private Number parameterOne;
  private Number parameterTwo;
  private Stack<Number> parameterStack = new Stack<>();
  private MapProperty<String, Number> VARIABLE_MAP = new SimpleMapProperty(
      FXCollections.observableMap(new LinkedHashMap<String, Number>()));

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
    backgroundColorProperty = new SimpleObjectProperty<Color>();
    penColorProperty = new SimpleObjectProperty<Color>();

  }

  public List<TurtleData> getTurtleList() {
    return turtleList;
  }

  public void setTurtleList(List<TurtleData> newTurtleList) {
    turtleList = newTurtleList;
  }

  public void setActiveTurtle(TurtleData activeTurtle){
      targetTurtle = activeTurtle;
  }

  public TurtleData getTurtle() {
    return targetTurtle;
  }

  public void setBackgroundColor(List<Integer> rgbList) {
    Color color = Color.rgb(rgbList.get(0), rgbList.get(1), rgbList.get(2));
    backgroundColorProperty.setValue(color);
  }

  public void setPenColor(List<Integer> rgbList) {
    Color color = Color.rgb(rgbList.get(0), rgbList.get(1), rgbList.get(2));
    penColorProperty.setValue(color);
  }

  public void bindBackgroundColor(Property viewBackground) {
    viewBackground.bindBidirectional(backgroundColorProperty);
  }

  public void bindPenColor(Property viewBackground) {
    viewBackground.bindBidirectional(penColorProperty);
  }

  public Stack<Number> getParameterStack(){
    return parameterStack;
  }


  public void setListArray(List<String> array) {
    currentLineArray = array;
  }

  public Function<List<String>, Number> getParseFunction() {
    return parseFunction;
  }
  public Function<List<String>, Number> getListFunction() {
    return listFunction;
  }

  public List<String> getCurrentLineArray() {
    return currentLineArray;
  }



  /**
   * Prompt the user to make a bet from a menu of choices.
   */
  public void addParser(ModelParser parser) {
    originParser = parser;
    parseFunction = d -> originParser.parseText(d);
    listFunction = l -> originParser.findListEnd(l);
//    updateCommandMap();
  }



  /**
   * Prompt the user to make a bet from a menu of choices.
   */
  public void setParameterOne(Number newValue) {
    parameterOne = newValue;
  }

  /**
   * Prompt the user to make a bet from a menu of choices.
   */
  public void setParameterTwo(Number newValue) {
    parameterTwo = newValue;
  }

  /**
   * Prompt the user to make a bet from a menu of choices.
   */
  public void setVariableName(String targetCommand) {
    targetVariable = targetCommand;
  }


  /**
   * Prompt the user to make a bet from a menu of choices.
   */
  public MapProperty getVariableMap() {
    return this.VARIABLE_MAP;
  }

  public void bindVariables(MapProperty displayedVariables) {
    displayedVariables.bind(VARIABLE_MAP);
  }

  public void addToVariableMap(String command, Number expression) {
    this.VARIABLE_MAP.putIfAbsent(command, expression);
    this.VARIABLE_MAP.put(command, expression);
  }


  public void addToColorMap(int index, List<Integer> color) {
    this.COLOR_MAP.putIfAbsent(index, color);
    this.COLOR_MAP.put(index, color);
  }

  public void bindColors(MapProperty viewColors) {
    viewColors.bind(COLOR_MAP);
  }

  public MapProperty getColorMap() {
    return COLOR_MAP;
  }

}
