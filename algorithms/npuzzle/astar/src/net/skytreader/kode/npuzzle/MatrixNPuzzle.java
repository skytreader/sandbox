package net.skytreader.kode.npuzzle;

import net.skytreader.kode.npuzzle.NPuzzle.Direction;

public class MatrixNPuzzle implements NPuzzle{
    /**
    This would always be a square matrix.
    */
    private int[][] matrixPuzzle;
    
    public MatrixNPuzzle(int n){
        matrixPuzzle = new int[n][n];
    }

    public void initialize(){
    }

    public void move(Direction d){
    }

    public int getTileAt(int row, int col){
        return 0;
    }
}
