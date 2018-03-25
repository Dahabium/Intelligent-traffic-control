package simulation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.awt.*;

public class Board extends GridPane{

    int DEFAULT_SIZE = 25;
    int xSize;
    int ySize;

    Tile[][] board;
    private boolean empty;

    public Board(int xSize, int ySize){
        this.xSize = xSize;
        this.ySize = ySize;
        board = new Tile[xSize][ySize];
        empty = true;
        //initBoard();
    }

    public int getBoardSizeX() {
        return xSize;
    }

    public int getBoardSizeY() {
        return ySize;
    }

    public boolean setTileAtCoordinates(Tile tile, int x, int y) {
        board[x][y] = tile;
        GridPane.setConstraints(tile,x,y);
        getChildren().add(tile);
        empty = false;
        return true;
    }

    public void setBoard(Graph graph)
    {

        for(int i = 0; i<ySize; i++)
        {
            for(int j = 0; j<xSize; j++)
            {

                javafx.scene.image.Image bgImage = new javafx.scene.image.Image("background.JPG", 150, 150, false, true);
                ImageView background = new ImageView(bgImage);

                Tile tempTile = new Tile();
                tempTile.getChildren().add(background);
                setTileAtCoordinates(tempTile, j, i);

            }
        }



        for(int i = 0; i<graph.nodes.size(); i++)
        {
            System.out.println("Nodes X & Y positions respectively are: " + graph.nodes.get(i).x + "x " + graph.nodes.get(i).y + "y ");
            Tile tile = new Tile();
            ImageView imgView;

            if(graph.nodes.get(i).type == 0)
            {
                javafx.scene.image.Image img = new javafx.scene.image.Image("intersection.JPG", 150,150,true,false);
                imgView = new ImageView(img);
                tile.getChildren().add(imgView);
            }
            if(graph.nodes.get(i).type == 1)
            {
                javafx.scene.image.Image img = new javafx.scene.image.Image("roundabout.JPG", 150,150,true,false);
                imgView = new ImageView(img);
                tile.getChildren().add(imgView);
            }
            if(graph.nodes.get(i).type == 2)
            {
                javafx.scene.image.Image img = new javafx.scene.image.Image("intersection.JPG", 150,150,true,false);
                imgView = new ImageView(img);
                tile.getChildren().add(imgView);
            }

            setTileAtCoordinates(tile, graph.nodes.get(i).x, graph.nodes.get(i).y);
        }

        System.out.println("There are " + graph.edges.size() + " edges");
        for(int i = 0; i<graph.edges.size(); i++)
        {


            if(graph.edges.get(i).type == 0)
            {

                Node start = graph.edges.get(i).start;
                Node end = graph.edges.get(i).end;
                if(start.Xpos < end.Xpos || start.Xpos > end.Xpos)
                {
                    int startX = graph.edges.get(i).start.x;
                    int endX = graph.edges.get(i).end.x;
                    int startY = graph.edges.get(i).start.y;
                    int endY = graph.edges.get(i).end.y;
                    int edgeX = startX + (endX - startX)/2;
                    int edgeY = startY + (endY-startY)/2;

                    javafx.scene.image.Image img = new javafx.scene.image.Image("Road.PNG", 150, 21.75, true, false);
                    ImageView imgView = new ImageView(img);
                    board[edgeX][edgeY].getChildren().add(imgView);

                    System.out.println("Edge drawn at board["+edgeX+"]["+ edgeY+ "]");

                }
                if(start.Ypos < end.Ypos || start.Ypos > end.Ypos)
                {
                    int startX = graph.edges.get(i).start.x;
                    int endX = graph.edges.get(i).end.x;
                    int startY = graph.edges.get(i).start.y;
                    int endY = graph.edges.get(i).end.y;
                    int edgeX = startX + (endX - startX)/2;
                    int edgeY = startY + (endY-startY)/2;

                    javafx.scene.image.Image img = new javafx.scene.image.Image("Road.PNG", 150, 21.75, true, false);
                    ImageView imgView = new ImageView(img);
                    imgView.setRotate(90);
                    board[edgeX][edgeY].getChildren().add(imgView);

                    System.out.println("Edge drawn at board["+edgeX+"]["+ edgeY+ "]");
                }

            }


        }

//        javafx.scene.image.Image bckgrnd = new javafx.scene.image.Image("background.JPG", 100,100,false,false);
//        ImageView background = new ImageView(bckgrnd);
//        for(int i = 0; i<ySize; i++)
//        {
//            for(int j = 0; j<xSize; j++)
//            {
//                if(board[j][i] == null) {
//                    Tile tile = new Tile();
//                    tile.getChildren().add(background);
//                    setTileAtCoordinates(tile, j, i);
//                }
//            }
//        }

    }

    public void resetBoard()
    {
        for(int i = 0; i<ySize; i++)
        {
            for(int j = 0; j<xSize; j++)
            {
                board[j][i] = null;
            }
        }
    }




}
