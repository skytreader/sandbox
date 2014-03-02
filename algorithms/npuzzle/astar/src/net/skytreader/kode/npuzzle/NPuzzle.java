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
    public void initialize();
    public void move(int row, int col);
    public int getTileAt(int row, int col);
}
