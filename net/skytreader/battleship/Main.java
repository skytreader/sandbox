package net.skytreader.battleship;

import net.skytreader.battleship.game.Battleship;
import net.skytreader.battleship.game.BattleBoard;

import net.skytreader.battleship.networking.SCTPConnector;

import com.sun.nio.sctp.MessageInfo;

public class Main{
    public static void main(String[] args) throws Exception{
        SCTPConnector connector = new SCTPConnector("10.11.4.162", 1111, 16981,
            null);
        connector.sendMessage("Hello".getBytes());
        MessageInfo mi = connector.receiveMessage();
    }
}
