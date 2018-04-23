package simulation;


import javafx.scene.control.Button;

public class Controller {
    public Controller(){}

    public void control(Button button){

    }

    public void drawGraphControl(Button button){
        button.setOnAction(e -> {
//            graph = new Graph();
//            board = new Board(30,10);
//            primaryStage.setScene(drawScene);
            System.out.println("CHECK!");
        });
    }
}
