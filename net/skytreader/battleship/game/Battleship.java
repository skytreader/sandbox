package net.skytreader.battleship.game;

import java.util.BitSet;

/**
Object model for a battleship in the board. Since only the vertical/horizontal
orientation of the ship matters (and not the actual direction it is facing), we
will assume that all ships are laid out from left-to-right or from top-to-
bottom.

@author Chad Estioco
*/
public class Battleship{
    
    // Common sizes for battleships.
    public final static int AIRCRAFT_CARRIER = 5;
    public final static int BATTLESHIP = 4;
    public final static int SUBMARINE = 3;
    public final static int DESTROYER = 3;
    public final static int PATROL_BOAT = 2;
    
    private int span;
    private int row;
    private int col; // Actually expressed as letters.
    private int hitPoints;
    
    // If true, the ship is oriented horizontally.
    private boolean isHorizontalOrientation;

    private BitSet hitAreas; // when all true, battleship is dead.
    
    /**
    Construct a Battleship with the given span, positioned at (row, col).

    Position should be taken as the first block occupied by this battleship; if
    it is oriented horizontally the leftmost block, otherwise the topmost block.

    @param s the span
    @param r the row
    @param c the column
    */
    public Battleship(int s, int r, int c){
        span = s;
        row = r;
        col = c;

        hitPoints = s;
        hitAreas = new BitSet(s);
    }

    public boolean isHorizontalOrientation(){
        return isHorizontalOrientation;
    }

    public void setIsHorizontalOrientation(boolean orientation){
        isHorizontalOrientation = orientation;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public int getSpan(){
        return span;
    }

    /**
    Damages the given area of the battleship.

    @param part index of the partition of the battleship to damage. Starts at 0.
        Hence the first block occupied by this battleship contains partition 0.
    */
    public void hit(int part){
        if(!hitAreas.get(part)){
            // Clear first shot on this part of the battleship.
            hitPoints--;
            hitAreas.flip(part);
        }
    }

    /**
    Determines whether this battleship is hit by a shot on the given board
    coordinates. Battleship is considered hit if shot is on a block occupied by
    this battleship.

    @param row row index of the hit block.
    @param col col index of the hit block.
    @return True if this battleship is hit, false otherwise.
    */
    public boolean isHit(int row, int col){
        if(isHorizontalOrientation && this.row == row){
            return col <= (this.col + span);
        } else if(!isHorizontalOrientation && this.col == col){
            return row <= (this.row + span);
        } else{
            return false;
        }
    }

    public boolean isAlive(){
        return hitPoints > 0;
    }

}
