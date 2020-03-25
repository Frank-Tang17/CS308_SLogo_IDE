package slogo.Model.Commands.ConcreteCommands;

import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.Commands.Command;

import java.util.ArrayList;
import java.util.List;

public class SetPalette extends Command {

    private CommandDatabase database;
    private static final int argumentsNeeded = 4;
    private int index;
    private List<Integer> rgb;
    /**
     * Superconstructor for a Turtle Command
     *
     * @param data
     */
    public SetPalette(CommandDatabase data) {
        super(data);
        database = data;
        rgb = new ArrayList<>();
    }

    @Override
    public Number executeAndReturnValue() {
        index = database.getParameterStack().pop().intValue();
        rgb.add(database.getParameterStack().pop().intValue());
        rgb.add(database.getParameterStack().pop().intValue());
        rgb.add(database.getParameterStack().pop().intValue());

        System.out.println(rgb.get(0) + " " + rgb.get(1) + " " + rgb.get(2));

        database.addToColorMap(index, rgb);

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
