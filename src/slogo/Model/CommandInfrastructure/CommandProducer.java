package slogo.Model.CommandInfrastructure;

import java.util.Stack;
import javafx.beans.property.ListProperty;
import slogo.Model.Commands.Command;
import slogo.Model.TurtleData;

public class CommandProducer {

  /**
   * Simple parser based on regular expressions that matches input strings to kinds of program elements.
   * @author Frank Tang
   */

  private CommandDatabase commandDatabase;
  private int argumentRunningTotal;
  private String newCommandEntry;
  private String argumentEntries;
  private ListProperty<String> HISTORY_LIST;
  private Number currentCommandReturnValue;
  private Command newCommand;
  private static final String BLANK_SPACE = " ";
  private static final String BLANK = "";


  public CommandProducer(CommandDatabase database, ListProperty<String> stringHistory){
    HISTORY_LIST = stringHistory;
    commandDatabase = database;
  }

  /**
   * Adds the given resource file to this language's recognized types
   */
  public Number parseStacks (Stack<String> commStack, Stack<Number> argStack, int argumentThreshold) {
    argumentRunningTotal = argumentThreshold;
//    checkStackSizesandRefresh(commStack, argStack);
//    System.out.println(argumentRunningTotal);
    while (commStack.size() > 0 && argStack.size() >= argumentRunningTotal){
      System.out.println("BeforeA" + argStack);
      System.out.println("BeforeC" + commStack);
      newCommand = makeCommand(commStack.peek());
      int parametersNeeded = newCommand.getArgumentsNeeded();
      newCommandEntry = commStack.peek();
      argumentEntries = BLANK;
      for (int i = 0; i < parametersNeeded; i++){
        commandDatabase.getParameterStack().push(argStack.peek());
        argumentEntries = BLANK_SPACE + argStack.pop().toString() + argumentEntries;
      }
      newCommandEntry = newCommandEntry + argumentEntries;
      HISTORY_LIST.getValue().add(newCommandEntry);
//      for(TurtleData turtle: commandDatabase.getTurtleList()){
//        if(turtle.getTurtleActive() == true) {
//          commandDatabase.setActiveTurtle(turtle);
//          newCommand = makeCommand(commStack.peek());
//          currentCommandReturnValue = newCommand.executeAndReturnValue();
//        }
//      }
      commandDatabase.setActiveTurtle(commandDatabase.getTurtle());
      newCommand = makeCommand(commStack.peek());
      currentCommandReturnValue = newCommand.executeAndReturnValue();
      commStack.pop();
      argumentRunningTotal--;
      if(commStack.size() == 0){
        break;
      }
      else if(argStack.size() <= argumentRunningTotal || argStack.size() == 0){
         argStack.push(currentCommandReturnValue);
      }

    }

    return currentCommandReturnValue;
  }



  public Command makeCommand(String commandName){
    try {

      Class commandClass = Class.forName("slogo.Model.Commands.ConcreteCommands." + commandName);
      Command command = (Command) commandClass.getConstructors()[0].newInstance(commandDatabase);
      return command;
    }
    catch (Exception e){
      e.printStackTrace();
      // TODO: FIX THIS SO WE DON'T FAIL
    }
    return null;
  }

}
