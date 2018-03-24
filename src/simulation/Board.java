package simulation;

import javafx.scene.layout.GridPane;

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



}
