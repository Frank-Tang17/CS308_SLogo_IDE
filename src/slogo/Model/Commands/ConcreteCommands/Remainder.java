package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

/**
 * Subclass to create a Remainder Command
 *
 * @author Frank Tang
 */
public class Remainder extends Command {

  private Number firstTerm;
  private Number secondTerm;
  private double returnArgValue;
  private static final int argumentsNeeded = 2;
  private CommandDatabase database;


  public Remainder(CommandDatabase data) {
    super(data);
    database = data;

  }


  /**
   * Returns the mod of the two parameters
   */
  @Override
  public Integer executeAndReturnValue() {
    firstTerm = database.getParameterStack().pop();
    secondTerm = database.getParameterStack().pop();
    returnArgValue = firstTerm.doubleValue() % secondTerm.doubleValue();
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


