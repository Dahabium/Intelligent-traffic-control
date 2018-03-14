package simulation;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;


public class SimulationScreen extends Scene {

    static int WINDOW_WIDTH = 1200;
    static int WINDOW_HEIGHT = 800;

    public SimulationScreen() {
        super(new GridPane(), WINDOW_WIDTH, WINDOW_HEIGHT);


        GridPane MenuLayout = new GridPane();
        MenuLayout.setPadding(new Insets(10,10,10,10));
        MenuLayout.setVgap(WINDOW_WIDTH /3);
        MenuLayout.setHgap(WINDOW_HEIGHT /3);

        setRoot(MenuLayout);
    }

}
