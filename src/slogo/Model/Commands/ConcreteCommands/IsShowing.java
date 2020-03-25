package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;
import slogo.Model.TurtleData;

/**
 * Subclass to create an IsShowing Command
 *
 * @author Frank Tang
 */
public class IsShowing extends Command {

  private double returnArgValue;
  private static final int argumentsNeeded = 0;
  private CommandDatabase database;
  private TurtleData turtleObject;


  public IsShowing(CommandDatabase data) {
    super(data);
    database = data;

  }

  /**
   * Returns a 1 if turtle is showing, returns a 0 if not
   */
  @Override
  public Integer executeAndReturnValue() {
    turtleObject = database.getTurtle();
    returnArgValue = turtleObject.getTurtleVisibility();
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


