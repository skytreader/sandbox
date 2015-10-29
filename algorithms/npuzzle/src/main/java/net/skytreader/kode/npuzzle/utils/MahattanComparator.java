package net.skytreader.kode.npuzzle.utils;

import java.util.Comparator;

import net.skytreader.kode.npuzzle.NPuzzle;

/**
Compares two NPuzzles. A puzzle instance is less than another puzzle instance if
it is closer to the solved state (computed by Manhattan distance).
*/
public class ManhattanComparator implements Comparator<NPuzzle>{
    
    @Override
    public int compareTo(NPuzzle n1, NPuzzle n2){
        int n1Count = getDistanceFromSolved(n1);
        int n2Count = getDistanceFromSolved(n2);

        if(n1Count < n2Count){
            return -1;
        } else if(n1Count == n2Count){
            return 0;
        } else{
            return 1;
        }
    }

    private int getDistanceFromSolved(NPuzzle n){
        int limit = n.getSize();
        int distance = 0;

        for(int row = 0; row < limit; row++){
            for(int col = 0; col < limit; col++){
                int occupant = n.getTileAt(row, col);
                Point solvedPos = Algorithms.getSolvedPosition(occupant);
                Point actualPos = new Point(row, col);
                distance += Algorithms.computeManhattanDistance(solvedPos, actualPos);
            }
        }

        return distance;
    }
}
