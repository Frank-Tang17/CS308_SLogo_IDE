//package slogo.Model.Commands.ConcreteCommands;
//
//import java.util.List;
//import slogo.Model.CommandInfrastructure.CommandDatabase;
//import slogo.Model.Commands.Command;
//import slogo.Model.ModelParser;
//
///**
// * Subclass to create a BackCommand
// *
// * @author Frank Tang
// */
//public class DoTimes extends Command {
//
//  private double returnArgValue = 0;
//  private List<String> linesSubArray;
//  private List<String> currentSubList;
//  private List<String> commandSubList;
//  private int currentIndex;
//  private Number variableLimit;
//  private String variableName;
//  private ModelParser parser;
//  private CommandDatabase commandDatabase;
//
//  private static final int argumentsNeeded = 0;
//  private CommandDatabase database;
//
//
//
//  public DoTimes(CommandDatabase data) {
//    super(data);
//    database = data;
//    parser = modelParser;
//    commandDatabase = database;
//
//  }
//
//  /**
//   * Moves the turtle backwards by a pixel amount.
//   */
//  @Override
//  public Double executeAndReturnValue() {
//    currentIndex = parser.getCurrentLinesIndex();
////    System.out.println("current index " + currentIndex);
//    linesSubArray = parser.getLinesArray();
////    System.out.println("BigArray " + linesSubArray);
//
//    currentSubList = linesSubArray.subList(currentIndex + 1, linesSubArray.size());
////    System.out.println("currentSublist " + currentSubList);
//    currentSubList = currentSubList.subList(currentSubList.indexOf("["), currentSubList.size());
//    int listStart = currentSubList.indexOf("[");
////    System.out.println("listStart" + listStart);
//    int listEnd = parser.findListEnd(currentSubList) + listStart;
//    commandSubList = currentSubList.subList(listEnd + 1, currentSubList.size());
//    linesSubArray = currentSubList.subList(listStart + 1, listEnd);
//    System.out.println("test" + linesSubArray);
//
//    variableName = linesSubArray.get(0);
//    variableLimit = Double.parseDouble(linesSubArray.get(1));
//
//    int commandListEnd = parser.findListEnd(commandSubList);
//    commandSubList = commandSubList.subList(1, commandListEnd);
//    System.out.println("Commandsublist " + commandSubList);
//
//    for(int i = 1; i <= variableLimit.doubleValue(); i++){
//      commandDatabase.addToVariableMap(variableName, i);
//      parser.parseText(commandSubList);
//    }
//
//    return this.returnArgValue;
//  }
//  @Override
//  public int getArgumentsNeeded(){
//    return this.argumentsNeeded;
//  }
//
//
//}
//
//