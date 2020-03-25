package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

/**
 * Subclass to create a NaturalLog Command
 *
 * @author Frank Tang
 */
public class NaturalLog extends Command {

  private double returnArgValue;
  private Number firstTerm;
  private static final int argumentsNeeded = 1;
  private CommandDatabase database;


  public NaturalLog(CommandDatabase data) {
    super(data);
    database = data;

  }

  /**
   * Returns the natural log of the parameter given
   */
  @Override
  public Double executeAndReturnValue() {
    firstTerm = database.getParameterStack().pop();
    returnArgValue = Math.log(firstTerm.doubleValue());
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


