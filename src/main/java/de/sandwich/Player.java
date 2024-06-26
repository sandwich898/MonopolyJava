package de.sandwich;

import de.sandwich.DennisUtilitiesPackage.Java.ConsoleUtilities;
import de.sandwich.Enums.Figuren;
import de.sandwich.Enums.Values;
import de.sandwich.Exceptions.NumberIsToBigLowExceptions;
import de.sandwich.Exceptions.PlayerNotFoundExceptions;
import de.sandwich.Fields.Field;
import de.sandwich.Fields.Station;
import de.sandwich.Fields.Street;
import de.sandwich.Fields.Utilitie;
import de.sandwich.GUI.Game.Displays.DisplayOne.BuildDisplay;

import static de.sandwich.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPut;
import static de.sandwich.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPutLine;

import java.util.HashMap;

public class Player {

    //Final player variables
    private final String name;
    private final int orderNumber;
    private final Figuren figur;

    //Game variables
    private boolean isInJail = false;
    private boolean hasGivenUp = false;
    private int freeJailCards = Values.PLAYER_FREEJAILCARDS_START.getValue();
    private int inJailRemain = Values.PLAYER_ROUNDS_IN_JAIL.getValue();
    private int bankAccount;
    private int fieldPostion;

    public Player(String name, @SuppressWarnings("exports") Figuren f, int orderNumber) {
        this.name = name;
        this.figur = f;
        this.orderNumber = orderNumber;

        //Start account
        bankAccount = Values.PLAYER_START_BANKACCOUNT.getValue();
        fieldPostion = Values.PLAYER_START_POSTION.getValue();
    }

    public void transferMoneyToBankAccount(int transferNumber) {
        bankAccount = bankAccount + transferNumber;
        try { Main.getGameOperator().getDisplayControllerOne().updateDisplay(); } catch (PlayerNotFoundExceptions ignored) {}
    }

    public void useFreeJailCard() {
        if (freeJailCards > 0) {
            freeJailCards--;
            removePlayerFromJail();
        } else throw new NumberIsToBigLowExceptions(false, "UsFreeJailCard, not enough Cards!");
    }

    public void addFreeJailCard() {
        if (freeJailCards < 4)
            freeJailCards++;
        else throw new NumberIsToBigLowExceptions(true, "addFreeJailCard");
    }

    public void setInJail(boolean inJail) {
        isInJail = inJail;
        if (inJail) {
            try { Main.getGameOperator().getBoard().setPlayerInJail(this); } catch (PlayerNotFoundExceptions e) { e.printStackTrace(); }

            inJailRemain = 4;
            fieldPostion = 10;
        }
    }

    public void removeOnInJailRemain() {
        this.inJailRemain--;
    }

    public void removePlayerFromJail() {
        try { Main.getGameOperator().getBoard().removePlayerFromJail(this); } catch (PlayerNotFoundExceptions e) { e.printStackTrace(); }

        inJailRemain = 0;
        isInJail = false;
    }

    public void giveUp() {
        hasGivenUp = true;

        Main.getGameOperator();
        HashMap<Integer, Field> FIELDS = Game.getFields();

        for (int i = 0; i < FIELDS.size(); i++) {
                if (FIELDS.get(i) instanceof Street f) {
                    if (f.getOwner() == this) {
                        if (f.getHouseNumber() > 0)
                            BuildDisplay.setHousesRemain(BuildDisplay.getHousesRemain() + f.getHouseNumber());
                        f.setOwner(null);
                    }
                } else if (FIELDS.get(i) instanceof Utilitie f) {
                    if (f.getOwner() == this)
                        f.setOwner(null);
                } if (FIELDS.get(i) instanceof Station f) {
                    if (f.getOwner() == this)
                        f.setOwner(null);
                }
            }

    }

    public void moveFieldPostion(int postions) {
        fieldPostion = fieldPostion + postions;
        if (fieldPostion >= 40) {
            fieldPostion = fieldPostion - 40;

            transferMoneyToBankAccount(4000);

            if (Main.CONSOLE_OUT_PUT) {
                consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Der Spieler nummer " + orderNumber + " ist über los gegangen und hat");
                consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, " 4000€ Plus gemacht!");
                consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
            }
        }

    }

    public String getName() {
        return name;
    }

    public int getBankAccount() {
        return bankAccount;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public int getFieldPostion() {
        return fieldPostion;
    }

    public int getInJailRemain() {
        return inJailRemain;
    }

    public int getFreeJailCardNumber() {
        return freeJailCards;
    }

    @SuppressWarnings("exports")
    public Figuren getFigur() {
        return figur;
    }

    public boolean isInJail() {
        return isInJail;
    }

    public boolean hasGivenUp() {
        return hasGivenUp;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Figure: " + figur.toString().toLowerCase() + ", FieldPosition: " + fieldPostion + ", Kontostand: " + bankAccount;
    }

}
