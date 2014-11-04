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
        model.endGame();
    }

    public void resetScore() {
        model.resetScore();
    }

    public void addDisc(int col) {
        // validate input?
        model.addDisc(col);
    }
}
