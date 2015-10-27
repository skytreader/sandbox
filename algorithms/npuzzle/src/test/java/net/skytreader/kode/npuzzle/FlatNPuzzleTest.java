package net.skytreader.kode.npuzzle;

import java.awt.Point;

import net.skytreader.kode.npuzzle.FlatNPuzzle;
import net.skytreader.kode.npuzzle.NPuzzle;

import net.skytreader.kode.npuzzle.exceptions.CorruptedPuzzleException;

import net.skytreader.kode.npuzzle.utils.Algorithms;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

public class FlatNPuzzleTest{
    private FlatNPuzzle fnp;
    public static final int TEST_SIZE = 4;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp(){
        fnp = new FlatNPuzzle(FlatNPuzzleTest.TEST_SIZE);
    }

    @Test
    public void testInitialize(){
        // FIXME The initialize should be randomized
        fnp.initialize();
        System.out.println("We have " + Algorithms.countInversions(fnp.toArray(false)) + " inversions.");
        Assert.assertTrue(Algorithms.isSolvable(fnp));
    }

    @Test
    public void testInitializeOdd(){
        FlatNPuzzle fnpOdd = new FlatNPuzzle(3);
        fnpOdd.initialize();
        Assert.assertTrue(Algorithms.isSolvable(fnpOdd));
    }

    @Test
    public void testSetConfigHappy(){
        int[] superInversion = new int[]{15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5,
          4, 3, 2, 1, 0};

        fnp.setConfig(superInversion);
    }

    @Test
    public void testSetConfigMoreBlanks(){
        exception.expect(CorruptedPuzzleException.class);
        int[] superBlank = new int[]{0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
          12, 13, 14};

        fnp.setConfig(superBlank);
    }

    @Test
    public void testSetConfigNoBlanks(){
        exception.expect(CorruptedPuzzleException.class);
        int[] noBlank = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
          15, 16};

        fnp.setConfig(noBlank);
    }

    @Test
    public void testSetConfigSmallerSize(){
        exception.expect(CorruptedPuzzleException.class);
        int[] small = new int[]{0, 1};
        fnp.setConfig(small);
    }

    @Test
    public void testSetConfigLargerSize(){
        exception.expect(CorruptedPuzzleException.class);
        int[] large = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
          15, 16};

        fnp.setConfig(large);
    }

    @Test
    public void testSetEntropyFactor(){
        Assert.assertNotEquals(fnp.getEntropyFactor(), 88);
        fnp.setEntropyFactor(88);
        Assert.assertEquals(fnp.getEntropyFactor(), 88);
        fnp.setEntropyFactor(105);
        Assert.assertEquals(fnp.getEntropyFactor(), 105);
    }

    @Test
    public void testEntropyFactorException(){
        // For our test puzzle of size 4, the maximum entropy factor will be 105.
        exception.expect(IllegalArgumentException.class);
        fnp.setEntropyFactor(106);
    }

    @Test
    public void testMove(){
        Point blankOrigin = fnp.getBlankPos();
        fnp.move(NPuzzle.Direction.UP);
        Point movedBlank = fnp.getBlankPos();

        Assert.assertEquals(blankOrigin.x - 1, movedBlank.x);
        Assert.assertEquals(blankOrigin.y, movedBlank.y);

        blankOrigin = movedBlank;
        fnp.move(NPuzzle.Direction.DOWN);
        movedBlank = fnp.getBlankPos();

        Assert.assertEquals(blankOrigin.x + 1, movedBlank.x);
        Assert.assertEquals(blankOrigin.y, movedBlank.y);

        blankOrigin = movedBlank;
        fnp.move(NPuzzle.Direction.LEFT);
        movedBlank = fnp.getBlankPos();

        Assert.assertEquals(blankOrigin.x, movedBlank.x);
        Assert.assertEquals(blankOrigin.y - 1, movedBlank.y);

        blankOrigin = movedBlank;
        fnp.move(NPuzzle.Direction.RIGHT);
        movedBlank = fnp.getBlankPos();

        Assert.assertEquals(blankOrigin.x, movedBlank.x);
        Assert.assertEquals(blankOrigin.y + 1, movedBlank.y);
    }

    @Test
    public void testEquals(){
        Assert.assertFalse(fnp.equals(null));

        String foo = "Just a random Object for comparison.";
        Assert.assertFalse(fnp.equals(foo));

        // Test reflexivity
        Assert.assertTrue(fnp.equals(fnp));

        // Create another instance, should still be equal at this point.
        FlatNPuzzle another = new FlatNPuzzle(FlatNPuzzleTest.TEST_SIZE);
        Assert.assertTrue(fnp.equals(another));
        // Test symmetry.
        Assert.assertTrue(another.equals(fnp));

        // Test transitivity
        FlatNPuzzle transitiveOther = new FlatNPuzzle(FlatNPuzzleTest.TEST_SIZE);
        Assert.assertTrue(another.equals(transitiveOther));
        Assert.assertTrue(fnp.equals(transitiveOther));

        // Move stuff around in another
        another.move(NPuzzle.Direction.UP);
        Assert.assertFalse(fnp.equals(another));
        Assert.assertFalse(another.equals(fnp));
    }
}
