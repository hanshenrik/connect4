package main.java;

/**
 * Created by hanshenrik on 04/11/14.
 */
public class FPController {
    private FPModel model;
    private FPView view;
    private AIPlayer aiPlayer;
    private boolean aiMode;

    public FPController(FPModel model) {
        this.model = model;
        this.aiPlayer = new AIPlayer(model);
        this.aiMode = false;
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

    public void playDisc(int col, boolean aiTurn) {
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
                endGame();
            }
        }
        if (!aiTurn && aiMode) {
//            playDisc(aiPlayer.simple(), true);
//            playDisc(aiPlayer.random(), true);
            playDisc(aiPlayer.sortOfBlockingHorVer(col), true);
        }
    }

    public void setAIMode(boolean b) {
        this.aiMode = b;
    }
}
