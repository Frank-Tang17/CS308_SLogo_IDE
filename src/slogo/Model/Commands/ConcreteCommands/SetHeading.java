package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;
import slogo.Model.TurtleData;

/**
 * Subclass to create a SetHeading Command
 *
 * @author Frank Tang
 */
public class SetHeading extends Command {

  private TurtleData turtleObject;
  private double returnArgValue;
  private double originalTurtleDirection;
  private Number newTurtleDirection;
  private static final int fullRevolution = 360;
  private static final int argumentsNeeded = 1;
  private CommandDatabase database;


  public SetHeading(CommandDatabase data) {
    super(data);
    database = data;

  }

  /**
   * Sets the turtle heading to the parameter given and returns the amount of degrees in angle change
   */
  @Override
  public Double executeAndReturnValue() {
    turtleObject = database.getTurtle();
    originalTurtleDirection = turtleObject.getTurtleHeading();
    newTurtleDirection = database.getParameterStack().pop();
    returnArgValue = newTurtleDirection.doubleValue() - originalTurtleDirection;
    turtleObject.setTurtleDirection(newTurtleDirection.doubleValue());
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


