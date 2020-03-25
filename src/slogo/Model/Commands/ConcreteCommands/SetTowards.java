package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;
import slogo.Model.TurtleData;

/**
 * Subclass to create a SetTowards Command
 *
 * @author Frank Tang
 */
public class SetTowards extends Command {


  private TurtleData turtleObject;
  private double returnArgValue;
  private double originalTurtleDirection;
  private double newTurtleDirection;
  private Number towardsX;
  private Number towardsY;
  private static final int HALF_REVOLUTION = 180;
  private static final int argumentsNeeded = 2;
  private CommandDatabase database;

  private static final int ZERO = 0;

  public SetTowards(CommandDatabase data) {
    super(data);
    database = data;


  }

  /**
   * Sets the new turtle heading towards the given coordinate from the parameters
   */
  @Override
  public Double executeAndReturnValue() {
    turtleObject = database.getTurtle();
    towardsX = database.getParameterStack().pop();
    towardsY = database.getParameterStack().pop();
    double differenceX = towardsX.doubleValue() - turtleObject.getTurtleX();
    double differenceY = towardsY.doubleValue() - turtleObject.getTurtleY();
    double towardsAngle = Math.toDegrees(Math.atan(differenceY / differenceX));

    if (differenceX < ZERO) {
      newTurtleDirection = HALF_REVOLUTION + towardsAngle;
    } else {
      newTurtleDirection = towardsAngle;
    }
    originalTurtleDirection = turtleObject.getTurtleHeading();
    returnArgValue = newTurtleDirection - originalTurtleDirection;
    turtleObject.setTurtleDirection(newTurtleDirection);
    turtleObject.addCoord(turtleObject.getTurtleX(), turtleObject.getTurtleY());
    return this.returnArgValue;

  }

  /**
   * Returns the amount of arguments that this command needs before it can be made
   */
  @Override
  public int getArgumentsNeeded() {
    return this.argumentsNeeded;
  }


}


