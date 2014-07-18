package net.skytreader.kode.ccl;

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
    /**
    Signals the connectivity style of this instance. True means it is 8-connected
    otherwise it is 4-connected.
    */
    private boolean is8conn;

    public LabelMaker(char[][] grid, boolean is8conn){
        this.grid = grid;
        this.is8conn = is8conn;
    }
    
    /**
    This actually performs connected-component labeling algorithm.
    */
    private void label(){
    }

    private boolean isCorner(int row, int col){
        int rowLimit = grid.length;
        int colLimit = grid[0].length;

        return ((row == 0 && col == 0) || (row == 0 && col == colLimit) ||
          (row == rowLimit && col == 0) || (row == rowLimit && col == colLimit));
    }
    
    /**
    Returns the neighbors of the cell described in row, col as an array. The
    neighbors are enumerated in row-major order (left-to-right, top-to-bottom).

    @param row
    @param col
    @return An array of Points signifying the neighbors of the cell described by
        the provided row, col combination. For every Point, Point.x is row,
        Point.y is col.
    */
    private Point[] getNeighbors(int row, int col){
        int rowLimit = grid.length;
        int colLimit = grid[0].length;

        if(is8conn){
            Point[] neighbors;

            if(row == 0 && col == 0){
                neighbors = new Point[0];
            } else if(row == 0 && col == colLimit){
                neighbors = new Point[1];
                neighbors[0] = new Point(row, col - 1);
            } else if(row == rowLimit && col == 0){
                neighbors = new Point[1];
                neighbors[0] = new Point(row - 1, col);
            } else{
                neighbors = new Point[2];
                neighbors[0] = new Point(row, col - 1);
                neighbors[1] = new Point(row - 1, col);
            }

            return neighbors;
        } else{
            Point[] neighbors;

            if(row == 0 && col == 0){
                neighbors = new Point[0];
            } else if(row == 0 && col == colLimit){
                neighbors = new Point[1];
                neighbors[0] = new Point(row, col - 1);
            } else if(row == rowLimit && col == 0){
                neighbors = new Point[2];
                neighbors[0] = new Point(row - 1, col);
                neighbors[1] = new Point(row - 1, col + 1);
            } else if (row == rowLimit & col == colLimit){
                neighbors = new Point[3];
                neighbors[0] = new Point(row, col - 1);
                neighbors[1] = new Point(row - 1, col - 1);
                neighbors[2] = new Point(row - 1, col);
            } else{
                neighbors = new Point[4];
                neighbors[0] = new Point(row, col - 1);
                neighbors[1] = new Point(row - 1, col - 1);
                neighbors[2] = new Point(row - 1, col);
                neighbors[3] = new Point(row - 1, col + 1);
            }

            return neighbors;
        }
    }

    /**
    Returns a set of points that includes the given point. Point.x is taken to
    be the row index while Point.y is taken to be the col index.
    */
    public Set<Point> getRegion(Point p){
        return null;
    }

}
