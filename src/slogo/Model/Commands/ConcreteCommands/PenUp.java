package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;
import slogo.Model.TurtleData;

/**
 * Subclass to create a PenUp Command
 *
 * @author Frank Tang
 */
public class PenUp extends Command {

  private TurtleData turtleObject;
  private double returnArgValue;
  private static final int penStatusValue = 0;
  private boolean isPenDown = false;
  private static final int argumentsNeeded = 0;
  private CommandDatabase database;

  public PenUp(CommandDatabase data) {
    super(data);
    database = data;

  }

  /**
   * Makes the pen of a turtle go up
   */
  @Override
  public Integer executeAndReturnValue() {
    turtleObject = database.getTurtle();
    returnArgValue = penStatusValue;

    turtleObject.setPenStatus(isPenDown);
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



