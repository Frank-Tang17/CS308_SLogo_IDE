package slogo.Model.Commands.ConcreteCommands;

import java.util.List;
import java.util.function.Function;
import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

/**
 * Subclass to create a BackCommand
 *
 * @author Frank Tang
 */
public class MakeUserInstruction extends Command {

  private double returnArgValue = 0;
  private Number expression;
  private String variable;
  private List<String> linesSubArray;
  private static final int argumentsNeeded = 1;
  private CommandDatabase database;
  private Function<List<String>, Number> parseTextFunction;
  private Function<List<String>, Number> listEndFunction;



  public MakeUserInstruction(CommandDatabase data) {
    super(data);
    database = data;

  }

  /**
   * Moves the turtle backwards by a pixel amount.
   */
  @Override
  public Double executeAndReturnValue() {
    parseTextFunction = database.getParseFunction();
    listEndFunction = database.getListFunction();
    linesSubArray = database.getCurrentLineArray();
    variable = database.getVariableName();
    database.getParameterStack().pop();

    List<String> commandList = linesSubArray.subList(linesSubArray.indexOf("["), linesSubArray.size());
    int listEnd = listEndFunction.apply(commandList).intValue();
    commandList = commandList.subList(1, listEnd);
    System.out.println(variable);

    String commandLine = "";
    for(String e : commandList){
      commandLine += e + " ";
      System.out.println(commandLine);
    }
    database.addToCommandMap(variable, commandLine);
    returnArgValue = 1;
    return this.returnArgValue;
  }
  @Override
  public int getArgumentsNeeded(){
    return this.argumentsNeeded;
  }


}


