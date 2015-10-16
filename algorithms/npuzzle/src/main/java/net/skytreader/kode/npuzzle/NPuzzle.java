package net.skytreader.kode.npuzzle;

import java.awt.Point;

import net.skytreader.kode.npuzzle.exceptions.CorruptedPuzzleException;

/**
Standard interface to NPuzzle. All Points should be taken as (row, col).
*/
public interface NPuzzle{
    public enum Direction{
        UP(-1, 0),
        DOWN(1, 0),
        LEFT(0, -1),
        RIGHT(0, 1);
        
        private final int drow;
        private final int dcol;

        Direction(int dr, int dc){
            drow = dr;
            dcol = dc;
        }

        int drow(){
            return drow;
        }

        int dcol(){
            return dcol;
        }
    }

    /**
    Prepares the puzzle for solving. This method should be invoked first before
    doing anything with the puzzle instance. Between the construction of the
    object and the call to initialize, no guarantee is made as to the state the
    puzzle is in.

    Preferrably, this is where you will "randomize" the puzzle.
    */

    public void initialize();

    /**
    Set this puzzle's configuration based on the given integer array.
    The integer array is assumed to be given in row-major order. Included
    primarily for testing purposes. A cell containing 0 is taken as the location
    of the blank cell.

    This method should perform checks on the given configuration to ensure that
    it is still, loosely, an instance of the n-puzzle. Among these checks are
    <ul>
        <li>That there is one and only one blank tile.</li>
        <li>That it is still of the same size as declared when this object was
        instantiated.</li>
    </ul>
    Different implementations may perform additional checks. The puzzle should
    be left unaltered if any of these checks fail.

    @param int[] conf
    @throws CorruptedPuzzleException
        Thrown when the given configuration does not conform to the checks of
        this implementation.
    */
    public void setConfig(int[] conf) throws CorruptedPuzzleException;

    /**
    List out the current configuration as an array of integers. The configuration
    is assumed to be enumerated in row-major order.
    */
    public int[] toArray();

    /**
    Move the blank tile to the specified direction (effectively swapping places
    with the tile currently in that location).

    @param Direction d
    */
    public void move(Direction d);

    /**
    Check the tile at the given location.
    */
    public int getTileAt(int row, int col);

    /**
    The size of this puzzle. Size is defined as the length of <i>one side</i>
    of the puzzle. So a puzzle with 16 tiles (with one blank) will have a size
    of 4.
    */
    public int getSize();

    /**
    Return the position of the blank tile.
    */
    public Point getBlankPos();

    /**
    Checks whether this NPuzzle instance is already solved.
    */
    public boolean isSolved();

    /**
    Two NPuzzle instances are equal if and only if they have the same
    configuration of tiles. More formally, for every possible (row, col)
    combination, the getTileAtMethod should return equal values.
    */
    public boolean equals(Object o);

    public int hashCode();
}
