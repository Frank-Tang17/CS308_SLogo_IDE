package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

/**
 * Subclass to create a GreaterThan Command
 *
 * @author Frank Tang
 */
public class GreaterThan extends Command {
  private double returnArgValue;
  private Number firstTerm;
  private Number secondTerm;
  private static final int argumentsNeeded = 2;
  private CommandDatabase database;

  public GreaterThan(CommandDatabase data) {
    super(data);
    database = data;



  }

  /**
   * Returns a 1 if the first parameter is larger than the second; otherwise, return 0
   */
  @Override
  public Integer executeAndReturnValue() {
    firstTerm = database.getParameterStack().pop();
    secondTerm = database.getParameterStack().pop();
    if (firstTerm.doubleValue() - secondTerm.doubleValue() > 0) {
      returnArgValue = 1;
    } else {
      returnArgValue = 0;
    }

    System.out.println(returnArgValue);
//    System.out.println("turtle Y " + turtleObject.getTurtleY());
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



