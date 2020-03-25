package slogo.Model.Commands.ConcreteCommands;

import java.util.List;
import java.util.function.Function;
import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

/**
 * Subclass to create an If Command
 *
 * @author Frank Tang
 */
public class If extends Command {

  private double returnArgValue = 0;
  private List<String> linesSubArray;
  private Number expression;
  private static final int argumentsNeeded = 1;
  private CommandDatabase database;
  private Function<List<String>, Number> parseTextFunction;
  private Function<List<String>, Number> listEndFunction;


  public If(CommandDatabase data) {
    super(data);
    database = data;

  }

  /**
   * Executes the commands in the list if the value of the parameter is greater than 0
   */
  @Override
  public Double executeAndReturnValue() {
    expression = database.getParameterStack().pop();
    parseTextFunction = database.getParseFunction();
    listEndFunction = database.getListFunction();
    linesSubArray = database.getCurrentLineArray();

    if (expression.doubleValue() > 0) {
      List<String> tempList = linesSubArray.subList(linesSubArray.indexOf("["), linesSubArray.size());
      int listEnd = listEndFunction.apply(tempList).intValue();
      linesSubArray = tempList.subList(1, listEnd);

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


