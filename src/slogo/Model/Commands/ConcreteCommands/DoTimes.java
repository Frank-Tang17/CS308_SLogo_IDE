package slogo.Model.Commands.ConcreteCommands;

import java.util.List;
import java.util.function.Function;
import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

/**
 * Subclass to create a DoTimes Command
 *
 * @author Frank Tang
 */
public class DoTimes extends Command {

  private double returnArgValue = 0;
  private List<String> linesSubArray;
  private Number variableLimit;
  private String variableName;
  private static final int argumentsNeeded = 0;
  private CommandDatabase database;
  private Function<List<String>, Number> parseTextFunction;
  private Function<List<String>, Number> listEndFunction;


  public DoTimes(CommandDatabase data) {
    super(data);
    database = data;

  }

  /**
   * Sets the variables in the first list to the variable map and then calls the parseText() method on the input string in the second list
   */
  @Override
  public Double executeAndReturnValue() {
    parseTextFunction = database.getParseFunction();
    listEndFunction = database.getListFunction();
    linesSubArray = database.getCurrentLineArray();

    List<String> variableList = linesSubArray
        .subList(linesSubArray.indexOf("["), linesSubArray.size());
    int listEnd = listEndFunction.apply(variableList).intValue();
    variableList = variableList.subList(1, listEnd);

    variableName = variableList.get(0);
    variableLimit = Integer.parseInt(variableList.get(1));

    List<String> commandList = linesSubArray.subList(listEnd + 2, linesSubArray.size());
    commandList = commandList.subList(commandList.indexOf("["), commandList.size());
    listEnd = listEndFunction.apply(commandList).intValue();
    commandList = commandList.subList(1, listEnd);

    for (int i = 1; i <= variableLimit.intValue();
        i++) {
      database.addToVariableMap(variableName, i);
      returnArgValue = parseTextFunction.apply(commandList).doubleValue();
    }

    return this.returnArgValue;
  }

  /**
   * Returns the amount of arguments that this command needs before it can be made
   */
  @Override
  public int getArgumentsNeeded() {
    return this.argumentsNeeded;
  }


}


