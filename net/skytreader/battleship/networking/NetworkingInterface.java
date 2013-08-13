package net.skytreader.battleship.networking;

/**
Networking interface for battleship.

Based on http://sandarenu.blogspot.com/2009/05/sctp-client-server-in-java.html .

@author Chad Estioco
*/
public interface NetworkingInterface{
    
    public boolean sendHit(int row, int col);
    public void receiveHit();

}
