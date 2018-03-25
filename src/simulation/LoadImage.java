package simulation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LoadImage{

    int nodeSize, edgeSize;

    public LoadImage(int nodeSize, int edgeSize)
    {
        this.nodeSize = nodeSize;
        this.edgeSize = edgeSize;
    }

    public ArrayList<ImageView> getImage() {


        ArrayList<ImageView> list = new ArrayList<>();
        Image img = new Image("background.JPG", 3000,1000,true,false);
        ImageView imgView = new ImageView(img);
       // imgView.preserveRatioProperty();
        list.add(imgView);


        for(int i = 0; i<nodeSize; i++)
        {
            Image tempImg = new Image("Intersection.JPG", 100, 100, true, false);
            ImageView tempImgView = new ImageView(tempImg);
            list.add(tempImgView);
        }

        for(int i = 0; i<edgeSize; i++)
        {
            Image tempImg = new Image("Knipsel.JPG", 239, 27, true, false);
        }
        return list;

        //Adding HBox to the scene

    }
}