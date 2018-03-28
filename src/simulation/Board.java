package simulation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.util.ArrayList;

public class Board extends GridPane {

    int SIM_SIZE = 100;
    int xSize;
    int ySize;

    ArrayList<double[]> grid;


    Tile[][] board;
    private boolean empty;

    public Board(int xSize, int ySize) {

        this.xSize = xSize;
        this.ySize = ySize;
        board = new Tile[xSize][ySize];
        empty = true;

        grid = new ArrayList<>();
        doGrid(xSize, ySize);

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
        GridPane.setConstraints(tile, x, y);
        getChildren().add(tile);
        empty = false;
        return true;
    }

    public Tile getTileAtCoordinates(int x, int y) {
        return this.board[x][y];
    }

    public void setBoard(Graph graph) {

        graph.setSubIntersections();

        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {

                javafx.scene.image.Image bgImage = new javafx.scene.image.Image("background.JPG", SIM_SIZE, SIM_SIZE, false, true);
                ImageView background = new ImageView(bgImage);

                Tile tempTile = new Tile();
                tempTile.getChildren().add(background);
                setTileAtCoordinates(tempTile, j, i);

            }
        }


        for (int i = 0; i < graph.nodes.size(); i++) {
            System.out.println("Nodes X & Y positions respectively are: " + graph.nodes.get(i).x + "x " + graph.nodes.get(i).y + "y ");
            Tile tile = new Tile();
            ImageView imgView;

            if (graph.nodes.get(i).type == 0) {
                javafx.scene.image.Image img = new javafx.scene.image.Image("intersection.JPG", SIM_SIZE, SIM_SIZE, true, false);
                imgView = new ImageView(img);
                tile.getChildren().add(imgView);
            }
            if (graph.nodes.get(i).type == 1) {
                javafx.scene.image.Image img = new javafx.scene.image.Image("roundabout.png", SIM_SIZE, SIM_SIZE, true, false);
                imgView = new ImageView(img);
                tile.getChildren().add(imgView);
            }
            if (graph.nodes.get(i).type == 2) {
                javafx.scene.image.Image img = new javafx.scene.image.Image("intersection.JPG", SIM_SIZE, SIM_SIZE, true, false);
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
                    int edgeY = startY + (endY - startY)/2;
                    int distance = Math.abs(endX-startX);
                    javafx.scene.image.Image img = new javafx.scene.image.Image("Road.PNG", SIM_SIZE, SIM_SIZE*0.145, true, false);


                    if( endX - startX < 0)
                    {

                        for(int j = 1; j<distance; j++) {
                            ImageView imgView = new ImageView(img);
                            board[endX+j][edgeY].getChildren().add(imgView);
                        }
                    }

                    else if(endX - startX > 0)
                    {

                        for(int j = 1; j<distance; j++) {
                            ImageView imgView = new ImageView(img);
                            board[startX+j][edgeY].getChildren().add(imgView);
                        }
                    }
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

                    javafx.scene.image.Image img = new javafx.scene.image.Image("Road.PNG", SIM_SIZE, SIM_SIZE*0.145, true, false);


                    //checks how far apart the nodes are
                    int distance = Math.abs(endY-startY);
                    if( endY - startY < 0)
                    {

                        for(int j = 1; j<distance; j++) {
                            ImageView imgView = new ImageView(img);
                            imgView.setRotate(90);
                            board[edgeX][endY+j].getChildren().add(imgView);
                        }
                    }

                    else if(endY - startY > 0)
                    {

                        for(int j = 1; j<distance; j++) {
                            ImageView imgView = new ImageView(img);
                            imgView.setRotate(90);
                            board[edgeX][startY+j].getChildren().add(imgView);
                        }
                    }

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

    public void doGrid(int xSize, int ySize) {

        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                double x = j * 51 + 46.5;
                double y = i * 51 + 126.5;
                double[] xy = new double[4];
                xy[0] = x;
                xy[1] = y;
                xy[2] = j;
                xy[3] = i;
                grid.add(xy);
            }
        }
    }


    public double[] getGridXY(double x, double y) {
        double[] closest = new double[4];
        double minimum = 9999999;
        for (int i = 0; i < grid.size(); i++) {
            double diff = Math.abs(grid.get(i)[0] - x) + Math.abs(grid.get(i)[1] - y);

            if (diff < minimum) {
                minimum = diff;
                closest = grid.get(i);
            }
        }

        return closest;

    }


    public void resetBoard() {
        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                board[j][i] = null;
            }
        }
    }


}
