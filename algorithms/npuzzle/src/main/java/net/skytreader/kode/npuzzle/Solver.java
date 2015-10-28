package net.skytreader.kode.npuzzle;

public abstract class Solver{
    protected NPuzzle npuzzle;

    public Solver(NPuzzle np){
        npuzzle = np;
    }

    public abstract void solve();
}
