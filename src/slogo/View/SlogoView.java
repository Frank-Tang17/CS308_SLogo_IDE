package slogo.View;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.CommandInfrastructure.CommandProducer;
import slogo.Model.ModelDatabase;
import slogo.Model.ModelParser;
import slogo.View.Info.InfoViews;
import slogo.View.Input.InputView;


public class SlogoView {

    private static final int SCENE_WIDTH = 1000;
    private static final int SCENE_HEIGHT = 600;

    private static final String MODELPARSER_LANGUAGE = "English";

    private static final String STYLESHEET_FILE = "default.css";

    private BorderPane myBorderPane;
    private ModelDatabase myModelDatabase;
    private CommandDatabase myCommandDatabase;
    private CommandProducer myCommandProducer;
    private ModelParser myModelParser;
    private InputView myInputView;
    private Pane myBackgroundPane;
    private TurtleView myTurtleView;
    private InfoViews myInfoView;


    public SlogoView(Stage displayedStage) {
        initModel();
        initView();
        initStage(displayedStage);
        bindProperties();
    }

    private void initModel() {
        myModelDatabase = new ModelDatabase();
        myCommandDatabase = new CommandDatabase(myModelDatabase.getMyTurtles());
        myCommandProducer = new CommandProducer(myCommandDatabase, myModelDatabase.getHISTORY_LIST());
        myModelParser = new ModelParser(MODELPARSER_LANGUAGE, myCommandDatabase, myCommandProducer);
    }

    private void initView() {
        myBackgroundPane = new Pane();
        myTurtleView = new TurtleView(myModelDatabase.getMyTurtles().get(0), myBackgroundPane,c -> myModelParser.parseText(c));
        CommandBox myCommandLine = new CommandBox(myModelParser, myTurtleView);
        myInputView = new InputView();
        myInfoView = new InfoViews(c -> myModelParser.parseText(c), myInputView.getLanguage());

        VBox commandAndInput = new VBox();
        commandAndInput.getChildren().addAll(myInputView.getInputPanel(), myCommandLine.getCommandLine());
        BackgroundFill commandBackground = new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY);
        commandAndInput.setBackground(new Background(commandBackground));

        myBorderPane = new BorderPane();
        myBorderPane.setBottom(commandAndInput);
        myBorderPane.setCenter(myBackgroundPane);
        myBorderPane.setRight(myInfoView.getCompletePanel());
    }

    private void initStage(Stage primaryStage) {
        Scene myScene = new Scene(myBorderPane, SCENE_WIDTH,SCENE_HEIGHT);
        myScene.getStylesheets()
                .add(getClass().getResource("/" + STYLESHEET_FILE).toExternalForm());
        primaryStage.setScene(myScene);
        primaryStage.show();
    }

    private void bindProperties() {
        createBindableBackground();
        createBindableFile();
        createBindableLanguage();
        createBindableInfoPanel();
    }

    // Inspiration from https://stackoverflow.com/questions/33999728/binding-colorpicker-in-javafx-to-label-background-property
    // TODO: once we understand bindings better, refactor
    private void createBindableBackground() {
        myBackgroundPane.backgroundProperty().bind(Bindings.createObjectBinding(() -> {
            BackgroundFill fill = new BackgroundFill((Paint)myInputView.getBackgroundPropertyColor().getValue(), CornerRadii.EMPTY, Insets.EMPTY);
            return new Background(fill);
        }, myInputView.getBackgroundPropertyColor()));
        myCommandDatabase.bindBackgroundColor(myInputView.getBackgroundPropertyColor());
    }

    private void createBindableFile() {
        myTurtleView.getTurtleFile().bind(myInputView.getTurtleFile());
    }

    private void createBindableLanguage() {myModelParser.getParserLanguageProperty().bind(myInputView.getLanguage());}

    private void createBindableInfoPanel() {
        myModelDatabase.bindHistory((ListProperty) myInfoView.getHistoryProperty());
        myCommandDatabase.bindCommands((MapProperty) myInfoView.getCommandProperty());
        myCommandDatabase.bindVariables((MapProperty) myInfoView.getVariableProperty());
        myCommandDatabase.bindColors(myInfoView.getColorsProperty());
    }
}