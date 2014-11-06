package main;

import java.awt.*;
import java.util.Observable;

/**
 * Created by hanshenrik on 23/10/14.
 */
public class FPModel extends Observable {

    public final int ROWS = 7;
    public final int COLS = 6;
    private boolean isWinningLine, isGameEndedByUser;
    private Disc startingDisc, nextDisc, previousWinner;
    private int playerOneWins, playerTwoWins;
    private int freeCells;
    private Cell[][] board;
    /*
     * The board is 'up side down' with respect to rows. This to make the layout
     * more intuitive. E.g., a 3x3 board would be indexed as such:
     *
     * [2,0]    [2,1]   [2,2]
     * [1,0]    [1,1]   [1,2]
     * [0,0]    [0,1]   [0,2]
     */

    public FPModel() {
        resetScore();
        newGame();
    }

    public void resetScore() {
        playerOneWins = 0;
        playerTwoWins = 0;
        setChanged();
        notifyObservers();
    }

    public String getScore() {
        return playerOneWins + "-" + playerTwoWins;
    }

    public void newGame() {
        isWinningLine = false;
        switchStarter();
        nextDisc = startingDisc;
        createBoard();
        freeCells = ROWS*COLS;
        setChanged();
        notifyObservers();
    }

    public void endGame() {
        isGameEndedByUser = true;
    }

    public boolean isGameOver() {
        return isGameEndedByUser || isWinningLine || isFullBoard();
    }

    public int playDisc(int col) {
        int row = findLowestFreeRow(col);

        if (row != -1 && !isGameOver()) {
            board[row][col].setDisc(nextDisc);
            freeCells--;
            switchTurn();
            setWinningLine(row, col);
            setChanged();
            notifyObservers();
        }
        return row;
    }

    private int findLowestFreeRow(int col) {
        for (int i = 0; i < ROWS; i++) {
            if (!board[i][col].hasDisc())
                return i;
        }
        return -1;
    }

    private void createBoard() {
        board = new Cell[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++)
                board[i][j] = new Cell();
        }
    }

    public Cell[][] getBoard() {
        return board;
    }

    private void increaseWins(Disc winningDisc) {
        if (winningDisc == Disc.PLAYER1)
            playerOneWins++;
        else if (winningDisc == Disc.PLAYER2)
            playerTwoWins++;
    }

    private void switchTurn() {
        nextDisc = (nextDisc == Disc.PLAYER1) ? Disc.PLAYER2 : Disc.PLAYER1;
    }

    private void switchStarter() {
        startingDisc = (startingDisc == Disc.PLAYER1) ? Disc.PLAYER2 : Disc.PLAYER1;
    }

    private void setWinningLine(int row, int col) {
        Point   horizontalStart = new Point(row, 0),
                horizontalEnd = new Point(row, COLS - 1),
                verticalStart = new Point(0, col),
                verticalEnd = new Point(ROWS - 1, col),
                northEastSearchStart = getNorthEastSearchStartPoint(row, col),
                northEastSearchEnd = new Point(ROWS - 1, COLS - 1),
                northWestSearchStart = getNorthWestSearchStartPoint(row, col),
                northWestSearchEnd = new Point(ROWS - 1, 0);

        isWinningLine =
            checkLine(horizontalStart, horizontalEnd) || // horizontal
            checkLine(verticalStart, verticalEnd) || // vertical
            checkLine(northEastSearchStart, northEastSearchEnd) ||
            checkLine(northWestSearchStart, northWestSearchEnd);
    }

    private Point getNorthEastSearchStartPoint(int row, int col) {
        Point startPoint = new Point(row, col);
        while ( (startPoint.x != 0) && (startPoint.y != 0) ) {
            startPoint.x--;
            startPoint.y--;
        }
        return startPoint;
    }

    private Point getNorthWestSearchStartPoint(int row, int col) {
        Point startPoint = new Point(row, col);
        while ( (startPoint.x != 0) && (startPoint.y != COLS - 1) ) {
            startPoint.x--;
            startPoint.y++;
        }
        return startPoint;
    }

    // Change to checking right, left, right, left... up, down, up, down...
    // ne, sw, ne, sw... nw, se, nw, se... until wrong disc on both sides!
    private boolean checkLine(Point start, Point end) {
        int similarInARow = 0;
        int dirX = 0;
        int dirY = 0;
        Disc prevDisc = null;
        Disc curDisc;

        if (start.x < end.x)
            dirX = 1;
        if (start.y < end.y)
            dirY = 1;
        else if (start.y > end.y)
            dirY = -1;

        int x = start.x;
        int y = start.y;
        while ( x < ROWS && ( (y < COLS && dirY >= 0) || (y >= 0 && dirY == -1) ) ) {
            curDisc = board[x][y].getDisc();
            if (curDisc == prevDisc && curDisc != null)
                similarInARow++;
            else
                similarInARow = 1;

            if (similarInARow == 4) {
                increaseWins(curDisc);
                previousWinner = curDisc;
                return true;
            }
            prevDisc = curDisc;
            x += dirX;
            y += dirY;
        }
        return false;
    }

    public Disc getStartingDisc() {
        return startingDisc;
    }

    public Disc getNextDisc() {
        return nextDisc;
    }

    public Disc getPreviousWinner() {
        return previousWinner;
    }

    public boolean isWinningLine() {
        return isWinningLine;
    }

    public boolean isFullBoard() {
        return freeCells == 0;
    }
}

