package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

/**
 * Subclass to create a Minus Command
 *
 * @author Frank Tang
 */
public class Minus extends Command {

  private double returnArgValue;
  private Number firstTerm;
  private static final int argumentsNeeded = 1;
  private CommandDatabase database;


  public Minus(CommandDatabase data) {
    super(data);
    database = data;

  }

  /**
   * Returns the negated value of the parameter
   */
  @Override
  public Double executeAndReturnValue() {
    firstTerm = database.getParameterStack().pop();
    returnArgValue = -firstTerm.doubleValue();
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


