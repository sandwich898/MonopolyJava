package sandwich.de.monopoly;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sandwich.de.monopoly.GUI.Game.Spielfeld;
import sandwich.de.monopoly.GUI.Menu.StartMenu;

import java.util.HashMap;

public class Main extends Application {

    //Important and final variables
    public static final String TEXT_FONT = "Clear Sans";
    public static final int START_BANK_ACCOUNT = 2000;
    public static final boolean CONSOLE_OUT_PUT = true;

    //Game variables
    public static HashMap<Integer, Color> streetColor = new HashMap<>();

    private static Game gameOperator;

    private static final double stageWidth = 1800, stageHeight = 950;

    private static Scene game;

    private static Scene menu;

    private static Stage primaryStage;
    @Override
    public void start(Stage stage) {

        primaryStage = stage;
        stage.setTitle("-M---o-----n----o---p----o---l----y");
        stage.setResizable(false);
        stage.setScene(menu);
        stage.show();
    }

    public static void main(String[] args) {

        streetColor.put(0, Color.DARKBLUE); //Straße
        streetColor.put(1, Color.DARKBLUE); //Straße
        streetColor.put(2, Color.AQUA);     //Straße
        streetColor.put(3, Color.AQUA);     //Straße
        streetColor.put(4, Color.AQUA);     //Straße
        streetColor.put(5, Color.PURPLE);   //Straße
        streetColor.put(6, Color.PURPLE);   //Straße
        streetColor.put(7, Color.PURPLE);   //Straße
        streetColor.put(8, Color.ORANGE);   //Straße
        streetColor.put(9, Color.ORANGE);   //Straße
        streetColor.put(10, Color.ORANGE);  //Straße
        streetColor.put(11, Color.RED);     //Straße
        streetColor.put(12, Color.RED);     //Straße
        streetColor.put(13, Color.RED);     //Straße
        streetColor.put(14, Color.YELLOW);  //Straße
        streetColor.put(15, Color.YELLOW);  //Straße
        streetColor.put(16, Color.YELLOW);  //Straße
        streetColor.put(17, Color.LIME);    //Straße
        streetColor.put(18, Color.LIME);    //Straße
        streetColor.put(19, Color.LIME);    //Straße
        streetColor.put(20, Color.rgb(112, 40, 0));    //Straße
        streetColor.put(21, Color.rgb(112, 40, 0));    //Straße
        streetColor.put(22, Color.GRAY);    //Anlagen
        streetColor.put(23, Color.BLACK);   //Bahnhöfe

        Spielfeld gameRoot = new Spielfeld(0, stageWidth, stageHeight, Color.rgb(204, 227, 199));
        game = new Scene(gameRoot, stageWidth, stageHeight, Color.BLACK);
        StartMenu menuRoot = new StartMenu(stageWidth, stageHeight);
        menu = new Scene(menuRoot, stageWidth, stageHeight, Color.BLACK);

        launch();


    }

    public static void changeScene(scenes s) {
        switch (s) {
            case GAME -> primaryStage.setScene(game);
            case MENU -> primaryStage.setScene(menu);
        }
    }

    public static Game getGameOperator() {
        return gameOperator;
    }

    public static void setGameOperator(Game gameOperator) {
        Main.gameOperator = gameOperator;
    }

    public enum scenes {
        MENU,
        GAME
    }
}

/*
   Fehler 001: 2 oder mehre Spieler haben die gleiche Figure ausgewählt!
   Fehler 002: 2 oder mehre Spieler haben den gleichen Namen ausgewählt!
   Fehler 003: Es ist im moment nur 1 Spieler erstellt!
   Fehler 004: Es sind im moment keine Spieler erstellt!
 */