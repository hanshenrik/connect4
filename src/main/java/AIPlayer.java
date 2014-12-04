package main.java;

import java.awt.*;
import java.util.Random;

/**
 * Created by hanshenrik on 04/12/14.
 */
public class AIPlayer {
    private FPModel model;
    private int col;
    private Disc[][] board;

    public AIPlayer(FPModel model) {
        this.model = model;
        this.col = 0;
    }

    public int simple() {
        return col++ % FPModel.COLS;
    }

    public int random() {
        Random r = new Random();
        return r.nextInt(FPModel.COLS - 1);
    }

    public int sortOfBlockingHorVer(int prevPlayedCol) {
        board = model.getBoard();

        col = getTwoInARow(prevPlayedCol, model.findLowestFreeRow(prevPlayedCol) - 1);
        return col == -1 ? random() : col;
    }

    /* checking logic from model, but instead of checking for winning line, check for 2 in a row of Disc.PLAYER1 */
    private int getTwoInARow(int row, int col) {
        System.out.println(row + ", " + col);
        int h = checkLine(new Point(row, 0), new Point(row, FPModel.COLS - 1)); // horizontal
        int v = checkLine(new Point(0, col), new Point(FPModel.ROWS - 1, col)); // vertical
        return h >= 0 ? h : v;
    }

    private int checkLine(Point start, Point end) {
        int similarInARow = 0;
        int dirX = 0;
        int dirY = 0;
        Disc prevDisc = Disc.PLAYER1;
        Disc curDisc;

        if (start.x < end.x)
            dirX = 1;
        if (start.y < end.y)
            dirY = 1;
        else if (start.y > end.y)
            dirY = -1;

        int x = start.x;
        int y = start.y;

        while ( x < FPModel.ROWS && ( (y < FPModel.COLS && dirY >= 0) || (y >= 0 && dirY == -1) ) ) {
            curDisc = board[x][y];
            if (curDisc == prevDisc)
                similarInARow++;
            else
                similarInARow = 1;

            if (similarInARow == 2) {
                return (x+1 >= FPModel.COLS) ? x-1 : x+1;
            }
            x += dirX;
            y += dirY;
        }
        return -1;
    }
}
