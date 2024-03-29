package sandwich.de.monopoly;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sandwich.de.monopoly.DennisUtilitiesPackage.Java.ConsoleUtilities;
import sandwich.de.monopoly.Enums.CornerTyp;
import sandwich.de.monopoly.Enums.ExtraFields;
import sandwich.de.monopoly.Exceptions.PlayerNotFoundExceptions;
import sandwich.de.monopoly.Exceptions.ToManyPlayersExceptions;
import sandwich.de.monopoly.Fields.*;
import sandwich.de.monopoly.GUI.Game.DisplayController.GameDisplayControllerOne;
import sandwich.de.monopoly.GUI.Game.DisplayController.GameDisplayControllerTwo;
import sandwich.de.monopoly.GUI.Game.DisplayController.MiddleGameDisplayController;
import sandwich.de.monopoly.GUI.Game.GameBoard;

import java.util.ArrayList;
import java.util.HashMap;

import static sandwich.de.monopoly.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPut;
import static sandwich.de.monopoly.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPutLine;
import static sandwich.de.monopoly.DennisUtilitiesPackage.Java.JavaUtilities.buildLongText;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;

public class Game {

    //Players
    private Player playerOne;
    private Player playerTwo;
    private Player playerThree;
    private Player playerFour;
    private Player playerFive;
    private final ArrayList<Player> playerList = new ArrayList<>();

    //Game Scene

    //Displays

    private final GameDisplayControllerOne displayControllerOne;
    private final GameDisplayControllerTwo displayControllerTwo;
    private final MiddleGameDisplayController middleDisplayController;

    //Gameboard
    private final Color BACKGROUND_COLOR = Color.rgb(112, 224, 88);
    private static final HashMap<Integer, Field> FIELDS = createFields();
    private final GameBoard gameBoard = new GameBoard(Main.WINDOW_HEIGHT * 0.98, Color.rgb(112, 224, 88), FIELDS);
    private Scene gameScene;
    private Pane root;

    //Game variables
    private int freeParkingMoney = 999;
    private int playerInGame = 0;
    private boolean turnPlayerIsMoving = false;

    private Player turnPlayer;
    private Player nextPlayer;


    public Game(Player[] players) {

        if (Main.CONSOLE_OUT_PUT) {
            consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, "Game Controller, Konstruktor:");
            consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
        }

        //Displays

        displayControllerOne = new GameDisplayControllerOne((Main.WINDOW_WIDTH - Main.WINDOW_HEIGHT) / 1.1, Main.WINDOW_HEIGHT * 0.60);
        displayControllerTwo = new GameDisplayControllerTwo((Main.WINDOW_WIDTH - Main.WINDOW_HEIGHT) / 1.1, Main.WINDOW_HEIGHT * 0.38, Color.rgb(56, 182, 255));
        middleDisplayController = new MiddleGameDisplayController(Main.WINDOW_HEIGHT * 0.40, Main.WINDOW_HEIGHT * 0.20, Main.WINDOW_HEIGHT * 0.18, Color.rgb(13, 155, 35));


        VBox sideDisplays = new VBox(Main.WINDOW_HEIGHT * 0.02);

        sideDisplays.getChildren().addAll(
                displayControllerOne,
                displayControllerTwo
        );
        sideDisplays.setLayoutX(Main.WINDOW_HEIGHT + (((Main.WINDOW_WIDTH - Main.WINDOW_HEIGHT) / 2) - ((Main.WINDOW_WIDTH - Main.WINDOW_HEIGHT) / 1.1) / 2));
        sideDisplays.setLayoutY(0);


        //REMOVE
        middleDisplayController.setLayoutY(Main.WINDOW_HEIGHT * 0.18);

        root = new Pane(
                buildRectangle("gameScene_Background", Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT, BACKGROUND_COLOR, true, null, 0, 0, 0),
                gameBoard,
                sideDisplays,
                middleDisplayController
        );

        gameScene = new Scene(root);


        for (int i = 0, j = 1; i != 5; i++) {
            if (players[i] != null) {
                playerInGame++;

                assert false;
                playerList.add(players[i]);

                switch (j) {
                    case 1 -> playerOne = players[i];
                    case 2 -> playerTwo = players[i];
                    case 3 -> playerThree = players[i];
                    case 4 -> playerFour = players[i];
                    case 5 -> playerFive = players[i];
                }
                j++;

            }
        }
        turnPlayer = playerOne;

        if (Main.CONSOLE_OUT_PUT) {
            consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, "Game Operator wurde erstellt!");
            consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, playerInGame + " Player Objects: ");

            for (int i = 0; i != 5; i++) {
                switch (i) {
                    case 0 -> {
                        if (playerOne != null) {
                            consoleOutPut(ConsoleUtilities.colors.BLUE, ConsoleUtilities.textStyle.REGULAR, "PlayerOne: ");
                            consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, playerOne.toString());
                        } else
                            consoleOutPut(ConsoleUtilities.colors.RED, ConsoleUtilities.textStyle.REGULAR, "Null");
                    }
                    case 1 -> {
                        if (playerTwo != null) {
                            consoleOutPut(ConsoleUtilities.colors.BLUE, ConsoleUtilities.textStyle.REGULAR, "PlayerTwo: ");
                            consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, playerTwo.toString());
                        } else
                            consoleOutPut(ConsoleUtilities.colors.RED, ConsoleUtilities.textStyle.REGULAR, "Null");
                    }
                    case 2 -> {
                        if (playerThree != null) {
                            consoleOutPut(ConsoleUtilities.colors.BLUE, ConsoleUtilities.textStyle.REGULAR, "PlayerThree: ");
                            consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, playerThree.toString());
                        } else
                            consoleOutPut(ConsoleUtilities.colors.RED, ConsoleUtilities.textStyle.REGULAR, "Null");
                    }
                    case 3 -> {
                        if (playerFour != null) {
                            consoleOutPut(ConsoleUtilities.colors.BLUE, ConsoleUtilities.textStyle.REGULAR, "PlayerFour: ");
                            consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, playerFour.toString());
                        } else
                            consoleOutPut(ConsoleUtilities.colors.RED, ConsoleUtilities.textStyle.REGULAR, "Null");
                    }
                    case 4 -> {
                        if (playerFive != null) {
                            consoleOutPut(ConsoleUtilities.colors.BLUE, ConsoleUtilities.textStyle.REGULAR, "PlayerFive: ");
                            consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, playerFive.toString());
                        } else
                            consoleOutPut(ConsoleUtilities.colors.RED, ConsoleUtilities.textStyle.REGULAR, "Null");
                    }
                    default -> throw new ToManyPlayersExceptions();
                }
                System.out.println();
            }
            consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
        }

        //Zeigt die Spieler an
        gameBoard.addPlayers(playerList);
        displayControllerOne.displayPlayers(playerList);
        displayControllerTwo.displayDice();
        //Zeigt das Game Board an
        Main.getPrimaryStage().setScene(gameScene);
    }

    //Starts the Animation and controls the postion from the player.
    public void playerRolledDice(int diceOne, int diceTwo) {

        turnPlayerIsMoving = true;

        if (turnPlayer.isInJail()) {
            if (diceOne == diceTwo) {
                nextPlayer = turnPlayer;
                turnPlayer.setInJail(false);
            } else {
                turnPlayer.removeOnInJailRemain();
                if (turnPlayer.getInJailRemain() <= 0) {
                    turnPlayer.setInJail(false);
                    gameBoard.removePlayerFromJail(turnPlayer);
                }
            }
        } else {
            turnPlayerIsMoving = true;
            try { gameBoard.movePlayer(turnPlayer, diceOne + diceTwo); } catch (PlayerNotFoundExceptions ignored) {}
            turnPlayer.moveFieldPostion(diceOne + diceTwo);

            if (diceOne == diceTwo)
                nextPlayer = turnPlayer;

            if (Main.CONSOLE_OUT_PUT) {
                consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Spieler nummer " + turnPlayer.getOrderNumber() + ", wurde bewegt auf die Feld Postion Nummer:");
                consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, " " + turnPlayer.getFieldPostion());
                consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
            }
        }
    }

    //Detects the field where the player is standing on, and then carries out the function of the field.
    public void playerHasMoved() {
        turnPlayerIsMoving = false;
        displayControllerTwo.showPlayerAction();

        if (FIELDS.get(turnPlayer.getFieldPostion()) instanceof Street street) { //Is the player on a Street
            if (!street.isOwned()) {
                if (street.getSalePrice() <= turnPlayer.getBankAccount()) {
                    middleDisplayController.displayBuyStreet(street);
                }
            } else {
                middleDisplayController.displayPayDisplay(buildLongText("Du schuldest den Spieler", street.getOwner().getName() + " " + (street.getRent() + "€")), street.getRent());
            }
        } else if (FIELDS.get(turnPlayer.getFieldPostion()) instanceof Corner corner) { //Is the player on a Corner
            switch (corner.getTyp()) {
                case FREE_PARKING -> {
                    turnPlayer.addBankAccount(freeParkingMoney);
                }
                case GO_TO_JAIL -> {
                    turnPlayer.setInJail(true);
                    gameBoard.setPlayerInJail(turnPlayer);
                }
            }
        } else if (FIELDS.get(turnPlayer.getFieldPostion()) instanceof PayExtra payField) { //Is the player on a pay extra field
            middleDisplayController.displayPayDisplay(payField.getTyp().getMessage(), payField.getPrice());
        }
    }

    //Controls the Street buy System
    public void buyStreet(Street street) {
        street.setOwner(turnPlayer);
        turnPlayer.addBankAccount(-street.getSalePrice());
    }

    public void transferMoney(int money) {
        if (!(money <= 0)) {

            if (FIELDS.get(turnPlayer.getFieldPostion()) instanceof Street street) {
                if (street.isOwned())
                    street.getOwner().addBankAccount(money);
            } else if (FIELDS.get(turnPlayer.getFieldPostion()) instanceof PayExtra) {
                freeParkingMoney = freeParkingMoney + money;

                if (Main.CONSOLE_OUT_PUT) {
                    consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                    consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Auf das frei-parken Konto wurden ");
                    consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, money + "€");
                    consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, " hinzugefügt!");
                    consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Damit sind jetzt ");
                    consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, freeParkingMoney + "€");
                    consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, " auf frei-parken!");
                    consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                }
            }
        }
        else throw new IllegalArgumentException("No negative amounts can be transfer!");
    }

    private static HashMap<Integer, Field> createFields() {
        HashMap<Integer, Field> s = new HashMap<>();

        s.put(0, new Corner(CornerTyp.START, 0));
        s.put(1, new Street("Braune Straße", 200, Color.rgb(112, 40, 0), 1));
        s.put(2, new GetCard(2));
        s.put(3, new Street("Braune Straße", 200,  Color.rgb(112, 40, 0), 3));
        s.put(4, new PayExtra(200, ExtraFields.SPOTIFY_PREMIUM, 4));
        s.put(5, new Station("Bahnhof", 200, 5));
        s.put(6, new Street("Aqua Straße", 200, Color.AQUA, 6));
        s.put(7, new GetCard(GetCard.ChanceColors.GREEN, 7));
        s.put(8, new Street("Aqua Straße", 200, Color.AQUA, 8));
        s.put(9, new Street("Aqua Straße", 200, Color.AQUA, 9));
        s.put(10, new Corner(CornerTyp.JAIL, 10));
        s.put(11, new Street("Purple Straße", 200, Color.PURPLE, 11));
        s.put(12, new PayExtra(200, ExtraFields.HESSLER_SCHULDEN, 12));
        s.put(13, new Street("Purple Straße", 200, Color.PURPLE, 13));
        s.put(14, new Street("Purple Straße", 200, Color.PURPLE, 14));
        s.put(15, new Station("Bahnhof", 200, 15));
        s.put(16, new Street("Orang Straße", 200, Color.ORANGE, 16));
        s.put(17, new Street("Orang Straße", 200, Color.ORANGE, 17));
        s.put(18, new GetCard(18));
        s.put(19, new Street("Orang Straße", 200, Color.ORANGE, 19));
        s.put(20, new Corner(CornerTyp.FREE_PARKING, 20));
        s.put(21, new Street("Red Straße", 200, Color.RED, 21));
        s.put(22, new Street("Red Straße", 200, Color.RED, 22));
        s.put(23, new GetCard(GetCard.ChanceColors.BLUE, 23));
        s.put(24, new Street("Red Straße", 200, Color.RED, 24));
        s.put(25, new Station("Bahnhoff", 200, 25));
        s.put(26, new Street("Yellow Straße", 200, Color.YELLOW, 26));
        s.put(27, new Street("Yellow Straße", 200, Color.YELLOW, 27));
        s.put(28, new PayExtra(200, ExtraFields.NAME_THREE, 28));
        s.put(29, new Street("Yellow Straße", 200, Color.YELLOW, 29));
        s.put(30, new Corner(CornerTyp.GO_TO_JAIL, 30));
        s.put(31, new Street("LIME Straße", 200, Color.LIME, 31));
        s.put(32, new Street("LIME Straße", 200, Color.LIME, 32));
        s.put(33, new GetCard(33));
        s.put(34, new Street("LIME Straße", 200, Color.LIME, 34));
        s.put(35, new Station("Bhanhoff", 200, 35));
        s.put(36, new GetCard(GetCard.ChanceColors.RED, 36));
        s.put(37, new Street("Dark Blue Straße", 200, Color.DARKBLUE, 37));
        s.put(38, new PayExtra(200, ExtraFields.NAME_FOUR, 38));
        s.put(39, new Street("Dark Blue Straße", 200, Color.DARKBLUE, 39));

        return s;
    }


    //Getter
    public Player getTurnPlayer() {
        return turnPlayer;
    }

    public GameDisplayControllerOne getDisplayControllerOne() {
        return displayControllerOne;
    }

    public GameDisplayControllerTwo getDisplayControllerTwo() {
        return displayControllerTwo;
    }

    public MiddleGameDisplayController getMiddleDisplayController() {
        return middleDisplayController;
    }

    public ArrayList<Player> getPlayers() {
        ArrayList<Player> ps = new ArrayList<>();

        if (playerOne != null)
            ps.add(playerOne);
        if (playerTwo != null)
            ps.add(playerTwo);
        if (playerThree != null)
            ps.add(playerThree);
        if (playerFour != null)
            ps.add(playerFour);
        if (playerFive != null)
            ps.add(playerFive);

        return ps;
    }

    public boolean isTurnPlayerIsMoving() {
        return turnPlayerIsMoving;
    }

    public static HashMap<Integer, Field> getFields() {
        return FIELDS;
    }

}

/* @// TODO: 21.03.2024
 * Hier werden die Sachen aufglisstet die das Spiel am Technischen noch können muss, nichts Grafisches.
 * 1. Nach 3 mal Pash ins Gefängnis
 * 2. Das Gameboard an sich ein biischen kleiner machen als die höhe des Fensters (Das heißt alle werte in dem einfach die Höhe anstat ein wert für die Gameboardgröße genommen wird ÄNDERN!!!)
 * ... (Hier stehen nur die Sachen die ich denke ich vergesse)
 */