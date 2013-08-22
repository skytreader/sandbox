package net.skytreader.battleship.game;

import java.util.Observable;

/**
Object model for a player's side of the battle board.

Observers are notified when a ship is hit. The hit ship is passed to the
Observers as an argument.

@author Chad Estioco
*/
public class BattleBoard extends Observable{

    private int boardWidth = 10;
    private int boardHeight = 10;
    private Battleship[] ships;
    private boolean[][] trackingBoard;
    
    /**
    Construct a new board with the given ship configuration. Note that it is
    assumed that the given set of ships do not overlap each other.
    */
    public BattleBoard(Battleship[] ships){
        this.ships = ships;
        // Implicitly all false
        trackingBoard = new boolean[boardWidth][boardHeight];
    }
    
    /**
    Receive a hit on the block described by row and col. Will damage any
    affected battleships.

    @param row
    @param col
    @return True if the attack hit any battleship, false otherwise.
    */
    public boolean hit(int row, int col){
        int limit = ships.length;

        for(int i = 0; i < limit; i++){
            if(ships[i].isHit(row, col)){
                //FIXME Hit the correct partition
                //int hitPartition = 0;
                //if(ships[i].getIsHorizontalOrientation()){
                //    hitPartition 
                ships[i].hit(0);
                setChanged();
                notifyObservers(ships[i]);
                return true;
            }
        }

        return false;
    }

    /**
    Marks the given block in the tracking board (i.e., indeed, a ship was hiding
    there).

    @param row
    @param col
    */
    public void mark(int row, int col){
    }

}
