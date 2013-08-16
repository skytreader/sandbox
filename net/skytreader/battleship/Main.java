package net.skytreader.battleship;

import java.awt.EventQueue;

import net.skytreader.battleship.game.Battleship;
import net.skytreader.battleship.game.BattleBoard;

import net.skytreader.battleship.networking.ZMQConnector;

import net.skytreader.battleship.ui.BattleRunnable;

import com.sun.nio.sctp.MessageInfo;

public class Main{
    public static void main(String[] args) throws Exception{
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        //SCTPConnector connector = new SCTPConnector("10.11.4.162", 1111, 16981);
        ZMQConnector connector = new ZMQConnector("10.11.2.189", 1115, 1118);
        BattleBoard model = new BattleBoard(null);
        EventQueue.invokeLater(new BattleRunnable(model, connector));
        //connector.sendMessage("Hello".getBytes());
        //MessageInfo mi = connector.receiveMessage();
    }
}
