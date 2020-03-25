package slogo.Model.Commands.ConcreteCommands;

import java.util.List;
import java.util.function.Function;
import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

/**
 * Subclass to create a Repeat Command
 *
 * @author Frank Tang
 */
public class Repeat extends Command {

  private double returnArgValue = 0;
  private static final int argumentsNeeded = 1;
  private List<String> linesSubArray;
  private Number amountOfIterations;
  private CommandDatabase database;
  private Function<List<String>, Number> parseTextFunction;
  private Function<List<String>, Number> listEndFunction;

  public Repeat(CommandDatabase data) {
    super(data);
    database = data;
  }

  /**
   * Repeats the given input string in the list by the amount of times given by the iteration parameter
   */
  @Override
  public Double executeAndReturnValue() {
    amountOfIterations = database.getParameterStack().pop();
    parseTextFunction = database.getParseFunction();
    listEndFunction = database.getListFunction();
    linesSubArray = database.getCurrentLineArray();

    List<String> tempList = linesSubArray.subList(linesSubArray.indexOf("["), linesSubArray.size());
    int listEnd = listEndFunction.apply(tempList).intValue();
    linesSubArray = tempList.subList(1, listEnd);
    for(int i = 1; i <= amountOfIterations.intValue(); i++) {
      returnArgValue = parseTextFunction.apply(linesSubArray).doubleValue();
    }
    return returnArgValue;
  }
  /**
   * Returns the amount of arguments that this command needs before it can be made
   */
  @Override
  public int getArgumentsNeeded(){
    return this.argumentsNeeded;
  }

}


