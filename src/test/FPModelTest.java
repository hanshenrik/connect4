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
    public void addDisc_winningLineDisablesAddingDisc() throws Exception {
        model.addDisc(0);
        model.addDisc(1);
        model.addDisc(0);
        model.addDisc(1);
        model.addDisc(0);
        model.addDisc(1);
        model.addDisc(0);

        model.addDisc(5);
        Cell cell = model.getBoard()[0][5];
        Assert.assertNull("Disc was added when winning line existed", cell.getDisc());
    }

    @Test
    public void isFullBoard_boardFilled() throws Exception {
        model.addDisc(0);
        model.addDisc(0);
        model.addDisc(0);
        model.addDisc(0);
        model.addDisc(0);
        model.addDisc(0);

        model.addDisc(1);
        model.addDisc(1);
        model.addDisc(1);
        model.addDisc(1);
        model.addDisc(1);
        model.addDisc(1);

        model.addDisc(2);
        model.addDisc(2);
        model.addDisc(2);
        model.addDisc(2);
        model.addDisc(2);
        model.addDisc(2);

        model.addDisc(5);

        model.addDisc(3);
        model.addDisc(3);
        model.addDisc(3);
        model.addDisc(3);
        model.addDisc(3);
        model.addDisc(3);

        model.addDisc(4);
        model.addDisc(4);
        model.addDisc(4);
        model.addDisc(4);
        model.addDisc(4);
        model.addDisc(4);

        model.addDisc(5);
        model.addDisc(5);
        model.addDisc(5);
        model.addDisc(5);
        model.addDisc(5);

        model.addDisc(0);
        model.addDisc(1);
        model.addDisc(2);
        model.addDisc(3);
        model.addDisc(4);

        Assert.assertFalse("isFullBoard() returned true when board was not full.", model.isFullBoard());
        model.addDisc(5);
        Assert.assertTrue("isFullBoard() returned false when board was exactly full.", model.isFullBoard());
    }
}