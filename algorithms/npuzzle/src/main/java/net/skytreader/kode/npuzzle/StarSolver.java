package net.skytreader.kode.npuzzle;

import java.util.PriorityQueue;

import net.skytreader.kode.npuzzle.utils.ManhattanComparator;

public class StarSolver extends Solver{
    private PriorityQueue pq;
    private final int INITIAL_CAPACITY = 28;
    
    public StarSolver(NPuzzle np){
        super(np);
        pq = new PriorityQueue(INITIAL_CAPACITY, new ManhattanComparator());
    }

    @Override
    public void solve(){
    }
}
