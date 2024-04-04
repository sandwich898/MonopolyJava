package de.sandwich.GUI.Game.DisplayController;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;

import de.sandwich.Main;
import de.sandwich.Enums.ProgramColor;
import de.sandwich.GUI.Game.Displays.DisplayTwo.ActionDisplay;
import de.sandwich.GUI.Game.Displays.DisplayTwo.DiceDisplay;


public class GameDisplayControllerTwo extends Pane {
    private final Pane actionDisplay;
    private final Pane diceDisplay;

    public GameDisplayControllerTwo(double width, double height) {
        setId("gameScene_DisplayTwo");
        setMaxSize(width, height);
        getChildren().add(buildRectangle("gameScene_action_Background", width, height, ProgramColor.DISPLAY_TWO_BACKGROUND.getColor(), true, ProgramColor.BORDER_COLOR_LIGHT.getColor(), width * 0.006));

        Label header = buildLabel("gameScene_action_Header", "Aktionen", Font.font(Main.TEXT_FONT, FontWeight.BOLD, width * 0.10), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());
        centeringChildInPane(header, this);

        actionDisplay = new ActionDisplay(width, height, this);
        diceDisplay = new DiceDisplay(width, height, this);
        actionDisplay.setVisible(false);
        diceDisplay.setVisible(true);

        getChildren().addAll(header, actionDisplay, diceDisplay);
    }

    public void displayDice() {
        diceDisplay.setVisible(true);
        actionDisplay.setVisible(false);
    }

    public void displayPlayerAction() {
        diceDisplay.setVisible(false);
        actionDisplay.setVisible(true);
    }


}