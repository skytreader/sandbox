package net.skytreader.kode.npuzzle;

import net.skytreader.kode.npuzzle.NPuzzle.Direction;

/**
For this class, the NPuzzle is represented as follows:
 (1) all none-blank cells are represented by an integer (1 to (size ^ 2) - 1)
 (2) the blank cell is represented by 0.
*/
public class MatrixNPuzzle implements NPuzzle{
    /**
    This would always be a square matrix.
    */
    private int[][] matrixPuzzle;
    private int[] unfolded;
    
    public MatrixNPuzzle(int n){
        matrixPuzzle = new int[n][n];
        int limit = n * n;
        unfolded = new int[limit];

        for(int i = 0; i < limit; i++){
            unfolded[i] = i;
        }
    }
    
    private int countInversions(){
        return 0;
    }

    private boolean isSolvable(){
        return true;
    }

    public void initialize(){
    }

    public void move(Direction d){
    }

    public int getTileAt(int row, int col){
        return 0;
    }
    
    /**
    For the purposes of this class, the instance is considered solved if
        (1) The tiles are laid out 1 to (size ^ 2) - 1 in row major order; and
        (2) The blank tile is at the upper-left corner.
    */
    public boolean isSolved(){
        int expected = 0;
        int limit = matrixPuzzle.length;

        for(int row = 0; row < limit; row++){
            for(int col = 0; col < limit; col++){
                if(matrixPuzzle[row][col] != expected){
                    return false;
                }
                expected++;
            }
        }
        return true;
    }
}
