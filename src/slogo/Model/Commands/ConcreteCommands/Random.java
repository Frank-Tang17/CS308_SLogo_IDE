package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

/**
 * Subclass to create a Random Command
 *
 * @author Frank Tang
 */
public class Random extends Command {

  private double returnArgValue;
  private Number maxRange;
  private static final int argumentsNeeded = 1;
  private CommandDatabase database;


  public Random(CommandDatabase data) {
    super(data);
    database = data;

  }

  /**
   * Returns a random value from up to the parameter given
   */
  @Override
  public Integer executeAndReturnValue() {
    maxRange = database.getParameterStack().pop();
    double randomValue = (int) Math.random() * maxRange.doubleValue();
    returnArgValue = randomValue;
    return (int) this.returnArgValue;
  }

  /**
   * Returns the amount of arguments that this command needs before it can be made
   */
  @Override
  public int getArgumentsNeeded() {
    return this.argumentsNeeded;
  }


}


