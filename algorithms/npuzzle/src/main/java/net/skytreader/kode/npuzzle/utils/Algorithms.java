package net.skytreader.kode.npuzzle.utils;

import java.awt.Point;

import java.util.Arrays;
import java.util.Stack;

import net.skytreader.kode.npuzzle.NPuzzle;

/**
Some handy methods that may be of use.
*/
public class Algorithms{
    public static int countInversions(int[] numlist){
        int limit = numlist.length;
        int[] listClone = Arrays.copyOf(numlist, limit);
        int inversionCount = 0;
        
        for(int i = 1; i < limit; i *= 2){
            inversionCount += mockMerge(listClone, i);
        }

        return inversionCount;
    }
    
    private static Stack<Integer> stackify(int[] num){
        Stack<Integer> stackified = new Stack<Integer>();
        for(int i = num.length - 1; i >= 0; i--){
            stackified.push(num[i]);
        }
        return stackified;
    }

    private static int mockMerge(int[] num, int skipCount){
        int limit = num.length;
        int l2Count = 0;

        for(int i = 0; i < limit;){
            Stack<Integer> partition1 = stackify(Arrays.copyOfRange(num, i, i + skipCount));
            int p2Limit = i + (2 * skipCount);

            if(p2Limit > limit){
                p2Limit = limit;
            }

            Stack<Integer> partition2 = new Stack<Integer>();

            if((i + skipCount) < limit){
                partition2 = stackify(Arrays.copyOfRange(num, i + skipCount, p2Limit));
            }

            int j = i;
            int sublimit = i + (2 * skipCount);

            // Perform merge sort.
            while(j < sublimit && !partition1.empty() && !partition2.empty()){
                if(partition1.peek() <= partition2.peek()){
                    num[j] = partition1.pop();
                } else{
                    num[j] = partition2.pop();
                    l2Count += partition1.size();
                }
                j++;
            }

            for(;j < p2Limit && !partition1.empty(); j++){
                num[j] = partition1.pop();
            }

            for(;j < p2Limit && !partition2.empty(); j++){
                num[j] = partition2.pop();
            }

            i = sublimit;
        }

        int leftover = limit % skipCount;

        if(leftover > 0){
            int cutpoint = limit - leftover;
            Stack<Integer> partition1 = stackify(Arrays.copyOfRange(num, 0, cutpoint));
            Stack<Integer> partition2 = stackify(Arrays.copyOfRange(num, cutpoint, limit));

            for(int i = 0; i < limit && !partition1.empty() && !partition2.empty(); i++){
                if(partition1.peek() < partition2.peek()){
                    num[i] = partition1.pop();
                } else{
                    num[i] = partition2.pop();
                    l2Count = partition1.size();
                }
            }
        }

        return l2Count;
    }

    public static boolean isSolvable(NPuzzle np){
        int puzzleSize = np.getSize();
        boolean isGridOdd = (puzzleSize % 2) != 0;
        boolean areInversionsEven = (Algorithms.countInversions(np.toArray(false)) % 2) == 0;

        if(isGridOdd){
            return areInversionsEven;
        } else{
            int blankRow = (np.getBlankPos()).x + 1;
            if((blankRow % 2) == (puzzleSize % 2)){
                return areInversionsEven;
            } else{
                return !areInversionsEven;
            }
        }
    }

    public static int computeManhattanDistance(Point p1, Point p2){
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }

    /**
    Get a tile's solved position in a two-dimensional grid. For a puzzle of size
    s, the solved position is the one where the blank (0) is at the bottom-right
    corner (size - 1, size - 1) and the tiles 1 to s-1 is laid out in row-
    major order from top to bottom.

    @param tileNo
        An integer specifying the label of the tile.
    @param puzzleSize
        An integer specifying the width/height of the puzzle instance.
    @throws IllegalArgumentException
        If the given tileNo is negative.
    */
    public static Point getSolvedPosition(int tileNo, int puzzleSize){
        if(tileNo < 0){
            throw new IllegalArgumentException("Tile numbers can't be negative.");
        }

        if(tileNo == 0){
            return new Point(puzzleSize - 1, puzzleSize - 1);
        } else{
            tileNo--;
            int row = tileNo / size;
            int col = tileNo % size;

            return new Point(row, col);
        }
    }
    
}
