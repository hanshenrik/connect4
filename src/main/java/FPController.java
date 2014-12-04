package main.java;

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
        view.setEnableEndGame(true);
        view.setEnableResetScore(true);
        view.setEnableBoardInteraction(true);
        view.setMessageField("New game started!");
    }

    public void endGame() {
        model.endGame();
        view.setEnableEndGame(false);
        view.setEnableBoardInteraction(false);
        view.setMessageField("Game ended!");
    }

    public void resetScore() {
        model.resetScore();
        view.setEnableResetScore(false);
        view.setMessageField("Score reset!");
    }

    public void playDisc(int col) {
        if (model.isGameOver())
            view.setMessageField("Game over, start new game!");
        else if (model.isFullBoard())
            view.setMessageField("Board is full!");
        else if (model.findLowestFreeRow(col) == -1)
            view.setMessageField("Column is full!");
        else {
            model.playDisc(col);
            view.setMessageField("Put disc in column " + col);
            if (model.isGameOver()) {
                System.out.println("from model.isGameOver()");
                endGame();
            }
        }
        printBoard(); // TODO: move to view, then remove!
    }

    public void printBoard() {
        // TODO: remove! for testing only
        Disc[][] board = model.getBoard();
        System.out.println("\n=======================================================");
        for (int i = model.ROWS - 1; i >= 0; i--) {
            System.out.print(i + " ");
            for (int j = 0; j < model.COLS; j++) {
                System.out.print(board[i][j] + ", ");
            }
            System.out.println("");
        }
        System.out.println("=======================================================");
    }
}
