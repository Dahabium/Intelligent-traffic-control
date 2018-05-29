package simulation;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board extends GridPane {

    int SIM_SIZE = 100;
    int xSize;
    int ySize;
    Graph graph;
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

    public void addIntersectionGUI(int x, int y, int squareSize){

        javafx.scene.image.Image img = new javafx.scene.image.Image("intersection.png",  squareSize, squareSize,true,false);
        ImageView imgView = new ImageView(img);

        this.getTileAtCoordinates(x,y).getChildren().add(imgView);
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

        this.graph = graph;
        graph.setSubIntersections();

        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {

                javafx.scene.image.Image bgImage = new javafx.scene.image.Image("background.JPG", SIM_SIZE, SIM_SIZE, false, true);
                ImageView background = new ImageView(bgImage);

                Tile tempTile = new Tile(SIM_SIZE);
                tempTile.getChildren().add(background);
                setTileAtCoordinates(tempTile, j, i);

            }
        }


        for(Node node : graph.nodes) {
            int roads = getAmountOfRoads(node);
            System.out.println("Nodes X & Y positions respectively are: " + node.x + "x " + node.y + "y ");
            Tile tile = new Tile(SIM_SIZE);
            ImageView imgView;

            //intersections that have only one edge will show as a road!! (type 4)


            if(roads == 4 || roads == 0)
            {
                javafx.scene.image.Image img = new javafx.scene.image.Image("Intersection.png", SIM_SIZE, SIM_SIZE, true, false);

                imgView = new ImageView(img);
                tile.getChildren().add(imgView);
            }

            if(roads == 2 || roads == 1)
            {
                javafx.scene.image.Image img = new javafx.scene.image.Image("Road.png",  SIM_SIZE, SIM_SIZE,true,false);
                imgView = new ImageView(img);

                if((node.up && !node.down) || (node.down && !node.up) || (node.up && node.down))
                {
                    imgView.setRotate(90);
                }

                if(node.left && node.up)
                {
                    img = new javafx.scene.image.Image("Corner.png",  SIM_SIZE, SIM_SIZE,true,false);
                    imgView = new ImageView(img);
                    imgView.setRotate(90);
                }
                if(node.up && node.right)
                {
                    img = new javafx.scene.image.Image("Corner.png",  SIM_SIZE, SIM_SIZE,true,false);
                    imgView = new ImageView(img);
                    imgView.setRotate(180);
                }
                if(node.right && node.down)
                {
                    img = new javafx.scene.image.Image("Corner.png",  SIM_SIZE, SIM_SIZE,true,false);
                    imgView = new ImageView(img);
                    imgView.setRotate(270);
                }
                if(node.left && node.down)
                {
                    img = new javafx.scene.image.Image("Corner.png",  SIM_SIZE, SIM_SIZE,true,false);
                    imgView = new ImageView(img);
                    imgView.setRotate(360);
                }

                tile.getChildren().add(imgView);
            }

            if(roads == 3)
            {
                javafx.scene.image.Image img = new javafx.scene.image.Image("Tsection.png",  SIM_SIZE, SIM_SIZE,true,false);
                imgView = new ImageView(img);
                if(!node.left)
                {
                    imgView.setRotate(270);
                }
                if(!node.down)
                {
                    imgView.setRotate(180);
                }
                if(!node.up)
                {
                    imgView.setRotate(360);
                }
                if(!node.right)
                {
                    imgView.setRotate(90);
                }

                tile.getChildren().add(imgView);
            }

            setTileAtCoordinates(tile, node.x, node.y);
        }


//        System.out.println("There are " + graph.edges.size() + " edges");
//        for(int i = 0; i<graph.edges.size(); i++)
//        {
//            if(graph.edges.get(i).type == 0)
//            {
//                Node start = graph.edges.get(i).start;
//                Node end = graph.edges.get(i).end;
//                if(start.Xpos < end.Xpos || start.Xpos > end.Xpos)
//                {
//                    int startX = graph.edges.get(i).start.x;
//                    int endX = graph.edges.get(i).end.x;
//                    int startY = graph.edges.get(i).start.y;
//                    int endY = graph.edges.get(i).end.y;
//                    int edgeX = startX + (endX - startX)/2;
//                    int edgeY = startY + (endY - startY)/2;
//                    int distance = Math.abs(endX-startX);
//
//                    javafx.scene.image.Image img = new javafx.scene.image.Image("Road.png", SIM_SIZE, SIM_SIZE, true, false);
//
//
//
//                    if( endX - startX < 0)
//                    {
//
//                        for(int j = 1; j<distance; j++) {
//                            ImageView imgView = new ImageView(img);
//                            board[endX+j][edgeY].getChildren().add(imgView);
//                        }
//                    }
//
//                    else if(endX - startX > 0)
//                    {
//
//                        for(int j = 1; j<distance; j++) {
//                            ImageView imgView = new ImageView(img);
//                            board[startX+j][edgeY].getChildren().add(imgView);
//                        }
//                    }
//                    System.out.println("Edge drawn at board["+edgeX+"]["+ edgeY+ "]");
//
//                }
//                if(start.Ypos < end.Ypos || start.Ypos > end.Ypos)
//                {
//                    int startX = graph.edges.get(i).start.x;
//                    int endX = graph.edges.get(i).end.x;
//                    int startY = graph.edges.get(i).start.y;
//                    int endY = graph.edges.get(i).end.y;
//                    int edgeX = startX + (endX - startX)/2;
//                    int edgeY = startY + (endY-startY)/2;
//
//
//                    javafx.scene.image.Image img = new javafx.scene.image.Image("Road.PNG", SIM_SIZE, SIM_SIZE, true, false);
//
//
//
//                    //checks how far apart the nodes are
//                    int distance = Math.abs(endY-startY);
//                    if( endY - startY < 0)
//                    {
//
//                        for(int j = 1; j<distance; j++) {
//                            ImageView imgView = new ImageView(img);
//                            imgView.setRotate(90);
//                            board[edgeX][endY+j].getChildren().add(imgView);
//                        }
//                    }
//                    else if(endY - startY > 0)
//                    {
//
//                        for(int j = 1; j<distance; j++) {
//                            ImageView imgView = new ImageView(img);
//                            imgView.setRotate(90);
//                            board[edgeX][startY+j].getChildren().add(imgView);
//                        }
//                    }
//
//                    System.out.println("Edge drawn at board["+edgeX+"]["+edgeY+ "]");
//                }
//
//            }
//
//
//        }

        initiateLanes();

    }

    public void initiateLanes()
    {
        for(Node n: graph.nodes)
        {
            List<Node> l = graph.getAdjecents(n);
               for(Node m : l)
               {
                   if(m!=n)
                   {
                       int amount = getAmountOfLanes(n, m);
                       if(amount > 0) drawLanes(amount, n, m);
                   }

               }
        }
    }

    public void drawLanes(int amount, Node from, Node to)
    {
        int gridX = (from.x + to.x)/2;
        int gridY = (from.y + to.y)/2;
        javafx.scene.image.Image img = new javafx.scene.image.Image("Road.PNG", SIM_SIZE, SIM_SIZE, true, false);

        if(amount == 1)
        {
            img = new javafx.scene.image.Image("one.png", SIM_SIZE, SIM_SIZE, true, false);

        }
        else if(amount == 2)
        {
            img = new javafx.scene.image.Image("Road.PNG", SIM_SIZE, SIM_SIZE, true, false);

        }
        else if(amount == 3)
        {
            img = new javafx.scene.image.Image("three.png", SIM_SIZE, SIM_SIZE, true, false);

        }
        else if(amount == 4)
        {
            img = new javafx.scene.image.Image("four.png", SIM_SIZE, SIM_SIZE, true, false);

        }
        else if(amount >= 5)
        {
            img = new javafx.scene.image.Image("five.png", SIM_SIZE, SIM_SIZE, true, false);

        }

        ImageView imgView = new ImageView(img);

        if(from.y != to.y)
        {
            imgView.setRotate(90);
            if(amount == 1 && graph.getAdjecents(from).contains(to) && from.y>to.y) imgView.setRotate(180);
            if(amount == 1 && graph.getAdjecents(to).contains(from) && from.y<to.y) imgView.setRotate(180);
        }

        else
        {
            if(amount == 1 && graph.getAdjecents(from).contains(to) && from.x>to.x) imgView.setRotate(180);
            if(amount == 1 && graph.getAdjecents(to).contains(from) && from.x<to.x) imgView.setRotate(180);
        }

        board[gridX][gridY].getChildren().add(imgView);
    }

    public int getAmountOfLanes(Node n, Node m)
    {
        int counter = 0;

        for(Edge e : graph.edges)
        {
            if(e.start == n && e.end == m) counter ++;
            if(e.start == m && e.end == n) counter++;
        }

        return counter;
    }



    //NOT THE SAME AS AMOUNT OF LANES
    public int getAmountOfRoads(Node n)
    {
        List<Edge> tempList = new ArrayList<>();
        for(Edge e : graph.edges)
        {
            tempList.add(e);
        }

        int counter = 0;

        //Deletes all duplicate edges, then counts how many point to Node n (max 5)
        ArrayList<Edge> visited = new ArrayList<>();

        for(Edge e : graph.edges)
        {
            visited.add(e);
            for(Edge f : graph.edges)
            {   // if f is not e, e has a connection with Node n and f is  the direction of e or f is the opposite direction of e -> counter --
                if(!visited.contains(f) && ((f.start == e.start && f.end == e.end) || (f.start == e.end && f.end == e.start))) {tempList.remove(f);}
            }
        }

        for(Edge e : tempList)
        {
            if(e.start == n || e.end == n) counter++;
        }

        System.out.println("Node " + n.getIndex() + " has " + counter + " different roads");
        return counter;
    }

    public void doGrid(int xSize, int ySize) {

        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                double x = j * (SIM_SIZE + 1) + 50;
                double y = i * (SIM_SIZE + 1) + 100;
                double[] xy = new double[4];
                xy[0] = x;
                xy[1] = y;
                xy[2] = j;
                xy[3] = i;
                grid.add(xy);
            }
        }
    }


    public double[] getGridXY(double x, double y, int CELL_SIZE) {

        double[] closest = new double[4];
        double minimum = 9999999;

        System.out.println("x " + x + " y" + y);

        for (int i = 0; i < grid.size(); i++) {

            double diff = Math.abs(grid.get(i)[0] - x) + Math.abs(grid.get(i)[1] - y);

            if (diff < minimum) {
                minimum = diff;
                closest = grid.get(i);
            }
        }

        System.out.println(" closest "+  Arrays.toString(closest));
        return closest;

    }

}
