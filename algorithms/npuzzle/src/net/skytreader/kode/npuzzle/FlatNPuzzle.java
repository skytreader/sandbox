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

    public void initialize(){
    }

    public void move(Direction d){
    }

    public int getTileAt(int row, int col){
        int index = translateRowCol(row, col);
        return puzzle[index];
    }

    public boolean isSolved(){
        return Arrays.equals(puzzle, solvedInstance);
    }

}
