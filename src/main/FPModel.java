package main;

import java.util.Observable;

/**
 * Created by hanshenrik on 23/10/14.
 */
public class FPModel extends Observable {

    private final int ROWS = 7;
    private final int COLS = 6;
    private boolean isWinningLine;
    private Disc startingDisc, nextDisc, lastWinner;
    private int playerOneWins, playerTwoWins;
    private String message;
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
        setChanged();
        notifyObservers();
    }

    // sufficient to disable all actions but newGame in View/Controller if the endGame button is pushed?
    public void endGame() {
        // TODO
    }

    public void addDisc(int col) {
        int row = findLowestFreeRow(col);

        // TODO should checks like this be done here? And should it only set a message?
        if (row == -1)
            setMessage("Column " + col + " is full.");
        else if (isWinningLine)
            setMessage("Winning line exists.");
        else if (isFullBoard())
            setMessage("Board is full.");
        else {
            board[row][col].setDisc(nextDisc);
            switchTurn();
            checkVictory(row, col);
            setMessage("");
        }
        setChanged();
        notifyObservers();
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
        else
            setMessage("Unidentified winning disc: " + winningDisc);
    }

    private void switchTurn() {
        nextDisc = (nextDisc == Disc.PLAYER1) ? Disc.PLAYER2 : Disc.PLAYER1;
    }

    private void switchStarter() {
        startingDisc = (startingDisc == Disc.PLAYER1) ? Disc.PLAYER2 : Disc.PLAYER1;
    }

    private void checkVictory(int row, int col) {
        Point northEastSearchStartPoint = getNorthEastSearchStartPoint(row, col);
        Point northWestSearchStartPoint = getNorthWestSearchStartPoint(row, col);

        isWinningLine =
            checkLine(row, 0, row, COLS - 1) || // horizontal
            checkLine(0, col, ROWS - 1, col) || // vertical
            checkLine(northEastSearchStartPoint.getX(), northEastSearchStartPoint.getY(), ROWS - 1, COLS - 1) ||
            checkLine(northWestSearchStartPoint.getX(), northWestSearchStartPoint.getY(), ROWS - 1, 0);
    }

    private Point getNorthEastSearchStartPoint(int row, int col) {
        Point startPoint = new Point(row, col);
        while ( (startPoint.getX() != 0) && (startPoint.getY() != 0) ) {
            startPoint.decreaseX();
            startPoint.decreaseY();
        }
        return startPoint;
    }

    private Point getNorthWestSearchStartPoint(int row, int col) {
        Point startPoint = new Point(row, col);
        while ( (startPoint.getX() != 0) && (startPoint.getY() != COLS - 1) ) {
            startPoint.decreaseX();
            startPoint.increaseY();
        }
        return startPoint;
    }

    private boolean checkLine(int startX, int startY, int endX, int endY) {
        int similarInARow = 0;
        int dirX = 0;
        int dirY = 0;
        Disc prevDisc = null;
        Disc curDisc;

        if (startX < endX)
            dirX = 1;
        if (startY < endY)
            dirY = 1;
        else if (startY > endY)
            dirY = -1;

        int x = startX;
        int y = startY;
        while ( x < ROWS && ( (y < COLS && dirY >= 0) || (y >= 0 && dirY == -1) ) ) {
            curDisc = board[x][y].getDisc();
            if (curDisc == prevDisc && curDisc != null)
                similarInARow++;
            else
                similarInARow = 1;

            if (similarInARow == 4) {
                increaseWins(curDisc);
                lastWinner = curDisc;
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

    public boolean isWinningLine() {
        return isWinningLine;
    }

    public Disc getLastWinner() {
        return lastWinner;
    }

    public boolean isFullBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (!board[i][j].hasDisc())
                    return false;
            }
        }
        return true;
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }
}

