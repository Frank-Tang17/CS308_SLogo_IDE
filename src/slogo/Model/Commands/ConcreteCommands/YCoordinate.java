package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;
import slogo.Model.TurtleData;

/**
 * Subclass to create a YCoordinate Command
 *
 * @author Frank Tang
 */
public class YCoordinate extends Command {


  private double returnArgValue;
  private static final int argumentsNeeded = 1;
  private CommandDatabase database;
  private TurtleData turtleObject;

  public YCoordinate(CommandDatabase data) {
    super(data);
    database = data;


  }

  /**
   * Returns the value of the turtles Y-coordinate
   */
  @Override
  public Double executeAndReturnValue() {
    turtleObject = database.getTurtle();
    returnArgValue = turtleObject.getTurtleY();

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


