package net.skytreader.kode.npuzzle;

/**
Standard interface to NPuzzle.
*/
public interface NPuzzle{
    public void move(int row, int col);
    public int getTileAt(int row, int col);
}
