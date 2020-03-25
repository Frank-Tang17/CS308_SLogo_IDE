package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;
import slogo.Model.TurtleData;

/**
 * Subclass to create a HideTurtle Command
 *
 * @author Frank Tang
 */
public class HideTurtle extends Command {

  private TurtleData turtleObject;
  private double returnArgValue;
  private static final int turtleVisibilityStatus = 0;
  private boolean isTurtleVisible = false;
  private static final int argumentsNeeded = 0;
  private CommandDatabase database;

  public HideTurtle(CommandDatabase data) {
    super(data);
    database = data;


  }

  /**
   * Turns the turtle invisible
   */
  @Override
  public Integer executeAndReturnValue() {
    turtleObject = database.getTurtle();
    returnArgValue = turtleVisibilityStatus;
    turtleObject.setTurtleVisibility(isTurtleVisible);
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



