package simulation;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class DrawMenu extends Scene {

    static int WINDOW_WIDTH = 320;
    static int WINDOW_HEIGHT = 400;

    public DrawMenu() {

        super(new GridPane(), WINDOW_WIDTH, WINDOW_HEIGHT);

        GridPane MenuLayout = new GridPane();
        MenuLayout.setPadding(new Insets(10,10,10,10));
        MenuLayout.setVgap(WINDOW_WIDTH /3);
        MenuLayout.setHgap(WINDOW_HEIGHT /3);


        setRoot(MenuLayout);
    }

}
