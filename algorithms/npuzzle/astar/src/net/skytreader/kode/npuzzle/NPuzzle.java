package net.skytreader.kode.npuzzle;

/**
Standard interface to NPuzzle.
*/
public interface NPuzzle{
    /**
    Prepares the puzzle for solving. This method should be invoked first before
    doing anything with the puzzle instance. Between the construction of the
    object and the call to initialize, no guarantee is made as to the state the
    puzzle is in.
    */

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
    Where you could "randomize" the NPuzzle instance.
    */
    public void initialize();
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
}
