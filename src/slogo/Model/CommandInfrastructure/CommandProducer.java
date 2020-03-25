package slogo.Model.CommandInfrastructure;

import java.util.Stack;

import javafx.beans.property.ListProperty;
import slogo.DisplayError;
import slogo.Model.Commands.Command;

public class CommandProducer {

  /**
   * CommandProducer class whose job is to parse the given stacks and determines whether or not commands can be made with the current stacks
   *
   * @author Frank Tang
   */

  private CommandDatabase commandDatabase;
  private int argumentRunningTotal;
  private ListProperty<String> HISTORY_LIST;
  private Number currentCommandReturnValue;
  private Command newCommand;
  private static final int ZERO = 0;
  private static final String SPACE = " ";
  private static final String BLANK = "";
  private static final String CommandCreationError = "CommandCreationError";
  private static final String CONCRETE_COMMAND_CLASS = "slogo.Model.Commands.ConcreteCommands.";



  public CommandProducer(CommandDatabase database, ListProperty<String> stringHistory) {
    HISTORY_LIST = stringHistory;
    commandDatabase = database;
  }

  /**
   * Parses the stacks given by the ModelParser and creates whatever commands are possible from the stacks
   */
  public Number parseStacks(Stack<String> commStack, Stack<Number> argStack, int argumentThreshold) {
    argumentRunningTotal = argumentThreshold;
    while (commStack.size() > 0 && argStack.size() >= argumentRunningTotal) {
      newCommand = makeCommand(commStack.peek());
      String newCommandEntry = commStack.peek();
      String argumentEntries = BLANK;
      for (int i = ZERO; i < newCommand.getArgumentsNeeded(); i++) {
        commandDatabase.getParameterStack().push(argStack.peek());
        argumentEntries = SPACE + argStack.pop().toString() + argumentEntries;
      }
      newCommandEntry = newCommandEntry + argumentEntries;
      HISTORY_LIST.getValue().add(newCommandEntry);
      
      newCommand = makeCommand(commStack.pop());
      currentCommandReturnValue = newCommand.executeAndReturnValue();

      argumentRunningTotal--;
      if (commStack.size() == ZERO) {
        break;
      }
      else if (argStack.size() <= argumentRunningTotal || argStack.size() == ZERO) {
        argStack.push(currentCommandReturnValue);
      }
    }
    return currentCommandReturnValue;
  }

  private Command makeCommand(String commandName) {
    try {
      Class commandClass = Class.forName(CONCRETE_COMMAND_CLASS + commandName);
      Command command = (Command) commandClass.getConstructors()[ZERO].newInstance(commandDatabase);
      return command;
    } catch (Exception e) {
      new DisplayError(CommandCreationError);
    }
    return null;
  }
}
