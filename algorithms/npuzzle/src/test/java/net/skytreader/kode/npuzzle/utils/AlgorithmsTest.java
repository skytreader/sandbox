package net.skytreader.kode.npuzzle.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;

import net.skytreader.kode.npuzzle.FlatNPuzzle;
import net.skytreader.kode.npuzzle.NPuzzle;

import net.skytreader.kode.npuzzle.utils.Algorithms;

import org.junit.Assert;
import org.junit.Test;

public class AlgorithmsTest{

    @Test
    public void testCountInversions(){
        int[] oddversion = new int[]{7, 6, 8, 4, 4, 2, 1, 3};
        Assert.assertEquals(23, Algorithms.countInversions(oddversion));

        int[] eversion = new int[]{4, 2, 1, 3, 9, 6, 8, 7};
        Assert.assertEquals(8, Algorithms.countInversions(eversion));

        int[] neinversion = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        Assert.assertEquals(0, Algorithms.countInversions(neinversion));

        int[] noneAtAll = new int[0];
        Assert.assertEquals(0, Algorithms.countInversions(noneAtAll));
    }

    @Test
    public void testIsSolvable(){
         NPuzzle oddWidth = new FlatNPuzzle(3);
    }
}
