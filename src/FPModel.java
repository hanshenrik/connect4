import java.awt.*;
import java.util.Observable;

/**
 * Created by hanshenrik on 23/10/14.
 */
public class FPModel extends Observable {

    private final int ROWS = 7;
    private final int COLS = 6;
    private Disc starter, nextDisc;
    private int playerOneWins, playerTwoWins;
    private Cell[][] board;
    /**
     * The board is 'up side down' with respect to rows. This to make searching
     * for free cells when adding discs more intuitive. E.g., a 3x3 board is
     * indexed as such:
     *
     * [2,0]    [2,1]   [2,2]
     * [1,0]    [1,1]   [1,2]
     * [0,0]    [0,1]   [0,2]
     */

    public FPModel() {
        resetScore();
        newGame();
        setChanged();
        notifyObservers();
    }

    public void resetScore() {
        playerOneWins = 0;
        playerTwoWins = 0;
    }

    // TODO: better way of returning result. Tuple?
    public String getScore() {
        return playerOneWins + "-" + playerTwoWins;
    }

    private void increaseWins(Disc winningDisc) {
        if (winningDisc == Disc.PLAYER1) {
            playerOneWins++;
        }
        else if (winningDisc == Disc.PLAYER2) {
            playerTwoWins++;
        }
        else {
            System.out.println("Error: Unidentified winning disc.");
        }
    }

    public void newGame() {
        switchStarter();
        nextDisc = starter;
        createBoard();
    }

    public void endGame() {
        // TODO
    }

    private void createBoard() {
        board = new Cell[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = new Cell();
            }
        }
    }

    private void switchTurn() {
        nextDisc = (nextDisc == Disc.PLAYER1) ? Disc.PLAYER2 : Disc.PLAYER1;
    }

    private void switchStarter() {
        starter = (starter == Disc.PLAYER1) ? Disc.PLAYER2 : Disc.PLAYER1;
    }

    // TODO: make custom exception ColumnFullException. Or just return -1?
    public void addDisc(int col) throws ArrayIndexOutOfBoundsException {
        int row = getLowestFreeRow(col);
        if (row == -1) {
            throw new ArrayIndexOutOfBoundsException("Column is full! Disc not added.");
        }
        board[row][col].setDisc(nextDisc);
        switchTurn();
    }

    private int getLowestFreeRow(int col) {
        for (int i = 0; i < ROWS - 1; i++) {
            if (!board[i][col].hasDisc()) {
                return i;
            }
        }
        return -1;
    }

    public boolean checkVictory(int row, int col) {
        return checkHorizontalVictory(row) ||
               checkVerticalVictory(col) ||
               checkDiagonalVictory(row, col);
    }

    private boolean checkHorizontalVictory(int row) {
        int similarInARow = 0;
        Disc prevDisc = null;
        Disc curDisc;
        Cell cell;

        for (int j = 0; j < COLS; j++) {
            cell = board[row][j];
            curDisc = cell.getDisc();
            if (curDisc == prevDisc && cell.hasDisc()) {
                similarInARow++;
            }
            else {
                similarInARow = 1;
            }

            if (similarInARow == 4) {
                System.out.println("hor");
                increaseWins(curDisc);
                return true;
            }
            prevDisc = curDisc;
        }
        return false;
    }

    private boolean checkVerticalVictory(int col) {
        int similarInARow = 0;
        Disc prevDisc = null;
        Disc curDisc;
        Cell cell;

        for (int i = 0; i < ROWS; i++) {
            cell = board[i][col];
            curDisc = cell.getDisc();
            if (curDisc == prevDisc && cell.hasDisc()) {
                similarInARow++;
            }
            else {
                similarInARow = 1;
            }

            if (similarInARow == 4) {
                System.out.println("ver");
                increaseWins(curDisc);
                return true;
            }
            prevDisc = curDisc;
        }
        return false;
    }

    private boolean checkDiagonalVictory(int row, int col) {
        return checkNorthEast(row, col) || checkNorthWest(row, col);
    }

    private boolean checkNorthEast(int row, int col) {
        int similarInARow = 0;
        Disc prevDisc = null;
        Disc curDisc;
        Cell cell;

        // find lowest, leftmost starting cell on diagonal
        int lowestRow = row;
        int leftmostCol = col;
        while ( (lowestRow != 0) && (leftmostCol != 0) ) {
            lowestRow--;
            leftmostCol--;
        }

        // search in North East direction
        for (int i = 0; lowestRow + i < ROWS - 1; i++) {
            if (leftmostCol + i > COLS - 1) {
                break;
            }
            cell = board[lowestRow + i][leftmostCol + i];
            curDisc = cell.getDisc();
            if (curDisc == prevDisc && cell.hasDisc()) {
                similarInARow++;
            }
            else {
                similarInARow = 1;
            }

            if (similarInARow == 4) {
                System.out.println("ne");
                increaseWins(curDisc);
                return true;
            }
            prevDisc = curDisc;
        }
        return false;
    }

    private boolean checkNorthWest(int row, int col) {
        int similarInARow = 0;
        Disc prevDisc = null;
        Disc curDisc;
        Cell cell;

        // find lowest, rightmost starting cell on diagonal
        int lowestRow = row;
        int rightmostCol = col;
        while ( (lowestRow != 0) && (rightmostCol != COLS - 1) ) {
            lowestRow--;
            rightmostCol++;
        }

        // search in North West direction
        for (int i = 0; lowestRow + i < ROWS - 1; i++) {
            if (rightmostCol - i < 0) {
                break;
            }
            cell = board[lowestRow + i][rightmostCol - i];
            curDisc = cell.getDisc();
            if (curDisc == prevDisc && cell.hasDisc()) {
                similarInARow++;
            }
            else {
                similarInARow = 1;
            }

            if (similarInARow == 4) {
                System.out.println("nw");
                increaseWins(curDisc);
                return true;
            }
            prevDisc = curDisc;
        }
        return false;
    }

    // for testing
    public void printBoard() {
        System.out.println("\n==================================================");
        for (int i = ROWS - 1; i >= 0; i--) {
            System.out.print(i + " ");
            for (int j = 0; j < COLS; j++) {
                System.out.print(board[i][j].getDisc() + ", ");
            }
            System.out.println("");
        }
        System.out.println("==================================================");
    }
}

