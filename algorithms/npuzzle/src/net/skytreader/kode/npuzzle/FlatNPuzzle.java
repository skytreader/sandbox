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
    /**
    entropyFactor determines how "messed-up" a puzzle do we get.
    */
    private int entropyFactor = 6; // FIXME What's the max value for entropyFactor?

    public FlatNPuzzle(int size){
        this.size = size;
        int limit = size * size;
        puzzle = new int[limit];

        for(int i = 0; i < limit; i++){
            solvedInstance[i] = i;
        }
    }

    public int getEntropyFactor(){
        return entropyFactor;
    }

    public int getSize(){
        return size;
    }

    public void setEntropyFactor(int ef){
        entropyFactor = ef;
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
    Add a certain number of inversions in the puzzle (determined by entropyFactor).

    @param isEven
        If true, add an even number of inversions. Otherwise, add an odd number
        of inversions.
    */
    private void makeInversions(boolean isEven){
        // TODO experiment on this function more
        // FIXME This method could hang if we don't validate entropyFactor.
        // get the current number of inversions (ideally, this is always 0 when
        // this method is called.
        boolean isEFEven = (entropyFactor % 2) == 0;
        int inversionAdd = 0;

        if(isEven && isEFEven){
            inversionAdd = entropyFactor;
        } else if(isEven && !isEFEven){
            inversionAdd = entropyFactor + 1;
        } else if(!isEven && isEFEven){
            inversionAdd = entropyFactor + 1;
        } else{
            inversionAdd = entropyFactor;
        }
        
        int lastInversion = 0;
        int limit = puzzle.length;

        while(inversionCount() < inversionAdd){
            for(int i = lastInversion; i < limit; i++){
                for(int j = i + 1; j < limit; j++){
                    if((puzzle[i] != 0 || puzzle[j] != 0) && puzzle[i] < puzzle[j]){
                        // swap without extra variable!
                        puzzle[i] += puzzle[j];
                        puzzle[j] = puzzle[i] - puzzle[j];
                        puzzle[i] -= puzzle[j];
                    }
                }
            }
        }
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
            makeInversions(true);
        } else{
            Point blankPosition = getBlankPos();
            int blankRow = blankPosition.x + 1; // Add 1 to translate from indices to "normal" counting

            if((blankRow % 2) == 0){
                // Add an odd number of inversions.
                makeInversions(false);
            } else{
                // Add an even number of inversions.
                makeInversions(true);
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
