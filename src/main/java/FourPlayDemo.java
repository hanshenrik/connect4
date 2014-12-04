package main.java;

/**
 * Created by hanshenrik on 23/10/14.
 */
public class FourPlayDemo {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        createAndShowGUI();
                    }
                }
        );
    }
    public static void createAndShowGUI() {
        FPModel model = new FPModel();
        FPController controller = new FPController(model);
        FPView view = new FPView(model, controller);
    }
}
