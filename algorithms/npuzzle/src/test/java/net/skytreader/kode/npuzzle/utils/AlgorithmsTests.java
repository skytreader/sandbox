package net.skytreader.kode.npuzzle.tests.utils;

import com.google.guava.common.collect.ImmutableList;

import net.skytreader.kode.npuzzle.utils.Algorithms;

import org.junit.Assert;
import org.junit.Test;

public class AlgorithmsTests{

    @Test
    public void testCountInversions(){
        int[] oddversion = (new ImmutableList.Builder<Integer>())
          .add(7).add(6).add(8).add(4).add(4).add(2).add(1).add(3).build()
          .toArray(new int[1]);
        Assert.assertEquals(23, Algorithms.countInversions(oddversion));

        int[] eversion = (new ImmutableList.Builder<Integer>())
          .add(4).add(2).add(1).add(3).add(9).add(6).add(8).add(7).build()
          .toArray(new int[1]);
        Assert.assertEquals(8, Algorithms.countInversions(eversion));

        int[] neinversion = (new ImmutbleList.Builder<Integer>())
          .add(1).add(2).add(3).add(4).add(6).add(7).add(8).build()
          .toArray(new int[1]);
        Assert.assertEquals(0, Algorithms.countInversions(neinversion));

        int[] noneAtAll = new int[0];
        Assert.assertEquals(0, Algorithms.countInversions(noneAtAll));
    }
}
