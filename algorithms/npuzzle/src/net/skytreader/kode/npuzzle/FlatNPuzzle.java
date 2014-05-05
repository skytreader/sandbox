package net.skytreader.kode.npuzzle;

import java.awt.Point;

import java.util.Arrays;

import net.skytreader.kode.npuzzle.NPuzzle.Direction;

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
    private int translateIndex(int index){
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

    public void initialize(){
    }

    public void move(Direction d){
        // Find the blank tile
        int blankIndex = 0;
        for(; blankIndex < size; blankIndex++){
            if(puzzle[blankIndex] == 0){
                break;
            }
        }

        //TODO If loop stops and we haven't found any blank tile, raise exception.

        Point p = translateIndex(blankIndex)
        int newRow = p.x - p.drow();
        int newCol = p.y - p.dcol();

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
