package net.skytreader.battleship.networking;

import java.io.IOException;

/**
Networking interface for battleship.

@author Chad Estioco
*/
public interface NetworkingInterface{
    
    public boolean sendHit(int row, int col) throws Exception;
    public int[] receiveHit() throws Exception;
    public void connect() throws IOException;

}
