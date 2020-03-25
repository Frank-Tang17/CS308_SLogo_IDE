package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;
import slogo.Model.TurtleData;

/**
 * Subclass to create a Right Command
 *
 * @author Frank Tang
 */
public class Right extends Command {

  private TurtleData turtleObject;
  private double returnArgValue;
  private Number degreesChanged;
  private static final int argumentsNeeded = 1;
  private CommandDatabase database;


  public Right(CommandDatabase data) {
    super(data);
    database = data;

  }

  /**
   * Rotates a turtle by a clockwise rotation of a degree amount.
   */
  @Override
  public Double executeAndReturnValue() {
    turtleObject = database.getTurtle();
    degreesChanged = database.getParameterStack().pop();
    returnArgValue = degreesChanged.doubleValue();
    turtleObject.rotateTurtleHeading(degreesChanged.doubleValue());
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


