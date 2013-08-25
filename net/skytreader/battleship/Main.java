package net.skytreader.battleship;

import java.awt.EventQueue;

import net.skytreader.battleship.game.Battleship;
import net.skytreader.battleship.game.BattleBoard;

//import net.skytreader.battleship.networking.ZMQConnector;
import net.skytreader.battleship.networking.SCTPConnector;

import net.skytreader.battleship.ui.BattleRunnable;

import net.skytreader.battleship.utils.ConfigReader;

import com.sun.nio.sctp.MessageInfo;

public class Main{
    public static final String VERSION = "0.1.0";

    public static void main(String[] args) throws Exception{
        if(args.length < 1){
            System.out.println("Usage: java [extra options] net.skytreader.battleship.Main [.cfg file]");
            System.exit(1);
        }

        ConfigReader cfgReader = new ConfigReader(args[0]);
        String host = cfgReader.getServerHost();
        int inPort = cfgReader.getInboundPort();
        int outPort = cfgReader.getOutboundPort();

        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        SCTPConnector connector = new SCTPConnector(host, inPort, outPort);
        //ZMQConnector connector = new ZMQConnector("10.11.2.189", 1115, 1118);
        BattleBoard model = new BattleBoard(new Battleship[0]);
        EventQueue.invokeLater(new BattleRunnable(model, connector));
        //connector.sendMessage("Hello".getBytes());
        //MessageInfo mi = connector.receiveMessage();
    }
}
