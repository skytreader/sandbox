package net.skytreader.kode.cll.tests;

import java.awt.Point;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import net.skytreader.kode.cll.LabelMaker;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

public class CLLTest{

    private char[][] sampleMap = {
    {'3','3','3','3','1','1','1','4','1','1','1','5'},
    {'9','9','9','9','2','2','2','2','6','6','6','5'},
    {'3','3','3','5','5','5','5','5','8','8','8','8'},
    {'9','9','9','9','9','9','7','7','7','9','9','9'},
    {'3','3','3','3','3','2','2','2','2','2','3','3'},
    {'3','3','3','3','8','8','8','8','4','4','4','4'},
    {'6','6','6','2','2','2','2','6','6','6','4','4'},
    {'4','4','3','3','3','3','3','8','8','8','8','8'},
    {'8','8','8','3','3','3','3','2','2','7','7','7'},
    {'9','9','9','5','5','5','0','0','2','8','8','8'},
    {'8','8','8','8','4','4','4','1','1','1','1','1'},
    {'9','9','9','7','7','7','7','7','1','1','1','1'},
    {'6','6','6','6','9','9','9','9','3','3','3','3'},
    {'9','9','9','9','9','9','9','9','9','9','9','9'},
    {'9','9','3','3','3','3','3','7','7','7','7','7'},
    {'5','5','5','5','5','5','5','5','5','5','5','5'}};

    private LabelMaker lblMaker;

    @Before
    public void setUp(){
        lblMaker = new LabelMaker(sampleMap);
    }

    @Test
    public void testGetRegion(){
        Point[] region = {
            new Point(10, 7), new Point(10, 8), new Point(10, 9), new Point(10, 10), new Point(10, 11),
            new Point(11, 8), new Point(11, 9), new Point(11, 10), new Point(11, 11)
        };
        TreeSet<Point> regionSet = new TreeSet<Point>(Arrays.asList(region));
        
        int limit = region.length;

        for(int i = 0; i < limit; i++){
            Set<Point> actualRegion = lblMaker.getRegion(region[i]);
            Assert.assertEquals(actualRegion, regionSet);
        }
    }
}
