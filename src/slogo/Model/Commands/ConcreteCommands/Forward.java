package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;
import slogo.Model.TurtleData;

/**
 * Subclass to create a Forward Command
 *
 * @author Frank Tang
 */
public class Forward extends Command {

  private CommandDatabase database;
  private static final int argumentsNeeded = 1;

  public Forward(CommandDatabase data) {
    super(data);
    database = data;

  }

  /**
   * Moves the turtle forward by a pixel amount.
   */
  @Override
  public Double executeAndReturnValue() {
    TurtleData turtleObject = database.getTurtle();
    Number distanceToTravel = database.getParameterStack().pop();
    double turtleHeading = turtleObject.getTurtleHeading();

    double distanceProportionY = Math.sin(Math.toRadians(turtleHeading));
    double distanceProportionX = Math.cos(Math.toRadians(turtleHeading));
    turtleObject.moveXCoord(distanceToTravel.doubleValue() * distanceProportionX);
    turtleObject.moveYCoord(distanceToTravel.doubleValue() * distanceProportionY);

    turtleObject.addCoord(turtleObject.getTurtleX(), turtleObject.getTurtleY());
    double returnArgValue = distanceToTravel.doubleValue();
    return returnArgValue;

  }

  /**
   * Returns the amount of arguments that this command needs before it can be made
   */
  @Override
  public int getArgumentsNeeded() {
    return this.argumentsNeeded;
  }
}


