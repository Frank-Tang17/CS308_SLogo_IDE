package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

/**
 * Subclass to create a Sum Command
 *
 * @author Frank Tang
 */
public class Sum extends Command {


  private Number firstTerm;
  private Number secondTerm;
  private double returnArgValue;
  private static final int argumentsNeeded = 2;
  private CommandDatabase database;



  public Sum(CommandDatabase data) {
    super(data);
    database = data;

  }


  /**
   * Returns the sum of the two parameters given
   */
  @Override
  public Double executeAndReturnValue() {
    firstTerm = database.getParameterStack().pop();
    secondTerm = database.getParameterStack().pop();
    returnArgValue = firstTerm.doubleValue() + secondTerm.doubleValue();
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


