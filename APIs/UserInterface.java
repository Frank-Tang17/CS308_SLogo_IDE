
import javafx.scene.paint.Color;

import java.util.*;

/**
 * Internal UI Class that handles Visualization of the SLogo class. Note that these were our ORIGINAL APIs, and changes can be found in the API_CHANGES doc.
 * Specifically deals with updates to the Turtle and Grid views with respect to changes in Colors and Turtle Locations
 * Also handles the initial visualization of Buttons, Info, Grid, and Turtle
 * Handles the display of an error that is called after External is given an error to be handled by Contoller
 * Capable of clearing the screen too, resetting the game upon command
 */
public interface UserInterface {

    /**
     * Method used to visualize the Turtle upon its initialization (prior to receiving commands).
     * Places it at a set location (0, 0) near the center of the screen and loads a default Turtle image
     */
    public void displayTurtle();

    /**
     * Method used to visualize the plane that the Turtle lives on
     * The plane is clear at first (with no drawings on it) and is set to default background and pen colors
     */
    public void displayGrid();

    /**
     * Creates a Pop Up window displaying the exception thrown (given as a parameter)
     * Exception thrown is a custom exception that contains information on its type and a display message
     * @param e is the exception throw
     */
    public void displayException(Exception e);

    /**
     * Displays the InfoBox which contains information on the variables, commands, and history of the project
     * Displayed once and then updated using Observable lists, such that it autoupdates
     * Displayed in a VBox that alternates between information types with ToggleButtons
     */
    public void displayInfo();

    /**
     * Takes information from the InfoBundle to update a Turtle's Location and/or orientation
     * Updates the Turtle all at once in a non-animatory fashion
     */
    public void updateTurtle();

    /**
     * Prompts a FileLoader to be run to select a new image to be displayed
     * Does so by updating the TurtleView's image
     */
    public void updateTurtleImage();

    /**
     * Called after a user has inputed a new Color via the Background Color Chooser
     * The change is reflected in GridView where the background of the Grid is set to the new color
     */
    public void updateBackgroundColor(Color color);

    /**
     * Called after a user has inputed a new Color via the Pen Color Chooser
     * This change is reflected in GridView where the new grid pattern is the chosen color
     */
    public void updatePenColor();

    /**
     * Clears the Visualized Grid, erasing the turtles path and past history
     * Resets the turtle back to its original position and orientation
     */
    public void clear();
}