package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

/**
 * Subclass to create a Turtles Command
 *
 * @author Frank Tang
 */
public class Turtles extends Command {

  private static final int argumentsNeeded = 0;
  private CommandDatabase database;


  public Turtles(CommandDatabase data) {
    super(data);
    database = data;


  }

  /**
   * Returns the size of the turtles list and prints out a console statement
   */
  @Override
  public Integer executeAndReturnValue() {
    System.out.println("Turtles made: " + database.getTurtleList().size());
    return database.getTurtleList().size();
  }

  /**
   * Returns the amount of arguments that this command needs before it can be made
   */
  @Override
  public int getArgumentsNeeded() {
    return this.argumentsNeeded;
  }
}



