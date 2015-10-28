package net.skytreader.kode.npuzzle;

import java.util.PriorityQueue;

public class StarSolver extends Solver{
    private PriorityQueue pq;
    
    public StarSolver(NPuzzle np){
        super(np);
        pq = new PriorityQueue();
    }

    @Override
    public void solve(){
    }
}
