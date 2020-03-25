package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;
import slogo.Model.TurtleData;

/**
 * Subclass to create a ShowTurtle Command
 *
 * @author Frank Tang
 */
public class ShowTurtle extends Command {


  private TurtleData turtleObject;
  private double returnArgValue;
  private static final int turtleVisibilityStatus = 1;
  private boolean isTurtleVisible = true;
  private static final int argumentsNeeded = 0;
  private CommandDatabase database;


  public ShowTurtle(CommandDatabase data) {
    super(data);
    database = data;


  }

  /**
   * Rotates a turtle by a counterclockwise rotation of a degree amount.
   */
  @Override
  public Integer executeAndReturnValue() {
    turtleObject = database.getTurtle();
    returnArgValue = turtleVisibilityStatus;
    turtleObject.setTurtleVisibility(isTurtleVisible);
    System.out.println("return " + returnArgValue);
    //System.out.println(returnArgValue);
//    System.out.println("turtle Y " + turtleObject.getTurtleY());
    return (int) this.returnArgValue;
  }
  /**
   * Returns the amount of arguments that this command needs before it can be made
   */
  @Override
  public int getArgumentsNeeded(){
    return this.argumentsNeeded;
  }


}



