package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

/**
 * Subclass to create a BackCommand
 *
 * @author Frank Tang
 */
public class MakeVariable extends Command {

  private double returnArgValue = 0;
  private Number expression;
  private String variable;
  private static final int argumentsNeeded = 1;
  private CommandDatabase database;

  public MakeVariable(CommandDatabase data) {
    super(data);
    database = data;

//    System.out.println("test" + parameterOne);
  }

  /**
   * Moves the turtle backwards by a pixel amount.
   */
  @Override
  public Double executeAndReturnValue() {
    expression = database.getParameterStack().pop();
    variable = database.getVariableName();
    database.addToVariableMap(variable, expression.doubleValue());
    returnArgValue = expression.doubleValue();
    return this.returnArgValue;
  }
  /**
   * Returns the amount of arguments that this command needs before it can be made
   */
  @Override
  public int getArgumentsNeeded(){
    return this.argumentsNeeded;
  }


}


