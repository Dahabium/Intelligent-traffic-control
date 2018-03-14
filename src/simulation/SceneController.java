package simulation;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class SceneController {

    private static SceneController ourInstance = new SceneController();
    private ScrollPane ourScrollPane = new ScrollPane();
    int GAME_WINDOW_WIDTH = 612;
    int GAME_WINDOW_HEIGHT = 750;
    int START_SCREEN_WIDTH = 600;
    int START_SCREEN_HEIGHT = 500;
    int SETTINGS_MENU_WIDTH = 700;
    int SETTINGS_MENU_HEIGHT = 600;
    private StartMenu startMenuScene;
    private DrawMenu drawMenuScene;
    private SimulationScreen simulationScreen;

    private int boardPressX, boardPressY;
    public Pane boardPane;

    public static SceneController getInstance() {
        return ourInstance;
    }

    private SceneController() {}

    public StartMenu getStartMenuScene() {
        if (startMenuScene == null){
            startMenuScene = new StartMenu();
        }
        return startMenuScene;
    }

    public DrawMenu getDrawMenuScene() {
        if (drawMenuScene == null){
            drawMenuScene = new DrawMenu();
        }
        return drawMenuScene;
    }

    public SimulationScreen getSimulationScene() {
        if (simulationScreen == null){
            simulationScreen = new SimulationScreen();
        }
        return simulationScreen;
    }


}
