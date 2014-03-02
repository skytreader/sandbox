package net.skytreader.kode.npuzzle;

public class MatrixNPuzzle implements NPuzzle{
    /**
    This would always be a square matrix.
    */
    private int[][] matrixPuzzle;
    
    public MatrixNPuzzle(int n){
        matrixPuzzle = new int[n][n];
    }

    public void move(int row, int col){
    }

    public void getTileAt(int row, int col){
    }
}
