package net.skytreader.kode.npuzzle.tests;

import net.skytreader.kode.npuzzle.FlatNPuzzle;

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
    }
}
