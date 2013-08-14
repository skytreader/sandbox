package net.skytreader.battleship;

import java.awt.EventQueue;

import net.skytreader.battleship.game.Battleship;
import net.skytreader.battleship.game.BattleBoard;

import net.skytreader.battleship.networking.SCTPConnector;

import net.skytreader.battleship.ui.BattleRunnable;

import com.sun.nio.sctp.MessageInfo;

public class Main{
    public static void main(String[] args) throws Exception{
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        BattleBoard model = new BattleBoard(null);
        EventQueue.invokeLater(new BattleRunnable(model));

        SCTPConnector connector = new SCTPConnector("10.11.4.162", 1111, 16981,
            null);
        connector.sendMessage("Hello".getBytes());
        MessageInfo mi = connector.receiveMessage();
    }
}
