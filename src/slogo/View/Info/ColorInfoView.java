package slogo.View.Info;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * Class representing the information displayed regarding the palettes available to be picked.
 * Information is encoded into a VBox that is displayed in the toggle button in InfoViews.
 * The color info is bound via a Map to the backend so it automatically updates.
 * @author Eric Doppelt
 */

public class ColorInfoView  {

    private MapProperty<Integer, List> myInfo;

    private VBox displayedInfo;

    private static final String COLON_REGEX = ":";
    private static final String SPACE = " ";
    private static final int R_INDEX = 0;
    private static final int G_INDEX = 1;
    private static final int B_INDEX = 2;

    /**
     * Constructor initializes the displayed VBox and map of colors that is binded to the backend.
     * A change listener is added to automatically update the frontend to display a new color when it is stored in the backend.
     */
    public ColorInfoView() {
        displayedInfo = new VBox();

        myInfo = new SimpleMapProperty<Integer, List>();
        myInfo.addListener((observable, oldValue, newValue) -> {
            displayedInfo.getChildren().clear();
            for (Integer index : (newValue.keySet())) {
                List<Integer> RGBColors = myInfo.get(index);
                String displayedText = index + COLON_REGEX + SPACE + RGBColors.get(R_INDEX) + SPACE + RGBColors.get(G_INDEX) + SPACE + RGBColors.get(B_INDEX);
                Label addedLabel = new Label(displayedText);
                addedLabel.setBackground(backgroundFromRGB(RGBColors.get(R_INDEX), RGBColors.get(G_INDEX), RGBColors.get(B_INDEX)));
                displayedInfo.getChildren().add(addedLabel);
            }
        });
    }

    /**
     * Basic getter method that retrns the VBox to display in InfoViews
     * @return VBox of labels representing each color
     */
    public VBox getInfoVBox() {
        return displayedInfo;
    }

    /**
     * Basic getter for the color map; this is used to bind the information to the backend.
     * @return MapProperty holding the name of each color and its RGB values
     */
    public MapProperty getInfoProperty() {
        return myInfo;
    }

    private Background backgroundFromRGB(Integer r, Integer g, Integer b) {
        return new Background(new BackgroundFill(Color.rgb(r, g, b), CornerRadii.EMPTY, Insets.EMPTY));
    }
}
