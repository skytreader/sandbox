package net.skytreader.kode.npuzzle.tests;

import net.skytreader.kode.npuzzle.FlatNPuzzle;
import net.skytreader.kode.npuzzle.NPuzzle;

import org.junit.Assert;
import org.junit.Test;

public class FlatNPuzzleTests{
    private FlatNPuzzle fnp;

    @Before
    public void setUp(){
        fnp = new FlatNPuzzle();
    }

    @Test
    public void testInitialize(){
    }

    @Test
    public void testEquals(){
        Assert.assertFalse(fnp.equals(null));

        String foo = "Just a random Object for comparison.";
        Assert.assertFalse(fnp.equals(foo));

        // Test reflexivity
        Assert.assertTrue(fnp.equals(fnp));

        // Create another instance, should still be equal at this point.
        FlatNPuzzle another = new FlatNPuzzle();
        Assert.assertTrue(fnp.equals(another));
        // Test symmetry.
        Assert.assertTrue(another.equals(fnp));

        // Test transitivity
        FlatNPuzzle transitiveOther = new FlatNPuzzle();
        Assert.assertTrue(another.equals(transitiveOther));
        Assert.assertTrue(fnp.equals(transitiveOther));

        // Move stuff around in another
        another.move(NPuzzle.Direction.DOWN);
        Assert.assertFalse(fnp.equals(another));
        Assert.assertFalse(another.equals(fnp));
    }
}
