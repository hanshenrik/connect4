package test;

import org.junit.*;
import main.*;

/**
 * There are as many naming conventions out there as there are bricks in the
 * Great Wall of China. Inspired by Kyle Blanely's 'JUnit Best Practices' [1],
 * I'm using the following method names: methodUnderTest_conditionBeingTested
 *
 * [1] http://www.kyleblaney.com/junit-best-practices/
 */

public class FPModelTest {
    private FPModel model;

    @Before
    public void setUp() throws Exception {
        model = new FPModel();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void newGame_noCellHasDisc() {
        model.playDisc(0);
        model.playDisc(1);
        model.playDisc(2);
        model.playDisc(3);
        model.playDisc(4);
        model.playDisc(5);
        model.playDisc(0);
        model.playDisc(0);
        model.playDisc(0);

        model.newGame();
        Disc[][] board = model.getBoard();

        for (Disc[] row : board) {
            for (Disc disc : row) {
                Assert.assertNull("Disc in new board was not null.", disc);
            }
        }
    }

    @Test
    public void playDisc_winningLineDisablesAddingDisc() throws Exception {
        int col = 5;

        model.playDisc(0);
        model.playDisc(1);
        model.playDisc(0);
        model.playDisc(1);
        model.playDisc(0);
        model.playDisc(1);
        model.playDisc(0); // winning line for Player1, vertical in column 0

        int row = model.playDisc(col);
        Disc disc = model.getBoard()[row][col];
        Assert.assertNull("Disc was added when winning line existed.", disc);
    }

    @Test
    public void isFullBoard_boardFilled() throws Exception {
        model.playDisc(0);
        model.playDisc(0);
        model.playDisc(0);
        model.playDisc(0);
        model.playDisc(0);
        model.playDisc(0);

        model.playDisc(1);
        model.playDisc(1);
        model.playDisc(1);
        model.playDisc(1);
        model.playDisc(1);
        model.playDisc(1);

        model.playDisc(2);
        model.playDisc(2);
        model.playDisc(2);
        model.playDisc(2);
        model.playDisc(2);
        model.playDisc(2);

        model.playDisc(5);

        model.playDisc(3);
        model.playDisc(3);
        model.playDisc(3);
        model.playDisc(3);
        model.playDisc(3);
        model.playDisc(3);

        model.playDisc(4);
        model.playDisc(4);
        model.playDisc(4);
        model.playDisc(4);
        model.playDisc(4);
        model.playDisc(4);

        model.playDisc(5);
        model.playDisc(5);
        model.playDisc(5);
        model.playDisc(5);
        model.playDisc(5);

        model.playDisc(0);
        model.playDisc(1);
        model.playDisc(2);
        model.playDisc(3);
        model.playDisc(4); // all but topmost cell in column 5 filled, no winning line

        Assert.assertFalse("isFullBoard() returned true when board was not full.", model.isFullBoard());
        model.playDisc(5);
        Assert.assertTrue("isFullBoard() returned false when board was full.", model.isFullBoard());
    }
}