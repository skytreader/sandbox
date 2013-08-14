package net.skytreader.battleship.game;

/**
Object model for a player's side of the battle board.

Observers are notified when a ship is hit. The hit ship is passed to the
Observers as an argument.

@author Chad Estioco
*/
public class BattleBoard extends Observable{
    
    // As long as we can notate the board size with letters, we go.
    public final static String LETTER_NOTATION = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 

    private int boardWidth = 10;
    private int boardHeight = 10;
    private Battleship[] ships;
    
    /**
    Construct a new board with the given ship configuration. Note that it is
    assumed that the given set of ships do not overlap each other.
    */
    public BattleBoard(Battleship[] ships){
        this.ships = ships;
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
                ships[i].hit(0);
                setChanged();
                notifyObservers(ships[i]);
                return true;
            }
        }

        return false;
    }

}
