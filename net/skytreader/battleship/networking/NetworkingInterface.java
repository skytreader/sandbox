package net.skytreader.battleship.networking;

import java.io.IOException;

/**
Networking interface for battleship.

@author Chad Estioco
*/
public abstract class NetworkingInterface{
    
    public final static String LETTER_NOTATION = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 

    public abstract boolean sendHit(int row, int col) throws Exception;
    public abstract int[] receiveHit() throws Exception;
    public abstract void connect() throws IOException;

    protected int[] translate(String s){
        String substr = s.substring(1, s.length());
        int[] translation = new int[2];
        translation[0] = NetworkingInterface.LETTER_NOTATION.indexOf(s.charAt(0));
        translation[1] = Integer.parseInt(substr) - 1;
        return translation;
    }

    protected String translate(int row, int col){
        return "" + NetworkingInterface.LETTER_NOTATION.charAt(col) + (row + 1);
    }
}
