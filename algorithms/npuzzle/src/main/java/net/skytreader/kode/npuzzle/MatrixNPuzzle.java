package net.skytreader.kode.npuzzle;

import java.awt.Point;

import net.skytreader.kode.npuzzle.NPuzzle.Direction;
import net.skytreader.kode.npuzzle.exceptions.CorruptedPuzzleException;

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
    private int[] unfoldedSolved;
    private Point blankPos;
    
    public MatrixNPuzzle(int n){
        matrixPuzzle = new int[n][n];
        int limit = n * n;

        for(int i = 0; i < limit; i++){
            unfoldedSolved[i] = i;
        }
    }

    public void setConfig(int[] conf) throws CorruptedPuzzleException{
        int width = matrixPuzzle.length;
        for(int i = 0; i < width; i++){
            for(int j = 0; j < matrixPuzzle[i].length; j++){
                matrixPuzzle[i][j] = conf[j + (i * width)];
            }
        }
    }

    public int[] toArray(boolean includeBlank){
        int sideLen = matrixPuzzle.length;
        int limit = sideLen * sideLen;
        int[] arrRep = new int[limit];

        for(int i = 0; i < limit; i++){
            int row = i / sideLen;
            int col = i % sideLen;
            arrRep[i] = matrixPuzzle[row][col];
        }

        return arrRep;
    }

    public Point getBlankPos(){
        return blankPos;
    }

    public int getSize(){
        return matrixPuzzle.length;
    }
    
    /*
    FIXME Uses O(n^2) and other inefficiencies. You can definitely do better.
    */
    private int countInversions(){
        int inversionCount = 0;
        int[] unfolded = unfold();
        int limit = unfolded.length;
        
        for(int i = 0; i < limit; i++){
            for(int j = i + 1; j < limit; j++){
                if(unfolded[i] > unfolded[j] && unfolded[j] != 0){
                    inversionCount++;
                }
            }
        }

        return inversionCount;
    }

    private int[] unfold(){
        int limit = matrixPuzzle.length * matrixPuzzle.length;
        int[] unfolded = new int[limit];
        int unfoldIndex = 0;

        for(int row = 0; row < matrixPuzzle.length; row++){
            for(int col = 0; col < matrixPuzzle.length; col++){
                unfolded[unfoldIndex] = matrixPuzzle[row][col];
                unfoldIndex++;
            }
        }

        return unfolded;
    }

    private boolean isSolvable(){
        int inversionCount = countInversions();
        int n = matrixPuzzle.length;
        
        if((n % 2) == 1 && (inversionCount % 2) == 0){
            return true;
        }

        return false;
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
