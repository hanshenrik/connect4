package main;

import java.awt.*;
import java.util.Observable;

/**
 * Created by hanshenrik on 23/10/14.
 */
public class FPModel extends Observable {

    public final int ROWS = 7;
    public final int COLS = 6;
    private /*@ spec_public @*/ boolean isWinningLine, isGameEndedByUser;
    private /*@ spec_public @*/ Disc startingDisc, nextDisc, previousWinner;
    private /*@ spec_public @*/ int playerOneWins, playerTwoWins;
    private /*@ spec_public @*/ int freeCells;
    private /*@ spec_public @*/ Cell[][] board;
    /*
     * The board is 'up side down' with respect to rows. This to make the layout
     * more intuitive. E.g., a 3x3 board would be indexed as such:
     *
     * [2,0]    [2,1]   [2,2]
     * [1,0]    [1,1]   [1,2]
     * [0,0]    [0,1]   [0,2]
     */

    //@ public invariant playerOneWins >= 0 && playerTwoWins >= 0;
    //@ public invariant freeCells <= ROWS*COLS && freeCells >= 0;
    //@ public invariant startingDisc == Disc.PLAYER1 ^ startingDisc == Disc.PLAYER2;
    //@ public invariant nextDisc == Disc.PLAYER1 ^ nextDisc == Disc.PLAYER2;

    //@ assignable playerOneWins;
    //@ assignable playerTwoWins;
    //@ assignable isWinningLine;
    //@ assignable startingDisc;
    //@ assignable nextDisc;
    //@ assignable board;
    //@ assignable freeCells;
    //@ ensures playerOneWins == 0;
    //@ ensures playerTwoWins == 0;
    //@ ensures isWinningLine == false;
    //@ ensures startingDisc == Disc.PLAYER1 ^ startingDisc == Disc.PLAYER2;
    //@ ensures nextDisc == startingDisc;
    //@ ensures board.length == ROWS;
        /*@ ensures
        (\forall int row; 0 < row && row < ROWS; board[row].length == COLS)
    @*/
    /*@ ensures
        (\forall int i; 0 < i && i < ROWS;
            (\forall int j; 0 < j && j < COLS; board[i][j].hasDisc() == false)
        )
    @*/
    //@ ensures freeCells == ROWS*COLS;
    public FPModel() {
        resetScore();
        newGame();
    }

    //@ assignable playerOneWins;
    //@ assignable playerTwoWins;
    //@ ensures playerOneWins == 0;
    //@ ensures playerTwoWins == 0;
    public void resetScore() {
        playerOneWins = 0;
        playerTwoWins = 0;
        setChanged();
        notifyObservers();
    }

    public String getScore() {
        return playerOneWins + "-" + playerTwoWins;
    }

    //@ assignable isWinningLine;
    //@ assignable startingDisc;
    //@ assignable nextDisc;
    //@ assignable board;
    //@ assignable freeCells;
    //@ ensures isWinningLine == false;
    //@ ensures startingDisc == Disc.PLAYER1 ^ startingDisc == Disc.PLAYER2;
    //@ ensures nextDisc == startingDisc;
    //@ ensures board.length == ROWS;
        /*@ ensures
        (\forall int row; 0 < row && row < ROWS; board[row].length == COLS)
    @*/
    /*@ ensures
        (\forall int i; 0 < i && i < ROWS;
            (\forall int j; 0 < j && j < COLS; board[i][j].hasDisc() == false)
        )
    @*/
    //@ ensures freeCells == ROWS*COLS;
    public void newGame() {
        isWinningLine = false;
        switchStarter();
        nextDisc = startingDisc;
        createBoard();
        freeCells = ROWS*COLS;
        setChanged();
        notifyObservers();
    }

    //@ assignable isGameEndedByUser;
    //@ ensures isGameEndedByUser == true;
    public void endGame() {
        isGameEndedByUser = true;
    }

    public boolean isGameOver() {
        return isGameEndedByUser || isWinningLine || isFullBoard();
    }

    //@ assignable freeCells;
    //@ assignable nextDisc;
    //@ assignable isWinningLine;
    //@ requires 0 < col && col < COLS;
    //@ ensures nextDisc == Disc.PLAYER1 ^ nextDisc == Disc.PLAYER2;
    public int playDisc(int col) {
        int row = findLowestFreeRow(col);

        if (row != -1 && !isGameOver()) {
            board[row][col].setDisc(nextDisc);
            freeCells--;
            switchTurn();
            setIsWinningLine(row, col);
            setChanged();
            notifyObservers();
        }
        return row;
    }

    //@ requires 0 < col && col < COLS;
    private int findLowestFreeRow(int col) {
        for (int i = 0; i < ROWS; i++) {
            if (!board[i][col].hasDisc())
                return i;
        }
        return -1;
    }

    //@ assignable board;
    //@ ensures board.length == ROWS;
    /*@ ensures
        (\forall int row; 0 < row && row < ROWS; board[row].length == COLS)
    @*/
    /*@ ensures
        (\forall int i; 0 < i && i < ROWS;
            (\forall int j; 0 < j && j < COLS; board[i][j].hasDisc() == false)
        )
    @*/
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

    //@ assignable nextDisc;
    //@ ensures nextDisc == Disc.PLAYER1 ^ nextDisc == Disc.PLAYER2;
    private void switchTurn() {
        nextDisc = (nextDisc == Disc.PLAYER1) ? Disc.PLAYER2 : Disc.PLAYER1;
    }

    //@ assignable startingDisc;
    //@ ensures startingDisc == Disc.PLAYER1 ^ startingDisc == Disc.PLAYER2;
    private void switchStarter() {
        startingDisc = (startingDisc == Disc.PLAYER1) ? Disc.PLAYER2 : Disc.PLAYER1;
    }

    //@ assignable isWinningLine;
    private void setIsWinningLine(int row, int col) {
        isWinningLine =
            checkLine(new Point(row, 0), new Point(row, COLS - 1)) || // horizontal
            checkLine(new Point(0, col), new Point(ROWS - 1, col)) || // vertical
            checkLine(getDiagonalStartPoint(row, col, "southwest"), new Point(ROWS - 1, COLS - 1)) ||
            checkLine(getDiagonalStartPoint(row, col, "southeast"), new Point(ROWS - 1, 0));
    }

    //@ requires 0 <= row && row < ROWS;
    //@ requires 0 <= col && col < COLS;
    //@ ensures 0 <= \result.x && \result.x < ROWS;
    //@ ensures 0 <= \result.y && \result.y < COLS;
    private Point getDiagonalStartPoint(int row, int col, String dir) {
        if (!dir.equals("southwest") && !dir.equals("southeast"))
            throw new IllegalArgumentException("Direction must be either 'southwest' or 'southeast'!");
        Point point = new Point(row, col);
        while ( (point.x != 0) && (point.y != 0) && (point.y != COLS - 1) ) {
            point.x--;
            if (dir.equals("southwest"))
                point.y--;
            else if (dir.equals("southeast"))
                point.y++;
        }
        return point;
    }

    //@ requires 0 <= start.x && start.x < ROWS;
    //@ requires 0 <= start.y && start.y < COLS;
    //@ requires 0 <= end.x && end.x < ROWS;
    //@ requires 0 <= end.y && end.y < COLS;
    //@ assignable previousWinner;
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

        /* make sure we don't end up outside the board by:
         * (0) in all cases; make sure x < ROWS (x can only be static or increase)
         * (1) if y increases (dirY > 0); make sure y < COLS
         * (2) if y decreases (dirY < 0); make sure y >= 0;
         */
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

    //@ requires winningDisc == Disc.PLAYER1 || winningDisc == Disc.PLAYER2;
    //@ assignable playerOneWins;
    //@ assignable playerTwoWins;
    //@ ensures (playerOneWins == \old(playerOneWins) + 1) ^ (playerTwoWins == \old(playerTwoWins) + 1);
    private void increaseWins(Disc winningDisc) {
        if (winningDisc == Disc.PLAYER1)
            playerOneWins++;
        else if (winningDisc == Disc.PLAYER2)
            playerTwoWins++;
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
