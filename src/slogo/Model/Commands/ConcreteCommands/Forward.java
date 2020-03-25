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

  private TurtleData turtleObject;
  private double returnArgValue;
  private double turtleHeading;
  private Number distanceToTravel;
  private CommandDatabase database;


  private double distanceProportionY;
  private double distanceProportionX;
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
    turtleObject = database.getTurtle();
    distanceToTravel = database.getParameterStack().pop();
    turtleHeading = turtleObject.getTurtleHeading();

    distanceProportionY = Math.sin(Math.toRadians(turtleHeading));
    distanceProportionX = Math.cos(Math.toRadians(turtleHeading));
    turtleObject.moveXCoord(distanceToTravel.doubleValue() * distanceProportionX);
    turtleObject.moveYCoord(distanceToTravel.doubleValue() * distanceProportionY);

    turtleObject.addCoord(turtleObject.getTurtleX(), turtleObject.getTurtleY());
    returnArgValue = distanceToTravel.doubleValue();
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


