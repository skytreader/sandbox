package net.skytreader.kode.npuzzle.tests;

import java.awt.Point;

import net.skytreader.kode.npuzzle.FlatNPuzzle;
import net.skytreader.kode.npuzzle.NPuzzle;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

public class FlatNPuzzleTests{
    private FlatNPuzzle fnp;
    public static final int TEST_SIZE = 4;

    @Before
    public void setUp(){
        fnp = new FlatNPuzzle(FlatNPuzzleTests.TEST_SIZE);
    }

    @Test
    public void testInitialize(){
    }

    @Test
    public void testMove(){
        Point blankOrigin = fnp.getBlankPos();
        fnp.move(NPuzzle.Direction.DOWN);
        Point movedBlank = fnp.getBlankPos();

        Assert.assertEquals(blankOrigin.x + 1, movedBlank.x);
        Assert.assertEquals(blankOrigin.y, movedBlank.y);

        blankOrigin = movedBlank;
        fnp.move(NPuzzle.Direction.UP);
        movedBlank = fnp.getBlankPos();

        Assert.assertEquals(blankOrigin.x - 1, movedBlank.x);
        Assert.assertEquals(blankOrigin.y, movedBlank.y);

        blankOrigin = movedBlank;
        fnp.move(NPuzzle.Direction.RIGHT);
        movedBlank = fnp.getBlankPos();

        Assert.assertEquals(blankOrigin.x, movedBlank.x);
        Assert.assertEquals(blankOrigin.y + 1, movedBlank.y);

        blankOrigin = movedBlank;
        fnp.move(NPuzzle.Direction.LEFT);
        movedBlank = fnp.getBlankPos();

        Assert.assertEquals(blankOrigin.x, movedBlank.x);
        Assert.assertEquals(blankOrigin.y - 1, movedBlank.y);
    }

    @Test
    public void testEquals(){
        Assert.assertFalse(fnp.equals(null));

        String foo = "Just a random Object for comparison.";
        Assert.assertFalse(fnp.equals(foo));

        // Test reflexivity
        Assert.assertTrue(fnp.equals(fnp));

        // Create another instance, should still be equal at this point.
        FlatNPuzzle another = new FlatNPuzzle(FlatNPuzzleTests.TEST_SIZE);
        Assert.assertTrue(fnp.equals(another));
        // Test symmetry.
        Assert.assertTrue(another.equals(fnp));

        // Test transitivity
        FlatNPuzzle transitiveOther = new FlatNPuzzle(FlatNPuzzleTests.TEST_SIZE);
        Assert.assertTrue(another.equals(transitiveOther));
        Assert.assertTrue(fnp.equals(transitiveOther));

        // Move stuff around in another
        another.move(NPuzzle.Direction.DOWN);
        Assert.assertFalse(fnp.equals(another));
        Assert.assertFalse(another.equals(fnp));
    }
}
