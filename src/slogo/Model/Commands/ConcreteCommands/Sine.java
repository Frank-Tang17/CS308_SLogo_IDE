package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

/**
 * Subclass to create a Sine Command
 *
 * @author Frank Tang
 */
public class Sine extends Command {


  private double returnArgValue;
  private Number amountOfDegrees;
  private static final int argumentsNeeded = 1;
  private CommandDatabase database;



  public Sine(CommandDatabase data) {
    super(data);
    database = data;


  }

  /**
   * Returns the sine of the parameter given
   */
  @Override
  public Double executeAndReturnValue() {
    amountOfDegrees = database.getParameterStack().pop();
    returnArgValue = Math.sin(Math.toRadians(amountOfDegrees.doubleValue()));
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


