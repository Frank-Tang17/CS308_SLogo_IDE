package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

/**
 * Subclass to create a CosineCommand
 *
 * @author Frank Tang
 */
public class Cosine extends Command {

  private double returnArgValue;
  private Number amountOfDegrees;
  private static final int argumentsNeeded = 1;
  private CommandDatabase database;

  public Cosine(CommandDatabase data) {
    super(data);
    database = data;


  }

  /**
   * Takes the degree parameter and calculates the cosine of the degree value
   */
  @Override
  public Double executeAndReturnValue() {
    amountOfDegrees = database.getParameterStack().pop();
    returnArgValue = Math.cos(Math.toRadians(amountOfDegrees.doubleValue()));
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


