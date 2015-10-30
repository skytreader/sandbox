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
        while(!this.np.isSolved()){
            NPuzzle[] nps = generateNewStates();
            for(int i = 0; i < 4; i++){
                pq.add(nps[0]);
            }

            this.np = pq.remove();
        }
    }

    private NPuzzle[] generateNewStates(){
        NPuzzle[] nextStates = new NPuzzle[4];

        for(int i = 0; i < 4; i++){
            nextStates[i] = new FlatNPuzzle(this.np);
        }

        nextStates[0].move(NPuzzle.Direction.UP);
        nextStates[1].move(NPuzzle.Direction.DOWN);
        nextStates[2].move(NPuzzle.Direction.LEFT);
        nextStates[3].move(NPuzzle.Direction.RIGHT);

        return nextStates;
    }
}
