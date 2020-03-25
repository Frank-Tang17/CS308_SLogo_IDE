package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;
import slogo.Model.TurtleData;

/**
 * Subclass to create a XCoordinate Command
 *
 * @author Frank Tang
 */
public class XCoordinate extends Command {


  private double returnArgValue;
  private static final int argumentsNeeded = 0;
  private CommandDatabase database;
  private TurtleData turtleObject;



  public XCoordinate(CommandDatabase data) {
    super(data);
    database = data;

  }

  /**
   * Returns the value of the turtles x-coordinate
   */
  @Override
  public Double executeAndReturnValue() {
    turtleObject = database.getTurtle();
    returnArgValue = turtleObject.getTurtleX();
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


