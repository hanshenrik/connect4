/**
 * Created by hanshenrik on 23/10/14.
 */
public class FourPlayDemo {

    public static void main(String[] args) {
        FPModel model = new FPModel();

        model.addDisc(0);
        model.addDisc(0);
        model.addDisc(0);
        model.addDisc(0);
        model.addDisc(0);
        model.addDisc(0);
        model.addDisc(0);
        model.addDisc(0);
        model.addDisc(0);
        model.printBoard();
        System.out.println("vertical: "+ model.checkVictory(3, 0));

        model.addDisc(0);
        model.addDisc(1);
        model.addDisc(1);
        model.addDisc(1);
        model.addDisc(1);
        model.addDisc(1);
        model.addDisc(2);
        model.addDisc(2);
        model.addDisc(2);
        model.addDisc(3);
        model.addDisc(3);
        model.printBoard();
        System.out.println("diagonal: "+ model.checkVictory(1, 3));

        model.addDisc(5);
        model.printBoard();
        System.out.println("not horizontal: "+model.checkVictory(0,5));

        model.addDisc(4);
        model.printBoard();
        System.out.println("horizontal: "+model.checkVictory(0,1));
        System.out.println(model.getScore());
    }
}
