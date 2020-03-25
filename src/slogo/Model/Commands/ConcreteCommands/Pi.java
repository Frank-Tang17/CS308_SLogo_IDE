package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

/**
 * Subclass to create a Pi Command
 *
 * @author Frank Tang
 */
public class Pi extends Command {

  private final double returnArgValue = Math.PI;
  private static final int argumentsNeeded = 0;

  public Pi(CommandDatabase data) {
    super(data);
  }

  /**
   * Returns the value of Pi.
   */
  @Override
  public Double executeAndReturnValue() {
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


