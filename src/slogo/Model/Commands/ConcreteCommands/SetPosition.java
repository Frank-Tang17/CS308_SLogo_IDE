package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;
import slogo.Model.TurtleData;

/**
 * Subclass to create a LeftCommand
 *
 * @author Frank Tang
 */
public class SetPosition extends Command {


  //moves turtle to an absolute screen position, where (0, 0) is the center of the screen
  //returns the distance turtle moved

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
   * Rotates a turtle by a counterclockwise rotation of a degree amount.
   */
  @Override
  public Double executeAndReturnValue() {
    turtleObject = database.getTurtle();
    newX = database.getParameterStack().pop();
    newY = database.getParameterStack().pop();
    double differenceX = newX.doubleValue() - turtleObject.getTurtleX();
    double differenceY = newY.doubleValue() - turtleObject.getTurtleY();

    returnArgValue = Math.sqrt(differenceX * differenceX + differenceY * differenceY);
    System.out.println("turtleX before " + turtleObject.getTurtleX());
    System.out.println("turtleY before " + turtleObject.getTurtleY());
    turtleObject.setXCoord(newX.doubleValue());
    turtleObject.setYCoord(newY.doubleValue());
    turtleObject.addCoord(turtleObject.getTurtleX(), turtleObject.getTurtleY());
    System.out.println("turtleX after " + turtleObject.getTurtleX());
    System.out.println("turtleY after " + turtleObject.getTurtleY());
    System.out.println("return " + returnArgValue);
    //System.out.println(returnArgValue);
//    System.out.println("turtle Y " + turtleObject.getTurtleY());
    return this.returnArgValue;

  }
  @Override
  public int getArgumentsNeeded(){
    return this.argumentsNeeded;
  }


}



