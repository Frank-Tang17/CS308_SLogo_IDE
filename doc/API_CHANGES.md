# API_CHANGES
## Authors: Benjamin Lu (bll32), Eric Doppelt (ead45), Erik Gregorio (eg158), Frank Tang (ft39)

### Changes that have been made to the APIs

#### Overview:
As an overview of the API changes that we made, there were several changes pertaining to the External API of the Backend of our project, mainly to the Controller class. This is because in the end, we decided to use bindings to update the information in the view so that any change in the Model's data will reflect in a change in the view's appearance. Everything else, however, has stayed relatively the same, as we are communicating via a CommandBox to send information to the ModelParser (FrontEnd External), communicating in the Model between the CommandDatabase, Commands, and the ModelParser (Backend Internal), and communicating in the FrontEnd Internal between the TurtleView and TurtlePopup classes to show the turtle and its popup menu. Overall though, we have a lot of changes we need to add to the APIs because we were not aware what should be added to the APIs. As a result, we need to have to add all the public methods we have developed in the time of making this project.

#### Frontend Internal

The Frontend Internal changed mostly due to the implementation of binding. This was not in our original plan; however, once we learned more about the concept, we realized that it would simplify our project significantly.

Specifically, the following methods have been affected by this change:

``` java
    public void updateBackgroundColor(Color color);
	public void updatePenColor();
	public void updateTurtleImage();
    public void updateTurtle();
```

None of these methods are still public, since the updates are used via binding. As such, there is no need to tell the View to update this information; instead, the Bindings updates automatically. These changes are major, but they are beneficial and purposeful.

Additionally, we are no longer calling the following methods:
``` java
    public void displayTurtle();
    public void displayGrid();
	public void displayInfo();
```

Instead, we use a variety of getter methods that return a Node to be added to a scene. displayInfo() for example, is now replaced by adding the node of an InfoView class to the display. This allows us to to create the visualization by adding various components to the scene in SlogoView, centralizing the display in one class (while also using composition). This change is also better, since the app is centralized well in one location now.

#### Frontend External
CommandBox lost a most of its public methods. As the team moved towards binding as our form of communication, the CommandBox became much more active. Instead of having other sources request commands from CommandBox via:
``` java
    public void getCommand();
```
We moved towards having CommandBox deciding when to pass the those commands to the ModelParser. Because of binding, we no longer needed to return/send a command from the backend to the frontend. Therefore, there was no need for: 
``` java
    public void sendCommand();
```
Since the CommandBox now choose when to send the command string to the back end, the backend move from telling the frontend to display an error via:
``` java
    public void throwException(Exception E);
```
to throwing exceptions when passing the string. Therefore, this method moved from public to private.

#### Backend Internal
The amount of methods in this API was increased significantly from the initial amount of methods thought of in the planning period because we neglected to think about various relationships that occur in the model, such as getting the data from a Turtle in order to change it. As such, there are a lot of added methods like getters to make the commands work appropriately with the turtle data.
 
``` java
public class VariableData { 
    public VariableData()
    public Map<String, Integer> getVariableMap() 
    }
```
This class represents the Variable map that needed to be made to hold variables that a user can set. The method getVariableMap() allows the map to be passed around so that we can access the variables' values in classes like the ModelParser.

 ```Java
public class ModelDatabase { 
  public ModelDatabase() {}
  public TurtleData getMyTurtle() {}
  public String getCommand()  {}
}
 ```
 This class represents the base for where all the data is stored for things like the Turtles. In this class, we have the following methods:
 1. public TurtleData getMyTurtle() - This method gets the turtle in the database
 2. public String getCommand() - This method gets the command property from the CommandBox


 ```Java
public class ModelParser { 
  public ModelParser(String language, CommandDatabase commandData){}
  public Property getParserLanguageProperty() {}
  public int findListEnd(List<String> listToCheck){}
  public void parseText (List<String> lines) {}
}
 ```
 1. public Property getParserLanguageProperty() - This method gets the property for the language that is being set to the Parser so that it can parse the correct regular expressions
 2. public int findListEnd(List<String> listToCheck) - This method gets the index of the end of a bracketed list command so that the parser can skip across the commands in the list
 3. public void parseText (List<String> lines) - This method parses through a string from the Command Box to send stacks to the CommandProducer. This method is the starting point for making any commands from an input String array, so by using this method as a Function or Consumer, the end result will be commands being made and executed from the input String array. 

The public List<String> getLinesArray(){} method was removed by myself because I was able to find a better way to get the remaining String array to commands that need it via the CommandDatabase
 ```Java
public class CommandProducer { 
  public CommandProducer(CommandDatabase database){}
  public void parseStacks (Stack commStack, Stack argStack, int argumentThreshold){} 
}
```
1. public void parseStacks (Stack commStack, Stack argStack, int argumentThreshold) - This method parses through the stacks given as a parameter from the ModelParser. Makes and executes the commands from the stacks given.

```Java
public class CommandDatabase { 
  public CommandDatabase(TurtleData turtle){}
    public List<TurtleData> getTurtleList() {}
    public void setTurtleList(List<TurtleData> newTurtleList) {}
    public void setActiveTurtle(TurtleData activeTurtle) {}
    public TurtleData getTurtle() {}
    public Stack<Number> getParameterStack() {}
    public void setListArray(List<String> array) {}
    public List<String> getCurrentLineArray() {}     
    public Function<List<String>, Number> getListFunction() {}
    public void addParser(ModelParser parser) {}
    public void setVariableName(String targetCommand) {}
    public MapProperty getVariableMap() {}
    public String getVariableName() {}
    public void addToVariableMap(String command, Number expression) {}
    public void addToCommandMap(String command, String commandLine) {}
    public MapProperty<String, String> getCOMMAND_LIST() {}


}
```

1. public List<TurtleData> getTurtleList() {} - Gets the list of all turtles present in program
2. public void setTurtleList(List<TurtleData> newTurtleList) {} - Sets the list of all possible turtles
3. public void setActiveTurtle(TurtleData activeTurtle) {} - Sets the active turtle for implementation of multiple turtles
4. public TurtleData getTurtle() {} - Returns the TurtleData object of a turtle
5. public Stack<Number> getParameterStack() {} - Returns the current stack of parameters set by the CommandProducer
6. public void setListArray(List<String> array) {} - Sets the current String array being parsed into the database
7. public List<String> getCurrentLineArray() {} - Returns the current String array being parsed
8. public Function<List<String>, Number> getListFunction() {} - Returns the Function for the findListEnd method
9. public void addParser(ModelParser parser) {} - Adds the two parser function of parsing text and finding the index of the end of a list to the database
10. public void setVariableName(String targetCommand) {} - Sets the variable name in the database to the most recently called variable name
11. public MapProperty getVariableMap() {} - Returns the map of variables and their values
12. public String getVariableName() {} - Returns the last variable name input into the database
13. public void addToVariableMap(String command, Number expression) {} - Adds an entry into the variable map
14. public void addToCommandMap(String command, String commandLine) {} - Adds an entry into the command map
15. public MapProperty<String, String> getCOMMAND_LIST() {} - Returns the command map

These API changes were made by the backend team to try to implement new turtles and other functionality. I added a Function for the findListEnd method so that I could
use it in commands so that special commands could doctor up input strings so that the parser could have a less complex parsing algorithm. Some of these API changes
were requested by the frontend so that they could also add functionality to their end, such as the binding the variable and command maps to the frontend for the scroll panes.
 
            



```Java
public abstract class Command {
  public Number executeAndReturnValue(){}
  public int getArgumentsNeeded(){}
}
```
1. public Number executeAndReturnValue(){} - This method is used in every command to execute a specific task and return a value for each command.
2. public int getArgumentsNeeded(){} - This method returns the amount of arguments that a commands needs to be made. The command returns tell this number to the CommandProducer

These changes in the Commands were made by me because we changed the structure of our command creation to use reflection instead of a factory. This consolidated information specific to each command in the subclasses themselves and
allowed for a universal method getArgumentsNeeded(){} that is used whenever we are making a new command.

#### Backend External

Most of these APIs changes are making the bindings for binding the turtleView class to the data that is found in the Model. 

```Java
public class CommandDatabase { 
  public CommandDatabase(TurtleData turtle){}
      public void setPenColor(List<Integer> rgbList) {}
      public void bindPenColor(Property viewBackground) {}
      public void setBackgroundColor(List<Integer> rgbList) {}
      public void bindBackgroundColor(Property viewBackground) {}
      public Function<List<String>, Number> getParseFunction() {}
      public void bindVariables(MapProperty displayedVariables) {}
      public void bindCommands(MapProperty displayedCommands) {}
      public void addToColorMap(int index, List<Integer> color) {}
      public void bindColors(MapProperty viewColors) {}
      public MapProperty getColorMap() {}
}
     
```
1. public void setPenColor(List<Integer> rgbList) {} - Sets the pen color based on an RGB value
2. public void bindPenColor(Property viewBackground) {} - Binds the pen color data to the front end
3. public void setBackgroundColor(List<Integer> rgbList) {} - Sets the background color via an RGB value
4. public void bindBackgroundColor(Property viewBackground) {} - Binds the background color data to the frontend
5. public Function<List<String>, Number> getParseFunction() {} - Returns the Function for the parseText method
6. public void bindVariables(MapProperty displayedVariables) {} - Binds the variable map to the MapProperty
7. public void bindCommands(MapProperty displayedCommands) {} - Binds the Command Map to the Map Property to be displayed in the front end
8. public void addToColorMap(int index, List<Integer> color) {} - Adds to the color map
9. public void bindColors(MapProperty viewColors) {} - Binds the Color Map to the MapProperty
10. public MapProperty getColorMap() {} - Returns the color map

These methods bind data from the backend to the frontend to be displayed on things like the Scrollpanes and the popups. I chose to make a Function for the parseText() method so that the frontend
and the backend can utilize the ability to create commands from a input string.
