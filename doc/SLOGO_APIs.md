Authors: Benjamin Lu (bll32), Eric Doppelt (ead45), Erik Gregorio (eg158), Frank Tang (ft39)


## External:
### How you plan to separate the graphical interface from the interpreter and how do you plan to let them communicate when necessary?

In order to separate the graphical interface from the interpreter, we plan on having the graphical interface update itself based on the information given from the controller about the turtle's location in the model's database. These method that update the turtle on the view will be private and will help keep the two separate from each other. We only will allow the two of them to communicate with the command line, in which it will have a method like .sendCommand() to send an inputted command to the interpreter. As for updating the display, we will tell the view where the turtle needs to be updated and it will update the display to the user. 

### What objects will be used for communication: making it clear how needed information will get where it is needed, what will be available and what will be encapsulated, what things will be immutable, and what errors may be thrown?

The model needs to know how to move the turtle. This can be in the form of a String (immutable object) passed in through the View that then Maps to an instruction set.
m

The View needs to know a new point to move the turtle to, and it needs to knowthe type of path to take (arc or straight line, for example). This could be bundled together into a command object that would tie this information together. This would also support future extensions, such as changing the color. This information will be send from the Interpretor to the Controller.

Errors can be thrown for bad input: such as a location off the screen, or a command that is not known.

## Internal:
### How you plan to provide paths for extension through interfaces, inheritance, and design patterns for new features you might reasonably expect to be added to the program?

In order to allow for our program to extendable in the future, we plan on using inheritance structures for some of the classes and features that we are going to implement. Notably, we expect that we should be able to add new commands in the future that are going to be more complex than a simple forward or turning. As a result, we plan on making an abstract Command class that will be extended to create different commands. In the future, this Command class would just need to be extended to create new commands like making a zigzag trace or a Koch curve. 


### What subclasses or implementing classes will be used to extend your part to add new features: making it clear what kind of code someone will be expected to write, what parts of your code you expect to be closed to modification, and what errors may be thrown?

The main aspect that is going to extended is the Command object, so that we can add new commands very easily in the future. However, we would expect the model and the view to be closed off from modification because these are more static pieces of the program that will not be modified as often. This is because the model represents the database of points and such that the turtle has gone to, and if the model is changed, then much of the other pieces of the program needs to be changed. As a result, the model should be closed off from modification for the most part. In additon, the view should be closed off from modification because it should be flexible enough from its conception that it can handle new commands being input in. This means that the view should just draw what is given, and not have to interpret what each command means. This will allow us to just change a command and have the view change accordingly.

The main exceptions that are thrown are when a command given is not formatted correctly and it will be caught by the controller before it goes to the model. The other error that should be thrown is when an inputted command would provide an invalid state, such as if a command causes the turtle to go off screen, etc.

## Use Cases:

### The user types 'fd 50' in the command window, sees the turtle move in the display window leaving a trail, and has the command added to the environment's history.

"fd 50" is typed in
The string is passed through the Error Checker
After passing, the String is given to the Interprator.
The Interprator moves the turtle on the model grid 50 units forward.
The Drawer is called, telling the View to create a new point 50 units ahead of the Turtle in the form of a straight line.
The Turtle is moved to the new point.
A line is drawn behind it.
Upon successful completion, "fd 50" is displayed in the UI as Text in a history section.

### The user types '50 fd' in the command window and sees an error message that the command was not formatted correctly.

"fd 50" is typed in
The string is passed through the Error Checker
The error checker throws the Error signifying that the number came first

### The user types 'pu fd 50 pd fd 50' in the command window and sees the turtle move twice (once without a trail and once with a trail).

The general error checking and interpetor applies here. The difference is that the commands "pu" and "pd" are recognized by the interprator and toggle a boolean which is included in the information sent via the Drawer. This tells the View not to leave a trace and just update the turtle's location.

### The user changes the color of the environment's background.
The user inputs the text command like "background blue"
The command passes through the Error Checker
The Model does not operate on the command
The Drawer passes the command to View
View updates the background color