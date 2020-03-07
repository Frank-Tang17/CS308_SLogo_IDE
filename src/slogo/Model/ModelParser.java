package slogo.Model;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.regex.Pattern;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import slogo.DisplayError;
import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.CommandInfrastructure.CommandProducer;
import slogo.Model.Commands.Command;

public class ModelParser {

  /**
   * Simple parser based on regular expressions that matches input strings to kinds of program elements.
   * Based off of ProgramParser by Robert Duvall
   * 2/20/2020
   * @author Frank Tang
   */

  private static final String REGEX_SYNTAX = "Syntax";
  private List<Entry<String, Pattern>> mySymbols;
  private CommandDatabase commandDatabase;
  private CommandProducer commandProducer;
  private List<String> linesArray;
  private ObjectProperty languageChosen;
  private int argumentThreshold;
  private Number finalCommandValue;
  private Command argumentChecker;
  private static final int zero = 0;
  private static final Integer defaultVariableValue = 0;
  private static final Integer toNumberValue = 1;
  private String symbolName;
  private String parseTerm;
  private static final String LIST_START = "ListStart";
  private static final String COMMAND = "Command";
  private static final String toCommand = "MakeUserInstruction";
  private static final String VARIABLE = "Variable";
  private static final String COMMENT = "Comment";
  private static final String variableCommand = "makeVariable";
  private static final String CONSTANT = "Constant";
  private static final String CONCRETE_COMMAND_CLASS = "slogo.Model.Commands.ConcreteCommands.";
  private static final String WHITESPACE = "\\s+";





  public ModelParser(String language, CommandDatabase commandData, CommandProducer producer){
    createBindableLanguage(language);

    commandDatabase = commandData;
    commandProducer = producer;
    commandDatabase.addParser(this);

  }

  private void createBindableLanguage(String language) {
    languageChosen = new SimpleObjectProperty<>(language);
    languageChosen.addListener((observable, oldValue, newValue) -> {
      setUpModelParserLanguage((String)newValue);
    });
    setUpModelParserLanguage(languageChosen.getValue().toString());
  }

  private void setUpModelParserLanguage(String language){
    mySymbols = new ArrayList<>();
    addPatterns(language);
    addPatterns(REGEX_SYNTAX);

  }

  public Property getParserLanguageProperty() {return languageChosen;}

  /**
   * Adds the given resource file to this language's recognized types
   */
  private void addPatterns (String syntax) {
    ResourceBundle resources = ResourceBundle.getBundle(syntax);
    for (String key : Collections.list(resources.getKeys())) {
      String regex = resources.getString(key);
      mySymbols.add(new SimpleEntry<>(key,
          Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
    }
  }

  /**
   * Returns language's type associated with the given text if one exists
   */
  private String getSymbol (String text) {
    final String ERROR = "NO MATCH";
    for (Entry<String, Pattern> e : mySymbols) {
      if (match(text, e.getValue())) {
        return e.getKey();
      }
    }
    new DisplayError("NoMatch");
    return ERROR;
  }

  // Returns true if the given text matches the given regular expression pattern
  private boolean match (String text, Pattern regex) {
    return regex.matcher(text).matches();
  }

  public int findListEnd(List<String> listToCheck){
    int listStartCounter = zero;
    int listEndCounter = zero;
    for(int i = 0; i < listToCheck.size(); i++){
      if(listToCheck.get(i).equals("]")){
        listEndCounter++;
      }
      else if(listToCheck.get(i).equals("[")){
        listStartCounter++;
      }
      if(listEndCounter == listStartCounter){
        return i;
      }
    }
    return zero;
  }

  // given some text, prints results of parsing it using the given language
  public Number parseText (List<String> inputCommandList) {
    Stack<String> commandStack = new Stack<>();
    Stack<Number> argumentStack = new Stack<>();
    for (int index = 0; index < inputCommandList.size(); index++) {
      if (inputCommandList.get(index).trim().length() > 0) {
        linesArray = inputCommandList.subList(index, inputCommandList.size());
        commandDatabase.setListArray(linesArray);
        parseTerm = inputCommandList.get(index);
        symbolName = this.getSymbol(parseTerm);
        
        if(symbolName.equals(CONSTANT)){
          argumentStack.push(Double.parseDouble(parseTerm));
        }
        else if(symbolName.equals(VARIABLE)){
          if(commandStack.peek().equals(variableCommand)){
            commandDatabase.setVariableName(parseTerm);
          }
          else if (commandDatabase.getVariableMap().containsKey(parseTerm)){
            argumentStack.push((Number) commandDatabase.getVariableMap().get(parseTerm));
          }
          else{
            argumentStack.push(defaultVariableValue);
          }
        }
        else if(symbolName.equals(COMMAND)){
          if(commandStack.size() != 0 && commandStack.peek().equals(toCommand)){
            commandDatabase.setVariableName(parseTerm);
            argumentStack.push(toNumberValue);
          }
          else if (commandDatabase.getCOMMAND_LIST().getValue().containsKey(parseTerm)){
            this.parseText(
                Arrays.asList(commandDatabase.getCOMMAND_LIST().getValue().get(parseTerm).split(WHITESPACE)));
          }
        }
        else if(symbolName.equals(LIST_START)){
          int listEnd = findListEnd(linesArray);
          index = listEnd + index;
          continue;
        }
        else if(checkCommand(symbolName)){
          commandStack.push(symbolName);
          argumentThreshold = argumentStack.size() + argumentChecker.getArgumentsNeeded();
        }
        finalCommandValue = commandProducer.parseStacks(commandStack, argumentStack, argumentThreshold);
      }
    }
    return finalCommandValue;
  }

  private boolean checkCommand(String commandName){
    try {
      Class commandClass = Class.forName(CONCRETE_COMMAND_CLASS + commandName);
      Object o = commandClass.getDeclaredConstructors()[0].newInstance(commandDatabase);
      argumentChecker = (Command) o;
      return true;
    }
    catch (Exception e){
      new DisplayError("NullCommand");
    }
    return false;
  }

}
