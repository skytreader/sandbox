package net.skytreader.kode.cll;

import java.awt.Point;
import java.util.Set;
import java.util.TreeSet;

/**
An implementation of connected-components labeling, but we should be able to
use this on a grid with more than two distinct cells. Input, for now, is expected
to be a two-dimensional array of chars.

This also allows you to query the region to which a given cell belongs to. It
is recommended that you assign one grid to one instance of LabelMaker.

@author Chad Estioco
*/

public class LabelMaker{
    
    private char[][] grid;

    public LabelMaker(char[][] grid){
        this.grid = grid;
    }

    public Set<Point> getRegion(Point p){
        return null;
    }

}
