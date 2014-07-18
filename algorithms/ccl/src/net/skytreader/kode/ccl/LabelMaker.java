package net.skytreader.kode.ccl;

import java.awt.Point;

import java.util.HashSet;
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
    private Set<Set<Point>> labels;

    public LabelMaker(char[][] grid, boolean is8conn){
        this.grid = grid;
        this.is8conn = is8conn;
        this.labels = new HashSet<Set<Point>>();
    }
    
    /**
    This actually performs connected-component labeling algorithm.
    */
    private void label(){
        int rowLimit = grid.length;
        int colLimit = grid[0].length;

        for(int row = 0; row < rowLimit; row++){
            for(int col = 0; col < colLimit; col++){
                Point thisPoint = new Point(row, col);
                Set<Point> thisLabel;
                Point[] neighbors = getNeighbors(row, col);
                // Try to merge
                int neighborCount = neighbors.length;

                for(int i = 0; i < neighborCount; i++){
                    Point p = neighbors[i];

                    if(thisLabel == null){
                        if(grid[p.x][p.y] == grid[row][col]){
                            Set<Point> label = getCurrentLabel(p.x, p.y, true);
                            label.add(thisPoint);
                            labels.add(label);
                            thisLabel = label;
                        }
                    } else{
                        if(grid[p.x][p.y] == grid[row][col]){
                            Set<Point> label = getCurrentLabel(p.x, p.y, true);
                            if(!thisLabel.equals(label)){
                                labels.remove(thisLabel);
                                label.addAll(thisLabel);
                                thisLabel = label;
                            }
                        }
                    }

                }
            }
        }
    }
    
    /**
    Iterates through the current labels we have and looks for one that contains
    the point described. If one is found, remove the label from our current set
    of labels and return. Else return null.

    (Removal is done so that if we want to merge this label with another in the
    run of our algorithm, we can do so consistently. If no merging will occur,
    it is the invoking function's responsibility to add the labels back.

    Time complexity: O(n) where n is the number of labels we currently have.

    @param row
    @param col
    @param remove
        If true, we remove the label set from labels when it is found. Else, no
        and just return the label set.
    @return The set of Points which the described row is included. If the given
    Point is not yet included in any labels, return null.
    */
    private Set<Point> getCurrentLabel(int row, int col, boolean remove){
        Point p = new Point();
        p.x = row;
        p.y = col;

        for(Set<Point> l : labels){
            if(l.contains(p)){
                if(remove){
                    labels.remove(l);
                }
                return l;
            }
        }

        return null;
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
    Returns a set of points that includes the given point.
    */
    public Set<Point> getRegion(int row, int col){
        return getCurrentLabel(row, col, false);
    }

}
