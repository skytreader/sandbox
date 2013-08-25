package net.skytreader.battleship.game;

import java.awt.Rectangle;

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
    public BattleBoard(Battleship[] ships) throws GameConfigurationException{
        if(checkShips(ships)){
            this.ships = ships;
            // Implicitly all false
            trackingBoard = new boolean[boardWidth][boardHeight];
        } else{
            throw new GameConfigurationException("Ships cannot intersect!");
        }
    }

    public Battleship[] getShips(){
        return ships;
    }
    
    /**
    Checks that no ships are overlapping in the given configuration. Returns
    true if _no_ ships intersects with another ship, false otherwise.

    TODO O(n^2) ... can we do better?
    */
    private boolean checkShips(Battleship[] ships){
        Rectangle[] rs = toRects(ships);
        int limit = rs.length;

        for(int i = 0; i < limit; i++){
            for(int j = i + 1; j < limit; j++){
                if(rs[i].intersects(rs[j])){
                    return false;
                }
            }
        }

        return true;
    }
    
    /**
    Transforms the Battleships into Rectangles.
    */
    private Rectangle[] toRects(Battleship[] ships){
        int limit = ships.length;
        Rectangle[] rs = new Rectangle[limit];

        for(int i = 0; i < limit; i++){
            int height = ships[i].getIsHorizontalOrientation() ? 1 :
              ships[i].getSpan();
            int width = ships[i].getIsHorizontalOrientation() ?
              ships[i].getSpan() : 1;
            rs[i] = new Rectangle(ships[i].getRow(), ships[i].getCol(), width,
              height);
        }

        return rs;
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
                int hitPartition = 0;
                if(ships[i].getIsHorizontalOrientation()){
                    hitPartition = col - ships[i].getCol(); 
                } else{
                    hitPartition = row - ships[i].getRow();
                }
                ships[i].hit(hitPartition);
                setChanged();
                mark(row, col);
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
    private void mark(int row, int col){
        trackingBoard[row][col] = true;
    }

}
