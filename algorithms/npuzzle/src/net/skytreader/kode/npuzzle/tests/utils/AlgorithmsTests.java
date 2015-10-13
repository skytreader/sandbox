package net.skytreader.kode.npuzzle.tests.utils;

import net.skytreader.kode.npuzzle.utils.Algorithms;

import org.junit.Assert;
import org.junit.Test;

public class AlgorithmsTests{

    @Test
    public void testCountInversions(){
        int[] oddversion = [7, 6, 8, 4, 4, 2, 1, 3];
        Assert.assertEquals(23, Algorithms.countInversions(oddversion));

        int[] eversion = [4, 2, 1, 3, 9, 6, 8, 7];
        Assert.assertEquals(8, Algorithms.countInversions(eversion));

        int[] neinversion = [1, 2, 3, 4, 6, 7, 8];
        Assert.assertEquals(0, Algorithms.countInversions(neinversion));

        int[] noneAtAll = [];
        Assert.assertEquals(0, Algorithms.countInversions(noneAtAll));
    }
}
