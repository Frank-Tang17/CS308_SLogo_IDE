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
  private CommandDatabase database;
  private static final String LIST_START = "[";
  private static final int ONE = 1;

  public Repeat(CommandDatabase data) {
    super(data);
    database = data;
  }

  /**
   * Repeats the given input string in the list by the amount of times given by the iteration parameter
   */
  @Override
  public Double executeAndReturnValue() {
    Number amountOfIterations = database.getParameterStack().pop();
    Function<List<String>, Number> parseTextFunction = database.getParseFunction();
    Function<List<String>, Number> listEndFunction = database.getListFunction();
    List<String> linesSubArray = database.getCurrentLineArray();

    List<String> tempList = linesSubArray.subList(linesSubArray.indexOf(LIST_START), linesSubArray.size());
    int listEnd = listEndFunction.apply(tempList).intValue();
    linesSubArray = tempList.subList(ONE, listEnd);
    for(int i = ONE; i <= amountOfIterations.intValue(); i++) {
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


