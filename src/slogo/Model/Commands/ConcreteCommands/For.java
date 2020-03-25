package slogo.Model.Commands.ConcreteCommands;

import java.util.List;
import java.util.function.Function;
import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

/**
 * Subclass to create a For Command
 *
 * @author Frank Tang
 */
public class For extends Command {

  private double returnArgValue = 0;
  private List<String> linesSubArray;
  private static final int argumentsNeeded = 0;
  private Number variableStart;
  private Number variableEnd;
  private Number variableIncrement;
  private String variableName;
  private CommandDatabase database;
  private Function<List<String>, Number> parseTextFunction;
  private Function<List<String>, Number> listEndFunction;


  public For(CommandDatabase data) {
    super(data);
    database = data;

  }

  /**
   * Sets a variable counter for the listed variable in the first list and then executes the
   * commands with that variable in the second list
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
    variableStart = Integer.parseInt(variableList.get(1));
    variableEnd = Integer.parseInt(variableList.get(2));
    variableIncrement = Integer.parseInt(variableList.get(3));

    List<String> commandList = linesSubArray.subList(listEnd + 2, linesSubArray.size());
    commandList = commandList.subList(commandList.indexOf("["), commandList.size());
    listEnd = listEndFunction.apply(commandList).intValue();
    commandList = commandList.subList(1, listEnd);

    for (int i = variableStart.intValue(); i <= variableEnd.intValue();
        variableIncrement.intValue()) {
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


