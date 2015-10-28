package net.skytreader.kode.npuzzle;

import com.google.common.collect.HashMultiset;

import java.awt.Point;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import net.skytreader.kode.npuzzle.NPuzzle.Direction;
import net.skytreader.kode.npuzzle.exceptions.CorruptedPuzzleException;
import net.skytreader.kode.npuzzle.utils.Algorithms;

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
    private int entropyFactor = 6;

    /**
    Create an instance of FlatNPuzzle. Upon construction, the puzzle is initially
    solved. Call initialize() to shuffle the puzzle.

    @param size
        The length of the side of an n puzzle. A puzzle with 16 blocks will have
        size = 4 while a puzzle with 9 blocks will have size = 3.
    */
    public FlatNPuzzle(int size){
        this.size = size;
        int limit = size * size;
        puzzle = new int[limit];
        solvedInstance = new int[limit];

        for(int i = 0; i < limit; i++){
            solvedInstance[i] = i + 1;
            puzzle[i] = i + 1;
        }

        solvedInstance[limit - 1] = 0;
        puzzle[limit - 1] = 0;
    }

    public void setConfig(int[] conf) throws CorruptedPuzzleException{
        if(conf.length != this.puzzle.length){
            throw new CorruptedPuzzleException("Attempting to change the puzzle size. Setting to " +
              conf.length + " but should be " + this.puzzle.length);
        }
        LinkedList<Integer> confList = new LinkedList<Integer>();
        for(int i = 0; i < conf.length; i++){
            confList.add(conf[i]);
        }
        HashMultiset<Integer> counter = HashMultiset.create(confList);

        if(counter.count(0) != 1){
            throw new CorruptedPuzzleException("Attempting to set more than one blank tile.");
        }

        this.puzzle = conf;
    }

    public int[] toArray(boolean includeBlank){
        if(includeBlank){
            return this.puzzle;
        } else{
            int puzzleLength = this.puzzle.length;
            int[] nonblank = new int[puzzleLength - 1];
            int nonblankIndex = 0;

            for(int i = 0; i < puzzleLength; i++){
                if(this.puzzle[i] != 0){
                    nonblank[nonblankIndex] = this.puzzle[i];
                    nonblankIndex++;
                }
            }

            return nonblank;
        }
    }

    public int getEntropyFactor(){
        return entropyFactor;
    }

    public int getSize(){
        return size;
    }

    /**
    @param ef
    @throws IllegalArgumentException
        If the given entropy factor is more than the maximum number of
        inversions possible for this state.
    */
    public void setEntropyFactor(int ef){
        int x = puzzle.length - 1;
        int entropyLimit = ((x * x) - x) / 2;

        if(ef > entropyLimit){
            throw new IllegalArgumentException("Maximum entropy limit for this instance is " + entropyLimit);
        }

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
    Add a certain number of inversions in the puzzle (determined by entropyFactor).

    @param addEven
        If true, add an even number of inversions. Otherwise, add aan odd number
        of inversions.
    */
    private void makeInversions(boolean addEven){
        boolean isEFEven = (entropyFactor % 2) == 0;
        int inversionAdd = 0;

        if(isEFEven){
            if(addEven){
                inversionAdd = entropyFactor;
            } else{
                inversionAdd = entropyFactor - 1;
            }
        } else{
            if(addEven){
                inversionAdd = entropyFactor - 1;
            } else{
                inversionAdd = entropyFactor;
            }
        }
        
        int limit = puzzle.length;

        while(Algorithms.countInversions(this.toArray(false)) < inversionAdd){
            for(int i = 0; i < limit; i++){
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
    Move the blank space to a random position in the puzzle.

    @param evenRow
        Boolean. If true, you are sure that the blank row is left in an even row
        <i>from the bottom</i>.
    */
    private void moveBlankRandomly(boolean evenRow){
        Random r = new Random();
        int limit = this.size;
        int randomCol = r.nextInt(limit);
        int randomRow = 0;

        if(evenRow){
            do{
                randomRow = r.nextInt(limit);
            } while((randomRow % 2) != 0);
        } else{
            do{
                randomRow = r.nextInt(limit);
            } while((randomRow % 2) != 1);
        }
        System.out.println("we are in row #" + randomRow);

        for(int row = this.getBlankPos().x; row != randomRow; row = this.getBlankPos().x){
            this.move(NPuzzle.Direction.UP);
        }

        for(int col = this.getBlankPos().y; col != randomCol; col = this.getBlankPos().y){
            this.move(NPuzzle.Direction.LEFT);
        }
    }

    /**
    Shuffles the puzzle such that it is non trivial to solve.

    If m is odd, inversion count must be even.
    If m is even and the missing tile is on an (even|odd), counting from below,
    and the number of inversions is (odd|even).
    */
    public void initialize(){
        NPuzzle.Direction[] directions = NPuzzle.Direction.values();
        int dirLimit = directions.length;
        Random r = new Random();
        int hCount = 0;
        int vCount = 0;

        for(int i = 0; i < this.entropyFactor; i++){
            NPuzzle.Direction d = directions[r.nextInt(dirLimit)];
            
            if((hCount == 0 || hCount == 1) && d == NPuzzle.Direction.RIGHT){
                d = NPuzzle.Direction.LEFT;
            } else if((vCount == 0 || vCount == 1) && d == NPuzzle.Direction.DOWN){
                d = NPuzzle.Direction.UP;
            }

            this.move(d);

            switch(d){
                case UP:
                    hCount++;
                    break;
                case DOWN:
                    hCount--;
                    break;
                case LEFT:
                    vCount++;
                    break;
                case RIGHT:
                    vCount--;
                    break;
            }
        }
    }
    
    public Point getBlankPos(){
        return translateIndex(getBlankIndex());
    }

    private int getBlankIndex(){
        int blankIndex = 0;
        int limit = this.puzzle.length;
        for(; blankIndex < limit; blankIndex++){
            if(puzzle[blankIndex] == 0){
                break;
            }
        }

        if(blankIndex >= puzzle.length){
            throw new CorruptedPuzzleException("blank tile not found!");
        }

        return blankIndex;
    }

    public void move(Direction d){
        int blankIndex = getBlankIndex();
        Point blankPoint = translateIndex(blankIndex);
        int newRow = blankPoint.x + d.drow();
        int newCol = blankPoint.y + d.dcol();
        System.out.println("new row and col " + newRow + " " + newCol);

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

    public boolean equals(Object o){
        if(!(o instanceof NPuzzle)){
            return false;
        }
        
        NPuzzle np = (NPuzzle) o;
        int npLimit = np.getSize();

        if(npLimit != this.getSize()){
            return false;
        }

        for(int row = 0; row < npLimit; row++){
            for(int col = 0; col < npLimit; col++){
                if(np.getTileAt(row, col) != this.getTileAt(row, col)){
                    return false;
                }
            }
        }

        return true;
    }

    /**
    The hashcode is just the hashcode of the underlying array representation.
    */
    public int hashCode(){
        return Arrays.hashCode(puzzle);
    }

}
