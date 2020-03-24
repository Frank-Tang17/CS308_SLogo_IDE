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
    turtleObject = database.getTurtle();
  }

  /**
   * Moves the turtle forward by a pixel amount.
   */
  @Override
  public Double executeAndReturnValue() {
    distanceToTravel = database.getParameterStack().pop();
    turtleHeading = turtleObject.getTurtleHeading();

    distanceProportionY = Math.sin(Math.toRadians(turtleHeading));
    distanceProportionX = Math.cos(Math.toRadians(turtleHeading));
    turtleObject.moveXCoord(distanceToTravel.doubleValue() * distanceProportionX);
    turtleObject.moveYCoord(distanceToTravel.doubleValue() * distanceProportionY);

    // TODO THIS IS TESTER CODE
    turtleObject.addCoord(turtleObject.getTurtleX(), turtleObject.getTurtleY());
    System.out.println("turtle ID "+ turtleObject.getTurtleID());
    System.out.println("turtle X " + turtleObject.getTurtleX());
    System.out.println("turtle Y " + turtleObject.getTurtleY());
    returnArgValue = distanceToTravel.doubleValue();
    return this.returnArgValue;

  }

  @Override
  public int getArgumentsNeeded(){
    return this.argumentsNeeded;
  }


}


