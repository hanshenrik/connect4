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
//        model.addDisc(0);
//        model.addDisc(1);
//        model.addDisc(0);
//        model.addDisc(1);
//        model.addDisc(0);
//        model.addDisc(1);
//        model.printBoard();
//        System.out.println(model.isWinningLine());
//        model.addDisc(0);
//        model.printBoard();
//        System.out.println(model.isWinningLine());
//
//        // test horizontal
//        model.newGame();
//        model.addDisc(0);
//        model.addDisc(0);
//        model.addDisc(1);
//        model.addDisc(1);
//        model.addDisc(2);
//        model.addDisc(2);
//        model.printBoard();
//        System.out.println(model.isWinningLine());
//        model.addDisc(3);
//        model.printBoard();
//        System.out.println(model.isWinningLine());

//        // test diagonal north west
//        model.newGame();
//        model.printBoard();
//        model.addDisc(0);
//        model.addDisc(0);
//        model.addDisc(0);
//        model.addDisc(0);
//        model.addDisc(5);
//        model.addDisc(1);
//        model.addDisc(1);
//        model.addDisc(1);
//        model.addDisc(2);
//        model.addDisc(2);
//        model.addDisc(5);
//        model.printBoard();
//        System.out.println(model.isWinningLine());
//        model.addDisc(3);
//        model.printBoard();
//        System.out.println(model.isWinningLine());

//        // test diagonal north east
//        model.newGame();
//        model.printBoard();
//        model.addDisc(0);
//        model.addDisc(1);
//        model.addDisc(2);
//        model.addDisc(3);
//        model.addDisc(4);
//        model.addDisc(5);
//        model.addDisc(5);
//        model.addDisc(5);
//        model.addDisc(5);
//        model.addDisc(5);
//        model.addDisc(0);
//        model.addDisc(4);
//        model.addDisc(4);
//        model.addDisc(4);
//        model.addDisc(3);
//        model.printBoard();
//        System.out.println(model.isWinningLine());
//        model.addDisc(3);
//        model.addDisc(0);
//        model.printBoard();
//        System.out.println(model.isWinningLine());
//        model.addDisc(2);
//        model.printBoard();
//        System.out.println(model.isWinningLine());
//
//        model.newGame();
//        model.printBoard();
//        System.out.println("full: "+model.isFullBoard());
//        model.addDisc(0);
//        model.addDisc(0);
//        model.addDisc(0);
//        model.addDisc(0);
//        model.addDisc(0);
//        model.addDisc(0);
//        model.addDisc(1);
//        model.addDisc(1);
//        model.addDisc(1);
//        model.addDisc(1);
//        model.addDisc(1);
//        model.addDisc(1);
//        model.addDisc(2);
//        model.addDisc(2);
//        model.addDisc(2);
//        model.addDisc(2);
//        model.addDisc(2);
//        model.addDisc(2);
//        model.addDisc(3);
//        model.addDisc(3);
//        model.addDisc(3);
//        model.addDisc(3);
//        model.addDisc(3);
//        model.addDisc(3);
//        model.addDisc(4);
//        model.addDisc(4);
//        model.addDisc(4);
//        model.addDisc(4);
//        model.addDisc(4);
//        model.addDisc(4);
//        model.addDisc(5);
//        model.addDisc(5);
//        model.addDisc(5);
//        model.addDisc(5);
//        model.addDisc(5);
//        model.addDisc(5);
//        model.addDisc(0);
//        model.addDisc(1);
//        model.addDisc(2);
//        model.addDisc(3);
//        model.addDisc(4);
//        model.addDisc(5);
//        model.printBoard();
//        System.out.println("full: " + model.isFullBoard());
    }
}
