package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

/**
 * Subclass to create a NotEqual Command
 *
 * @author Frank Tang
 */
public class NotEqual extends Command {

  private double returnArgValue;
  private Number firstTerm;
  private Number secondTerm;
  private static final int argumentsNeeded = 2;
  private CommandDatabase database;

  public NotEqual(CommandDatabase data) {
    super(data);
    database = data;

  }

  /**
   * Returns a 1 if the two parameters are not equal to each other; otherwise, return a 0
   */
  @Override
  public Integer executeAndReturnValue() {
    firstTerm = database.getParameterStack().pop();
    secondTerm = database.getParameterStack().pop();

    if (!firstTerm.equals(secondTerm)) {
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
  public int getArgumentsNeeded(){
    return this.argumentsNeeded;
  }



}



