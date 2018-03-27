package simulation;

import javafx.animation.PathTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.security.acl.Group;
import java.util.ArrayList;

public class AnimationParts {
    ArrayList<Integer> IntPath;
    Path path;

    Circle agent;

    public AnimationParts(ArrayList<Integer> IntPath, Graph graph, Board board) {

        this.IntPath = IntPath;
        this.agent = new Circle(25);
        this.path = new Path();
        agent.setFill(Color.RED);


        for (int i = 0; i < IntPath.size(); i++) {
            if (i == 0){
                //set the start of the car movement animation
                MoveTo moveTo = new MoveTo(graph.getNodeByIndex(IntPath.get(i)).x * board.SIM_SIZE + board.SIM_SIZE / 2, graph.getNodeByIndex(IntPath.get(i)).y * board.SIM_SIZE + board.SIM_SIZE/2);
                path.getElements().add(moveTo);
            }
            else {
                LineTo lineTo = new LineTo(graph.getNodeByIndex(IntPath.get(i)).x * board.SIM_SIZE + board.SIM_SIZE / 2 , graph.getNodeByIndex(IntPath.get(i)).y * board.SIM_SIZE + board.SIM_SIZE/2 );
                path.getElements().add(lineTo);
            }

        }


        PathTransition transition = new PathTransition();
        transition.setDuration(Duration.millis(10000));
        transition.setNode(this.agent);
        transition.setPath(this.path);

//        transition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

        transition.setCycleCount(2);

        //Setting auto reverse value to true
        transition.setAutoReverse(false);

        //Playing the animation
        transition.play();


    }

    public Circle getAnimatedCircle() {
        return this.agent;
    }
}
