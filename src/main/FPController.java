package main;

/**
 * Created by hanshenrik on 04/11/14.
 */
public class FPController {
    private FPModel model;
    private FPView view;

    public FPController(FPModel model) {
        this.model = model;
    }

    public void setView(FPView view) {
        this.view = view;
    }

    public void newGame() {
        model.newGame();
    }

    public void endGame() {
//        model.endGame();
        model.playDisc(0);
        model.playDisc(1);
        printBoard();
    }

    public void resetScore() {
        model.resetScore();
    }

    public void addDisc(int col) {
        // validate input?
        model.playDisc(col);
        printBoard();
    }

    public void printBoard() {
        // TODO: remove! for testing only
        Cell[][] board = model.getBoard();
        System.out.println("\n=======================================================");
        for (int i = model.ROWS - 1; i >= 0; i--) {
            System.out.print(i + " ");
            for (int j = 0; j < model.COLS; j++) {
                System.out.print(board[i][j].getDisc() + ", ");
            }
            System.out.println("");
        }
        System.out.println("=======================================================");
    }
}
