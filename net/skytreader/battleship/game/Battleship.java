package net.skytreader.battleship.game;

import java.util.BitSet;

/**
Object model for a battleship in the board.

@author Chad Estioco
*/
public class Battleship{
    
    // Common sizes for battleships.
    private final static int AIRCRAFT_CARRIER = 5;
    private final static int BATTLESHIP = 4;
    private final static int SUBMARINE = 3;
    private final static int DESTROYER = 3;
    private final static int PATROL_BOAT = 2;
    
    private int width;
    private int row;
    private int col; // Actually expressed as letters.
    private int hitPoints;

    private BitSet hitAreas; // when all true, battleship is dead.
    
    /**
    Construct a Battleship with the given width, positioned at (row, col).

    @param w the width
    @param r the row
    @param c the column
    */
    public Battleship(int w, int r, int c){
        width = w;
        row = r;
        col = c;

        life = w;
        hitAreas = new BitSet(w);
    }

    public void hit(int part){
        if(!hitAreas.get(part)){
            // Clear first shot on this part of the battleship.
            life--;
            hitAreas.toggle(part);
        }
    }

    public void isAlive(){
        return life > 0;
    }

}
