package slogo.Model.Commands.ConcreteCommands;

import java.util.ArrayList;
import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

public class SetBackground extends Command {
    private CommandDatabase database;
    private static final int argumentsNeeded = 1;
    private Number index;

    /**
     * Superconstructor for a Turtle Command
     *
     * @param data
     */
    public SetBackground(CommandDatabase data) {
        super(data);
        database = data;
    }

    @Override
    public Number executeAndReturnValue() {
        index = database.getParameterStack().pop();
        ArrayList<Integer> color = (ArrayList<Integer>) database.getColorMap().get(index.intValue());
        database.setBackgroundColor(color);
        return index;
    }
  /**
   * Returns the amount of arguments that this command needs before it can be made
   */
    @Override
    public int getArgumentsNeeded() {
        return this.argumentsNeeded;
    }
}
