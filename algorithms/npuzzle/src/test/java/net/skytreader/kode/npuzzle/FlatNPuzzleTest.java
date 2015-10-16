package net.skytreader.kode.npuzzle;

import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;

import java.awt.Point;

import net.skytreader.kode.npuzzle.FlatNPuzzle;
import net.skytreader.kode.npuzzle.NPuzzle;

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
        // Test the even instance
    }

    @Test
    public void testSetConfig(){
        int[] superInversion = Ints.toArray((new ImmutableList.Builder<Integer>())
          .add(15).add(14).add(13).add(12).add(11).add(10).add(9).add(8).add(7)
          .add(6).add(5).add(4).add(3).add(2).add(1).build());

        fnp.setConfig(superInversion);
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
        FlatNPuzzle another = new FlatNPuzzle(FlatNPuzzleTest.TEST_SIZE);
        Assert.assertTrue(fnp.equals(another));
        // Test symmetry.
        Assert.assertTrue(another.equals(fnp));

        // Test transitivity
        FlatNPuzzle transitiveOther = new FlatNPuzzle(FlatNPuzzleTest.TEST_SIZE);
        Assert.assertTrue(another.equals(transitiveOther));
        Assert.assertTrue(fnp.equals(transitiveOther));

        // Move stuff around in another
        another.move(NPuzzle.Direction.DOWN);
        Assert.assertFalse(fnp.equals(another));
        Assert.assertFalse(another.equals(fnp));
    }
}
