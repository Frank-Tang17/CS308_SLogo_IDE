package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;
import slogo.Model.TurtleData;

/**
 * Subclass to create a Heading Command
 *
 * @author Frank Tang
 */
public class Heading extends Command {

  private double returnArgValue;
  private static final int argumentsNeeded = 0;
  private CommandDatabase database;
  private TurtleData turtleObject;


  public Heading(CommandDatabase data) {
    super(data);
    database = data;

  }

  /**
   * Returns the turtle's current heading
   */
  @Override
  public Double executeAndReturnValue() {
    turtleObject = database.getTurtle();
    returnArgValue = turtleObject.getTurtleHeading();
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


