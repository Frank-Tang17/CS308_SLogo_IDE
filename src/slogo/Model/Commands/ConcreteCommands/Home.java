package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;
import slogo.Model.TurtleData;

/**
 * Subclass to create a Home Command
 *
 * @author Frank Tang
 */
public class Home extends Command {

  private TurtleData turtleObject;
  private double returnArgValue;
  private static final double zeroX = 0;
  private static final double zeroY = 0;
  private static final int argumentsNeeded = 0;
  private CommandDatabase database;

  public Home(CommandDatabase data) {
    super(data);
    database = data;

  }

  /**
   * Moves the turtle to (0,0) and returns the distance travelled
   */
  @Override
  public Double executeAndReturnValue() {
    turtleObject = database.getTurtle();
    double differenceX = zeroX - turtleObject.getTurtleX();
    double differenceY = zeroY - turtleObject.getTurtleY();

    returnArgValue = Math.sqrt(differenceX * differenceX + differenceY * differenceY);

    turtleObject.setXCoord(zeroX);
    turtleObject.setYCoord(zeroY);
    turtleObject.addCoord(zeroX, zeroY);
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



