package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;
import slogo.Model.TurtleData;

/**
 * Subclass to create an IsPenDown Command
 *
 * @author Frank Tang
 */
public class IsPenDown extends Command {

  private double returnArgValue;
  private static final int argumentsNeeded = 0;
  private CommandDatabase database;
  private TurtleData turtleObject;

  public IsPenDown(CommandDatabase data) {
    super(data);
    database = data;

  }

  /**
   * Returns the status of the pen; 1 if down, 0 if up
   */
  @Override
  public Integer executeAndReturnValue() {
    turtleObject = database.getTurtle();
    returnArgValue = turtleObject.getPenStatus();
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


