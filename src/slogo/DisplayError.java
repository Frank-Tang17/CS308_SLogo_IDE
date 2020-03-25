package slogo;

import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Class that is used to help catch errors and display messages about the type of error
 *
 * @author Frank Tang
 */
public class DisplayError {
  private ResourceBundle languageBundle = ResourceBundle.getBundle("EnglishError");
  private static final String ERROR_TITLE = "ErrorTitle";
  /**
   * Makes an alert pop up with a specific message in it
   */
  public DisplayError(String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(languageBundle.getString(ERROR_TITLE));
    alert.setContentText(languageBundle.getString(message));
    alert.showAndWait();
  }
}

