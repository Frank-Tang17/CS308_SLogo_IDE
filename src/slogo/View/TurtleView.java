package slogo.View;


import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import java.io.File;
import java.util.*;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import slogo.Model.TurtleData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Turtle View handles all turtle display methods along with its path.
 * @author erikgregorio
 */
public class TurtleView {
    public static final int X_COORDINATE = 0;
    public static final int Y_COORDINATE = 1;
    public static final double ANGLE_OFFSET = 90.0;
    public static final double CENTER_X = 400;
    public static final double CENTER_Y = 285;
    public static final double DEFAULT_ANGLE = 45.0;
    public static final double OFFSET = 360;

    public static final KeyCode FORWARD = KeyCode.F;
    public static final KeyCode BACKWARD = KeyCode.B;
    public static final KeyCode RIGHT_ROTATE = KeyCode.R;
    public static final KeyCode LEFT_ROTATE = KeyCode.L;

    private static final String TURTLEIMAGES_DIRECTORY = "turtleImages";
    private static final String DEFAULT_IMAGE_PATH = "turtleImages/perfectTurtle.png";
    public static final String FORWARD_COMMAND = "fd 50";
    public static final String BACKWARD_COMMAND = "bk 50";
    public static final String WHITESPACE = "\\s+";

    private String turtleID;
    private SimpleBooleanProperty isPenDown;
    private SimpleDoubleProperty turtleAngle;
    private List<Double> currentPosition;
    private ObservableList<List<Double>> positions;
    private Pane myBackground;
    private ImageView turtleView;
    private ObjectProperty<File> myTurtleFile;

    private double heightOffset;
    private double widthOffset;
    private ObjectProperty<Color> penColor= new SimpleObjectProperty<>(Color.BLACK);
    private Double lineWidth = 3.0;
    private SimpleDoubleProperty paneHeightOffset = new SimpleDoubleProperty();
    private SimpleDoubleProperty paneWidthOffset = new SimpleDoubleProperty();
    private TurtlePopUp myTurtleInfo;
    private SimpleBooleanProperty turtleIsActive = new SimpleBooleanProperty(true);

    private int currentIndex;
    private HashMap<Integer, Line> turtleLines;
    private HashMap<Integer, Double> turtleRotations = new HashMap<>();

    private SimpleDoubleProperty xCoor = new SimpleDoubleProperty();
    private SimpleDoubleProperty yCoor = new SimpleDoubleProperty();

    private Consumer<List<String>> parser;



    /**
     * Constructor used to build a new Turtle Display. One per backend Turtle.
     * @param turtle Backend Turtle To Bind
     * @param pane Platform to display Turtle
     */
    public TurtleView(TurtleData turtle, Pane pane, Consumer<List<String>> parserCommand){
        turtleID = turtle.getTurtleID();
        parser = parserCommand;
        myBackground = pane;
        setUpTurtle(turtle, pane);
        currentPosition = setUpInitialPosition();
        bindProperties(turtle);
        addTurtleInteraction();
        addTurtlePopup();
    }

    private void setUpTurtle(TurtleData turtle, Pane pane) {
        turtleView = new ImageView(getImage(DEFAULT_IMAGE_PATH));
        setUpTurtleFile();
        turtleView.setRotate(turtle.getTurtleHeading() + ANGLE_OFFSET);
        turtleView.setY(CENTER_Y - heightOffset);
        turtleView.setX(CENTER_X - widthOffset);
        pane.getChildren().add(turtleView);

        paneWidthOffset.bind(pane.widthProperty());
        paneHeightOffset.bind(pane.heightProperty());
        turtleRotations.put(currentIndex, turtleView.getRotate());
    }

    private void setUpTurtleFile(){
        myTurtleFile = new SimpleObjectProperty<>();
        myTurtleFile.addListener((observable, oldValue, newValue) -> {
            String filePath = newValue.toString().substring(newValue.toString().indexOf(TURTLEIMAGES_DIRECTORY));
            turtleView.setImage(getImage(filePath));
        });
        myTurtleFile.setValue(new File(DEFAULT_IMAGE_PATH));
    }

    private Image getImage(String Path){
        Image newImage = new Image(Path);
        heightOffset = newImage.getHeight()/2;
        widthOffset = newImage.getWidth()/2;
        return newImage;
    }

    private List<Double> setUpInitialPosition(){
        ArrayList<Double > myList = new ArrayList<>();
        myList.add(turtleView.getX());
        myList.add(turtleView.getY());
        return myList;
    }

    private void addTurtleInteraction(){
        myBackground.setOnMouseClicked(event -> myBackground.requestFocus());
        myBackground.setOnKeyPressed(event -> handleMovement(event.getCode()));
        turtleView.setOnMouseClicked(event -> toggleTurtle());
    }

    private void handleMovement(KeyCode keyPressed){
        if(keyPressed == FORWARD){ parser.accept(Arrays.asList(FORWARD_COMMAND.split(WHITESPACE)));}
        else if(keyPressed == BACKWARD){parser.accept(Arrays.asList(BACKWARD_COMMAND.split(WHITESPACE)));}
        else if(keyPressed == RIGHT_ROTATE) turtleAngle.set(turtleAngle.get() - DEFAULT_ANGLE);
        else if(keyPressed == LEFT_ROTATE) turtleAngle.set(turtleAngle.get() + DEFAULT_ANGLE);
    }

    private void toggleTurtle(){
        if(turtleIsActive.get()){
            turtleView.setOpacity(0.2);
            turtleIsActive.setValue(false);
        }
        else{
            turtleView.setOpacity(1.0);
            turtleIsActive.setValue(true);
        }
    }
    /**
     * Methods used to bind the properties of all the backend turtle to the front end turtle
     * @param turtle
     */

    private void bindProperties(TurtleData turtle){
        bindPositions(turtle.getCoordHistory());
        bindPen(turtle.getPenDownProperty());
        bindAngle(turtle.directionProperty());
        Bindings.bindBidirectional(turtleIsActive,turtle.getActiveProperty());
        Bindings.bindBidirectional(xCoor, turtle.getTurtleXProperty());
        Bindings.bindBidirectional(yCoor, turtle.getTurtleYProperty());
        turtleView.visibleProperty().bind(turtle.turtleVisibility());
    }

    private void bindPositions(ObservableList<List<Double>> turtlepos){
        positions = FXCollections.observableArrayList();
        Bindings.bindContentBidirectional(positions, turtlepos);
        positions.addListener((ListChangeListener<List<Double>>) c -> {
            newTurtlePosition(c);
        });
    }

    private void newTurtlePosition(ListChangeListener.Change<? extends List<Double>> allPositions){
        allPositions.next();
        if(!allPositions.wasRemoved()) {
            //if (turtleIsActive) {
                List<Double> newPosition = allPositions.getList().get(allPositions.getList().size() - 1);
                currentIndex++;
                System.out.println("PRINTING INDEX :" + currentIndex);
            if (isPenDown.get()) addPath(getNewLine(currentPosition, newPosition));
            updateTurtlePosition(newPosition.get(X_COORDINATE), newPosition.get(Y_COORDINATE));
            //} else positions.remove(allPositions.getList().size() - 1);
        }
    }

    private void bindPen(SimpleBooleanProperty backendPen){
        isPenDown = new SimpleBooleanProperty();
        Bindings.bindBidirectional(isPenDown, backendPen);
        isPenDown.addListener((observable, oldValue, newValue) -> setPenDown(newValue));
    }

    private void bindAngle(SimpleDoubleProperty backendAngle){
        turtleAngle = new SimpleDoubleProperty();
        Bindings.bindBidirectional(turtleAngle, backendAngle);
        turtleAngle.addListener((observable, oldValue, newValue) -> {
            System.out.println(ANGLE_OFFSET);
            RotateTransition rotationAnimation = new RotateTransition();
            rotationAnimation.setFromAngle((double)oldValue + ANGLE_OFFSET);
            rotationAnimation.setToAngle((double)newValue + ANGLE_OFFSET);
            rotationAnimation.setDuration(Duration.millis(1000));
            rotationAnimation.setNode(turtleView);
            rotationAnimation.play();
            turtleView.getRotate();
        });

        turtleAngle.addListener( (observable, oldValue, newValue) ->{
                if(turtleIsActive.get()) turtleView.setRotate((newValue.doubleValue()+ ANGLE_OFFSET));
                turtleRotations.put(currentIndex, turtleView.getRotate());
                if(myTurtleInfo == null) myTurtleInfo = new TurtlePopUp(this, 0.0,0.0);
                myTurtleInfo.updateHeading(OFFSET - newValue.doubleValue());
        });
    }

    /**
     * Methods to handle turtle popup menu as well as when to hide it and show it
     */
    private void addTurtlePopup(){
        turtleView.setOnMouseEntered(event -> showPopup());
        turtleView.setOnMouseExited(event -> hidePopup());
    }

    private void showPopup(){
        if(myTurtleInfo == null) myTurtleInfo = new TurtlePopUp(this,0.0,0.0);
        updatePopupPosition();
        myTurtleInfo.show(turtleView.getScene().getWindow());
    }

    private void updatePopupPosition(){
        if(myTurtleInfo == null) myTurtleInfo = new TurtlePopUp(this, 0.0,0.0);
        myTurtleInfo.updatePopupPosition(turtleView.getX() + turtleView.getScene().getWindow().getX() + widthOffset,
                turtleView.getY() + turtleView.getScene().getWindow().getY() + 2*heightOffset);
    }

    private void hidePopup(){
        myTurtleInfo.hide();
    }

    /**
     * Update turtle position using the given new coordinates
     */
    private void updateTurtlePosition(double x, double y){
        Path animatedPath = new Path();

        MoveTo startingPoint = new MoveTo(currentPosition.get(X_COORDINATE), currentPosition.get(Y_COORDINATE));
        LineTo animatedLine = new LineTo(x + paneWidthOffset.getValue()/2, y + paneHeightOffset.getValue()/2);

        animatedPath.getElements().addAll(startingPoint, animatedLine);

        PathTransition animation = new PathTransition();
        animation.setPath(animatedPath);
        animation.setDuration(Duration.seconds(1));
        animation.setNode(turtleView);
        animation.setInterpolator(Interpolator.LINEAR);
        animation.play();

        currentPosition.clear();
        currentPosition.add(X_COORDINATE, x + paneWidthOffset.get()/2);
        currentPosition.add(Y_COORDINATE, y + paneHeightOffset.get()/2);
    }

    /**
     * Methods for adding a new line/path for the turtle and keeping track of them
     * @param newPath
     */

    private void addPath(Line newPath){
        myBackground.getChildren().add(newPath);
        if(turtleLines == null) turtleLines = new HashMap<>();

        if(undoButtonClicked()) clearOldPath();
        turtleLines.put(currentIndex, newPath);
        //currentIndex = turtleLines.size();
    }

    private boolean undoButtonClicked(){
        return (currentIndex < turtleLines.size()-1);
    }
    private void clearOldPath(){
        //TODO REMOVE LINES OF OLD PATH;
        for(int i = currentIndex+1; i< positions.size(); i++){
            positions.remove(i);
            i--;
        }
    }

    private Line getNewLine(List<Double> oldValues, List<Double> newValues){
        Line newPath = new Line();
        newPath.setStartX(oldValues.get(X_COORDINATE));
        newPath.setStartY(oldValues.get(Y_COORDINATE));
        newPath.setEndX(newValues.get(X_COORDINATE) + paneWidthOffset.get()/2);
        newPath.setEndY(newValues.get(Y_COORDINATE) + paneHeightOffset.get()/2);
        newPath.setStroke(penColor.getValue());
        newPath.setStrokeWidth(lineWidth);
        return newPath;
    }

    /**
     * Method that allows setting a new image for the turtle
     */
    public ObjectProperty<File> getTurtleFile(){
        return myTurtleFile;
    }

    /**
     * Turtle Method returns Color property to bind for automatic Pen Color changing
     * @return Color Property
     */
    public ObjectProperty<Color> getPenColorProperty(){
        return penColor;
    }
    /**
     * Allows other calsses to set a new width for this turtle pen
     */
    public void setNewWidth(Double newWidth){
        lineWidth = newWidth;
    }
    /**
     *
     * @return Pen Down/Up Property
     */
    public SimpleBooleanProperty getPenDownProperty(){
        return isPenDown;
    }
    public void setPenDown(Boolean newValue){
        isPenDown.setValue(newValue);
    }

    public String getID(){
        return turtleID;
    }
    /**
     * Undo button to restore turtle to its second most recent postition
     */
    public void undoMovement(){
        System.out.println("Current Index: " + currentIndex);
        if(turtleLines.containsKey(currentIndex)) myBackground.getChildren().remove(turtleLines.get(currentIndex));
        currentIndex--;
        updateTurtlePosition(positions.get(currentIndex).get(X_COORDINATE), positions.get(currentIndex).get(Y_COORDINATE));
        xCoor.set(positions.get(currentIndex).get(X_COORDINATE));
        yCoor.set(positions.get(currentIndex).get(Y_COORDINATE));
        if(turtleRotations.containsKey(currentIndex)) {
            turtleView.setRotate(turtleRotations.get(currentIndex));
            turtleAngle.set(turtleRotations.get(currentIndex)-ANGLE_OFFSET);
        }
    }
    /**
     * Undo button to restore turtle to its last postition
     */
    public void redoMovement(){
        currentIndex++;
        if(turtleLines.containsKey(currentIndex)) myBackground.getChildren().add(turtleLines.get(currentIndex));
        updateTurtlePosition(positions.get(currentIndex).get(X_COORDINATE), positions.get(currentIndex).get(Y_COORDINATE));
        xCoor.set(positions.get(currentIndex).get(X_COORDINATE));
        yCoor.set(positions.get(currentIndex).get(Y_COORDINATE));
        if(turtleRotations.containsKey(currentIndex)) {
            turtleView.setRotate(turtleRotations.get(currentIndex));
            turtleAngle.set(turtleRotations.get(currentIndex)-ANGLE_OFFSET);
        }
    }
}