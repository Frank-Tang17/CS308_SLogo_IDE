package slogo.Model;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import slogo.Model.CommandInfrastructure.CommandDatabase;
import slogo.Model.CommandInfrastructure.CommandProducer;

public class ModelDatabase {

  private SimpleObjectProperty<List<TurtleData>> turtleListProperty = new SimpleObjectProperty<>(
      FXCollections.observableArrayList());
  ;
  private SimpleStringProperty commandProperty;

  // regular expression representing any whitespace characters (space, tab, or newline)
  private String language;
  private ListProperty<String> HISTORY_LIST = new SimpleListProperty(FXCollections.observableList(new ArrayList<>()));


  private List<TurtleData> turtles = new ArrayList<>();

  private CommandDatabase originCommandDatabase;
  private ModelParser originParser;
  private CommandProducer originProducer;


  /**
   * Main Database for instantiating turtles and parsing data
   *
   * @author Frank Tang
   */

  public ModelDatabase() {
    TurtleData happyTurtle = new TurtleData("happy", 0, 0, 0);
    happyTurtle.setTurtleActive(true);
    turtles.add(happyTurtle);
    language = "English";

    originCommandDatabase = new CommandDatabase(turtles);
    originProducer = new CommandProducer(originCommandDatabase, HISTORY_LIST);
    originParser = new ModelParser(language, originCommandDatabase, originProducer);
  }

  public List<TurtleData> getMyTurtles() {
    return turtles;
  }

  public ListProperty<String> getHISTORY_LIST(){
    return HISTORY_LIST;
  }


  public String getCommand() {
    return commandProperty.get();
  }

//  public void addToHistory(String command) {
//    HISTORY_LIST.getValue().add(command);
//  }
  public void bindHistory(ListProperty displayedHistory) {
    displayedHistory.bind(HISTORY_LIST);
  }



}
