package simulation;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends StackPane {

    public Tile(){

        Rectangle border = new Rectangle(50, 50);
        border.setFill(null);

        //Essential to represent the grid
        border.setStroke(Color.BLACK);

        setAlignment(Pos.CENTER);
        getChildren().add(border);
    }


}
