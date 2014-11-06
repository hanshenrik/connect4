package main;

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
//
//        // test vertical
//        controller.addDisc(0);
//        controller.addDisc(1);
//        controller.addDisc(0);
//        controller.addDisc(1);
//        controller.addDisc(0);
//        controller.addDisc(1);
//        controller.addDisc(0);
//
//        // test horizontal
//        controller.newGame();
//        controller.addDisc(0);
//        controller.addDisc(0);
//        controller.addDisc(1);
//        controller.addDisc(1);
//        controller.addDisc(2);
//        controller.addDisc(2);
//        controller.addDisc(3);
//
//        // test diagonal north west
//        controller.newGame();
//        controller.addDisc(0);
//        controller.addDisc(0);
//        controller.addDisc(0);
//        controller.addDisc(0);
//        controller.addDisc(5);
//        controller.addDisc(1);
//        controller.addDisc(1);
//        controller.addDisc(1);
//        controller.addDisc(2);
//        controller.addDisc(2);
//        controller.addDisc(5);
//        controller.addDisc(3);
//
//        // test diagonal north east
//        controller.newGame();
//        controller.addDisc(0);
//        controller.addDisc(1);
//        controller.addDisc(2);
//        controller.addDisc(3);
//        controller.addDisc(4);
//        controller.addDisc(5);
//        controller.addDisc(5);
//        controller.addDisc(5);
//        controller.addDisc(5);
//        controller.addDisc(5);
//        controller.addDisc(0);
//        controller.addDisc(4);
//        controller.addDisc(4);
//        controller.addDisc(4);
//        controller.addDisc(3);
//        controller.addDisc(3);
//        controller.addDisc(0);
//        controller.addDisc(2);
    }
}
