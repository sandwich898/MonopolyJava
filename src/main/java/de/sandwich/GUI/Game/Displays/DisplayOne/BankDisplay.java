package de.sandwich.GUI.Game.Displays.DisplayOne;

import de.sandwich.GUI.Game.DisplayController.GameDisplayControllerOne;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;

import java.util.ArrayList;
import java.util.HashMap;

import de.sandwich.Game;
import de.sandwich.Main;
import de.sandwich.Player;
import de.sandwich.Enums.ProgramColor;
import de.sandwich.Exceptions.WrongNodeException;
import de.sandwich.Fields.Field;
import de.sandwich.Fields.Street;


public class BankDisplay extends Pane {

    private final double WIDTH;
    private final double HEIGHT;

    private final Label mortgageDisplay;
    private final StackPane button;

    private Pane playerPane = new Pane();

    private Player activePlayer;

    private int mortgage = 0;
    private ArrayList<Integer> mortgageStreets = new ArrayList<>();

    public BankDisplay (double width, double height, GameDisplayControllerOne rootDisplay) {
        setId("gameScene_displayOne_BankDisplay");
        setMaxSize(width, height);

        this.WIDTH = width;
        this.HEIGHT = height;

        mortgageDisplay = buildLabel("gameScene_displayOne_bankDisplay_MortgageDisplay", mortgage + "€", Font.font(Main.TEXT_FONT, width * 0.05), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor(), 0, 0);
        mortgageDisplay.setLayoutY(height * 0.80);
        centeringChildInPane(mortgageDisplay, rootDisplay);

        button = createButton(width * 0.35, height * 0.08);
        button.setLayoutX(width / 2 - width * 0.175);
        button.setLayoutY(height - height * 0.10);

        button.setOnMouseClicked(mouseEvent -> {
            if (activePlayer != null && mortgage != 0) {
                Main.getGameOperator();
                HashMap<Integer, Field> fields = Game.getFields();

                for (int i = 0; i < fields.size(); i++) {
                    if (fields.get(i) instanceof Street s) {
                        if (s.getOwner() == activePlayer) {
                            if (s.isInBank()) {
                                if (!mortgageStreets.contains(i)) {
                                    s.purchaseFromBank();
                                }
                            } else if (mortgageStreets.contains(i)) {
                                s.sellToTheBank();
                            }
                        }
                    }
                }
            }
        });

        getChildren().addAll(mortgageDisplay, button, playerPane);
    }

    public void display(Player p) {
        if (!playerPane.getChildren().isEmpty()) {
            playerPane.getChildren().clear();
        }

        addMortgage(-(mortgage));
        mortgageStreets.clear();
        activePlayer = p;

        Pane playerDisplay = GameDisplayControllerOne.buildPlayer(WIDTH * 0.38, HEIGHT * 0.60, ProgramColor.BANK_PLAYER_BACKGROUND.getColor(), p);

        Rectangle[] streets = GameDisplayControllerOne.buildStreetInventar(WIDTH * 0.38, HEIGHT * 0.60, p);

        Pane streetDisplay = new Pane();

        for (Rectangle sObject : streets) {
            streetDisplay.getChildren().add(sObject);
            final int F_NUMBER = Integer.parseInt(sObject.getId().substring(12, 14));

            Main.getGameOperator();
            if (Game.getFields().get(F_NUMBER) instanceof Street street) {
                if (street.getOwner() == activePlayer) {
                    if (street.isInBank()) {
                        sObject.setId(sObject.getId() + "M");
                            sObject.setStroke(ProgramColor.SELECT_COLOR.getColor());
            
                            mortgageStreets.add(F_NUMBER);

                            ScaleTransition scaleTransitionBig = new ScaleTransition(Duration.seconds(0.15), sObject);
                            scaleTransitionBig.setByX(sObject.getWidth() * 0.02);
                            scaleTransitionBig.setByY(sObject.getWidth() * 0.02);
                            scaleTransitionBig.play();
            
                            ScaleTransition scaleTransitionSmall = new ScaleTransition(Duration.seconds(0.05), sObject);
                            scaleTransitionSmall.setByX(-(sObject.getWidth() * 0.01));
                            scaleTransitionSmall.setByY(-(sObject.getWidth() * 0.01));
            
                            scaleTransitionBig.setOnFinished(actionEvent -> scaleTransitionSmall.play());
                    }
                }
            }


            sObject.setOnMouseClicked(mouseEvent -> {
                if (Game.getFields().get(F_NUMBER) instanceof Street street) {
                    if (street.getOwner() == activePlayer) {
                        if (street.getHouseNumber() == 0) {
                            if (sObject.getId().endsWith("true")) {
                                sObject.setId(sObject.getId() + "M");
                                sObject.setStroke(ProgramColor.SELECT_COLOR.getColor());
    
                                Main.getGameOperator().getMiddleDisplayController().displayStreetInfoDisplay(street);
    
                                mortgageStreets.add(F_NUMBER);
    
                                ScaleTransition scaleTransitionBig = new ScaleTransition(Duration.seconds(0.15), sObject);
                                scaleTransitionBig.setByX(sObject.getWidth() * 0.02);
                                scaleTransitionBig.setByY(sObject.getWidth() * 0.02);
                                scaleTransitionBig.play();
    
                                ScaleTransition scaleTransitionSmall = new ScaleTransition(Duration.seconds(0.05), sObject);
                                scaleTransitionSmall.setByX(-(sObject.getWidth() * 0.01));
                                scaleTransitionSmall.setByY(-(sObject.getWidth() * 0.01));
    
                                scaleTransitionBig.setOnFinished(actionEvent -> scaleTransitionSmall.play());
    
                                if (Game.getFields().get(F_NUMBER) instanceof Street s)
                                    addMortgage(s.getPrice() / 2);
                                else throw new WrongNodeException();
                            } else if (sObject.getId().endsWith("trueM")) {
                                sObject.setId(sObject.getId().substring(0, sObject.getId().length() - 1));
    
                                Main.getGameOperator().getMiddleDisplayController().removeDisplay();
    
                                if (!(mortgageStreets.size() <= 1))
                                    mortgageStreets.remove(Integer.valueOf(F_NUMBER));
                                else 
                                    mortgageStreets.clear();
    
                                sObject.setStroke(ProgramColor.BORDER_COLOR_LIGHT.getColor());
    
                                ScaleTransition scaleTransitionBig = new ScaleTransition(Duration.seconds(0.15), sObject);
                                scaleTransitionBig.setByX((-sObject.getWidth() * 0.01));
                                scaleTransitionBig.setByY(-(sObject.getWidth() * 0.01));
                                scaleTransitionBig.play();
    
                                if (Game.getFields().get(F_NUMBER) instanceof Street s)
                                    addMortgage(-(s.getPrice() / 2));
                                else throw new WrongNodeException();
                            }
                        } else {
                            Main.getGameOperator().displayErrorMessage("Du kannst diese Straße nicht an die Bank verkaufen, weil was auf der Straße drauf gebaut ist!");
                        }
                    }
                }
            });

        }

        playerPane.setLayoutX(WIDTH / 2 - playerDisplay.getMaxWidth() / 2);
        playerPane.setLayoutY(HEIGHT / 2 - playerDisplay.getMaxHeight() / 2);

        playerPane.getChildren().addAll(playerDisplay, streetDisplay);
    }

    private void addMortgage(int x) {
        mortgage = mortgage + x;
        mortgageDisplay.setText(mortgage + "€");

        if (button.getChildren().get(0) instanceof Rectangle b) {
            if (mortgage < 0) {
                b.setFill(ProgramColor.BANK_MORTGAGE_BUTTON_PLUS.getColor());
            } else if (mortgage > 0) {
                b.setFill(ProgramColor.BANK_MORTGAGE_BUTTON_MINUS.getColor());
            } else {
                b.setFill(ProgramColor.BUTTON_DISABLED.getColor());
            }
        } else throw new IllegalArgumentException("The bank button has a wrong node order!");

        if (button.getChildren().get(1) instanceof Label t) {
            if (mortgage < 0) {
                t.setText("Wieder auf kaufen");
            } else if (mortgage > 0) {
               t.setText("Hypothek aufnehmen");
            } else {
                t.setText("");
            }
        }
    }

    private StackPane createButton(double width, double height) {
        StackPane pane = new StackPane();
        pane.setId("gameScene_displayOne_bankDisplay_MortgageButton");

        Rectangle background = buildRectangle("gameScene_displayOne_bankDisplay_mortgageButton_Background", width, height, ProgramColor.BUTTON_DISABLED.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), width * 0.01);
        background.setArcWidth(width * 0.25);
        background.setArcHeight(width * 0.25);

        Label text = buildLabel("gameScene_displayOne_bankDisplay_mortgageButton_Text", "", Font.font(Main.TEXT_FONT, FontWeight.BOLD, width * 0.10), null, ProgramColor.TEXT_COLOR.getColor());

        pane.getChildren().addAll(background, text);
        return pane;
    }

}
