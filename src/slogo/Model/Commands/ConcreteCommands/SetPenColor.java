package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

import java.util.ArrayList;

public class SetPenColor extends Command {

    private CommandDatabase database;
    private static final int argumentsNeeded = 1;
    private int index;

    /**
     * Superconstructor for a Turtle Command
     *
     * @param data
     */
    public SetPenColor(CommandDatabase data) {
        super(data);
        database = data;
    }

    @Override
    public Number executeAndReturnValue() {
        index = database.getParameterStack().pop().intValue();
        ArrayList<Integer> color = (ArrayList<Integer>) database.getColorMap().get(index);
        database.getTurtle().setPenColor(color);
        return index;
    }

    @Override
    public int getArgumentsNeeded() {
        return this.argumentsNeeded;
    }
}
