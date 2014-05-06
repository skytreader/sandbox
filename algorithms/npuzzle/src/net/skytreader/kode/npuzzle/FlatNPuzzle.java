package net.skytreader.kode.npuzzle;

import java.awt.Point;

import java.util.Arrays;

import net.skytreader.kode.npuzzle.NPuzzle.Direction;
import net.skytreader.kode.npuzzle.exceptions.CorruptedPuzzleException;

/**
For this class, the NPuzzle is represented as follows:
  (1) all none-blank cells are represented by an integer (1 to (size ^ 2) - 1)
  (2) the blank cell is represented by 0.

@author Chad Estioco
*/
public class FlatNPuzzle implements NPuzzle{
    
    private int[] puzzle;
    private int[] solvedInstance;
    private int size;

    public FlatNPuzzle(int size){
        this.size = size;
        int limit = size * size;
        puzzle = new int[limit];

        for(int i = 0; i < limit; i++){
            solvedInstance[i] = i;
        }
    }

    public int getSize(){
        return size;
    }
    
    /**
    Given the row and column, compute the index in the flat representation.
    */
    private int translateRowCol(int row, int col){
        return (row * size) + col;
    }

    /**
    Given the index, compute the row, col in the grid representation. Returns a
    Point object with the row in Point.x and col in Point.y.
    */
    private Point translateIndex(int index){
        int row = index / size;
        int col = index % size;
        return new Point(row, col);
    }
    
    /**
    O(n^2) and you can definitely do better.
    */
    private int inversionCount(){
        int inversionCount = 0;

        for(int i = 0; i < size; i++){
            for(int j = i + 1; j < size; j++){
                if(puzzle[i] > puzzle[j]){
                    inversionCount++;
                }
            }
        }

        return inversionCount;
    }

    /**
    "Randomizes" the puzzle. Afterwards, the puzzle must remain solvable.

    If m is odd, inversion count must be even.
    If m is even and the missing tile is on an (even|odd), counting from below,
    and the number of inversions is (odd|even).
    */
    public void initialize(){
        int inversionCount = inversionCount();
        if((size % 2) == 1){
            // Add an even number of inversions.
        } else{
            Point blankPosition = getBlankPos();

            if((inversionCount % 2) == 0){ // even number of inversions
                // Easier since the polarity of rows do not change going from
                // below or above.
            }
        }
    }
    
    public Point getBlankPos(){
        return translateIndex(getBlankIndex());
    }

    private int getBlankIndex(){
        int blankIndex = 0;
        for(; blankIndex < size; blankIndex++){
            if(puzzle[blankIndex] == 0){
                break;
            }
        }

        if(blankIndex <= size){
            throw new CorruptedPuzzleException("blank tile not found!");
        }

        return blankIndex;
    }

    public void move(Direction d){
        int blankIndex = getBlankIndex();
        Point blankPoint = translateIndex(blankIndex);
        int newRow = blankPoint.x - d.drow();
        int newCol = blankPoint.y - d.dcol();

        int moveIndex = translateRowCol(newRow, newCol);

        // Swap blankIndex and moveIndex
        int temp = puzzle[moveIndex];
        puzzle[moveIndex] = puzzle[blankIndex];
        puzzle[blankIndex] = temp;
    }

    public int getTileAt(int row, int col){
        int index = translateRowCol(row, col);
        return puzzle[index];
    }

    public boolean isSolved(){
        return Arrays.equals(puzzle, solvedInstance);
    }

}
