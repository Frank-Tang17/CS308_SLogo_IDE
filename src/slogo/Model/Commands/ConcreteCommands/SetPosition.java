package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;
import slogo.Model.TurtleData;

/**
 * Subclass to create a SetPosition Command
 *
 * @author Frank Tang
 */
public class SetPosition extends Command {


  private TurtleData turtleObject;
  private double returnArgValue;
  private Number newX;
  private Number newY;
  private static final int argumentsNeeded = 2;
  private CommandDatabase database;


  public SetPosition(CommandDatabase data) {
    super(data);
    database = data;


  }

  /**
   * Moves the turtle to a new location and returns the distance traveled.
   */
  @Override
  public Double executeAndReturnValue() {
    turtleObject = database.getTurtle();
    newX = database.getParameterStack().pop();
    newY = database.getParameterStack().pop();
    double differenceX = newX.doubleValue() - turtleObject.getTurtleX();
    double differenceY = newY.doubleValue() - turtleObject.getTurtleY();

    returnArgValue = Math.sqrt(differenceX * differenceX + differenceY * differenceY);
    turtleObject.setXCoord(newX.doubleValue());
    turtleObject.setYCoord(newY.doubleValue());
    turtleObject.addCoord(turtleObject.getTurtleX(), turtleObject.getTurtleY());
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



