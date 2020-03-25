package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

/**
 * Subclass to create a Tangent Command
 *
 * @author Frank Tang
 */
public class Tangent extends Command {


  private double returnArgValue;
  private Number amountOfDegrees;
  private static final int argumentsNeeded = 1;
  private CommandDatabase database;


  public Tangent(CommandDatabase data) {
    super(data);
    database = data;

  }

  /**
   * Calculates the tangent value of the given parameter
   */
  @Override
  public Double executeAndReturnValue() {
    amountOfDegrees = database.getParameterStack().pop();
    returnArgValue = Math.tan(Math.toRadians(amountOfDegrees.doubleValue()));
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


