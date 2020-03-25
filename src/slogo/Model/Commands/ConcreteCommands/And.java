package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

/**
 * Subclass to create an AndCommand
 *
 * @author Frank Tang
 */
public class And extends Command {
  //moves turtle to an absolute screen position, where (0, 0) is the center of the screen
  //returns the distance turtle moved

  private double returnArgValue;
  private Number firstTerm;
  private Number secondTerm;
  private static final int argumentsNeeded = 2;
  private CommandDatabase database;

  public And(CommandDatabase data) {
    super(data);
    database = data;
  }

  /**
   * Compares the two parameter values and returns a 1 if they are both not equal to zero;
   * otherwise, this command returns zero
   */
  @Override
  public Integer executeAndReturnValue() {
    firstTerm = database.getParameterStack().pop();
    secondTerm = database.getParameterStack().pop();

    if (!firstTerm.equals(0) && !secondTerm.equals(0)) {
      returnArgValue = 1;
    } else {
      returnArgValue = 0;
    }

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



