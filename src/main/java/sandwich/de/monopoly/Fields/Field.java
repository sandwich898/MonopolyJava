package sandwich.de.monopoly.Fields;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public abstract class Field {

    private final double POS;

    private Line[] borderLines;

    protected final Color BORDER_COLOR = Color.BLACK;

    protected Pane field;

    public Field(double postion) {
        POS = postion;
    }

    public Pane buildField(double width, double height, double borderWidth, double fontSize, Color backgroundColor) {
        return new Pane();
    }

    public double getPosition() {
        return POS;
    }


}
