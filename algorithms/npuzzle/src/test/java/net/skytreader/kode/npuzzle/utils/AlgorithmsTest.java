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
        int[] oddversion = Ints.toArray((new ImmutableList.Builder<Integer>())
          .add(7).add(6).add(8).add(4).add(4).add(2).add(1).add(3).build());
        Assert.assertEquals(23, Algorithms.countInversions(oddversion));

        int[] eversion = Ints.toArray((new ImmutableList.Builder<Integer>())
          .add(4).add(2).add(1).add(3).add(9).add(6).add(8).add(7).build());
        Assert.assertEquals(8, Algorithms.countInversions(eversion));

        int[] neinversion = Ints.toArray((new ImmutableList.Builder<Integer>())
          .add(1).add(2).add(3).add(4).add(6).add(7).add(8).build());
        Assert.assertEquals(0, Algorithms.countInversions(neinversion));

        int[] noneAtAll = new int[0];
        Assert.assertEquals(0, Algorithms.countInversions(noneAtAll));
    }

    @Test
    public void testIsSolvable(){
         NPuzzle oddWidth = new FlatNPuzzle(3);
    }
}
