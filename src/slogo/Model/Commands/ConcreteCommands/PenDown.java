package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;
import slogo.Model.TurtleData;

/**
 * Subclass to create a PenDown Command
 *
 * @author Frank Tang
 */
public class PenDown extends Command {

  private TurtleData turtleObject;
  private double returnArgValue;
  private static final int penStatusValue = 1;
  private boolean isPenDown = true;
  private static final int argumentsNeeded = 0;
  private CommandDatabase database;

  public PenDown(CommandDatabase data) {
    super(data);
    database = data;


  }

  /**
   * Makes the pen of a turtle go down
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
  public int getArgumentsNeeded() {
    return this.argumentsNeeded;
  }


}



