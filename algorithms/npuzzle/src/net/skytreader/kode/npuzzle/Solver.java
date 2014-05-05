package net.skytreader.kode.npuzzle;

public interface Solver{
    public boolean isSolvable();
    public void solve(int row, int col);
}
