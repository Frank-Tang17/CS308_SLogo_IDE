package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

/**
 * Subclass to create a Power Command
 *
 * @author Frank Tang
 */
public class Power extends Command {

  private double returnArgValue;
  private Number baseNumber;
  private Number exponentNumber;
  private static final int argumentsNeeded = 2;
  private CommandDatabase database;



  public Power(CommandDatabase data) {
    super(data);
    database = data;

  }

  /**
   * Returns the calculation of putting the first term to the second term power.
   */
  @Override
  public Double executeAndReturnValue() {
    baseNumber = database.getParameterStack().pop();
    exponentNumber = database.getParameterStack().pop();
    returnArgValue = Math.pow(baseNumber.doubleValue(), exponentNumber.doubleValue());
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


