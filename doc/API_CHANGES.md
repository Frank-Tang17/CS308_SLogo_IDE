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
  public List<String> getLinesArray(){}
}
 ```
 1. public Property getParserLanguageProperty() - This method gets the property for the language that is being set to the Parser so that it can parse the correct regular expressions
 2. public int findListEnd(List<String> listToCheck) - This method gets the index of the end of a bracketed list command so that the parser can skip across the commands in the list
 3. public void parseText (List<String> lines) - This method parses through a string from the Command Box to send stacks to the CommandProducer
 4. public List<String> getLinesArray() - returns the shortened line array to be used in commands like the repeat command


 ```Java
public class CommandProducer { 
  public CommandProducer(CommandDatabase database){}
  public void parseStacks (Stack commStack, Stack argStack, int argumentThreshold){} 
}
```
1. public void parseStacks (Stack commStack, Stack argStack, int argumentThreshold) - This method parses through the stacks given as a parameter from the ModelParser

```Java
public class CommandDatabase { 
  public CommandDatabase(TurtleData turtle){}
  public void addParser (ModelParser parser){} 
  public Command makeZeroParameterCommand (String targetCommand) {}
  public void setVariableName(String targetCommand) {}
  public Command makeOneParameterCommand (String targetCommand, Number value) {}
  public Command makeTwoParameterCommand (String targetCommand, Number value1, Number value2) {}
  public Integer getAmountOfParametersNeeded(String targetCommand) {}
  public boolean isInCommandMap(String targetCommand) {}
  public MapProperty getVariables() {}
  public void addToHistory(String command) {}
  public void addToVariables(String command, Number expression) {}
}
```
1. public void addParser (ModelParser parser) - This method will add the parser to the CommandDatabase. This method will be changed to reduce the dependencies of the project. 
2. public Command makeZeroParameterCommand (String targetCommand) - This method will result making the CommandProducer to make a zero parameter command.
3. public void setVariableName(String targetCommand) - This method will set the variable name in the CommandDatabase
4. public Command makeOneParameterCommand (String targetCommand, Number value) - This method will result making the CommandProducer to make a one parameter command.
5. public Command makeTwoParameterCommand (String targetCommand, Number value1, Number value2) - This method will result making the CommandProducer to make a two parameter command.
6. public Integer getAmountOfParametersNeeded(String targetCommand) - returns the amount of parameters a command needs
7. public boolean isInCommandMap(String targetCommand) - returns whether or not the regular expression is found in the command map 
8. public MapProperty getVariables() - gets the map property to be displayed in the view
9. public void addToVariables(String command, Number expression) - adds to the variable map

```Java
public abstract class CommandDatabase {
  public Number executeAndReturnValue(){}
}
```
1. This method is used in every command to execute a specific task and return a value for each command.

#### Backend External

Most of these APIs changes are making the bindings for binding the turtleView class to the data that is found in the Model. 

```Java
  public void bindHistory(ListProperty displayedHistory){}
  public void bindCommands(MapProperty displayedCommands){}
  public void bindVariables(MapProperty displayedVariables){}
  public void bindCommands(MapProperty displayedCommands){} 
  public void bindVariables(MapProperty displayedVariables){} 
```
These methods bind data from the backend to the frontend to be displayed on things like the Scrollpanes and the popups