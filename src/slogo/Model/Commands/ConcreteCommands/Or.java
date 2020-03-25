package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

/**
 * Subclass to create a Or Command
 *
 * @author Frank Tang
 */
public class Or extends Command {

  private double returnArgValue;
  private Number firstTerm;
  private Number secondTerm;
  private static final int argumentsNeeded = 2;
  private CommandDatabase database;

  public Or(CommandDatabase data) {
    super(data);
    database = data;

  }

  /**
   * Returns a 1 if either of the parameters given is not equal to zero; otherwise, return a 0
   */
  @Override
  public Integer executeAndReturnValue() {
    firstTerm = database.getParameterStack().pop();
    secondTerm = database.getParameterStack().pop();

    if (!firstTerm.equals(0) || !secondTerm.equals(0)) {
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



