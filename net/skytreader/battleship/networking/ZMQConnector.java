package net.skytreader.battleship.networking;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;
import org.zeromq.ZMQ.Context;

import java.io.IOException;

import net.skytreader.battleship.game.BattleBoard;

public class ZMQConnector implements NetworkingInterface{
    
    private Socket sendSocket;
    private Socket receiveSocket;

    private String host;
    private int sendPort;
    private int receivePort;

    public ZMQConnector(String host, int sendPort, int receivePort)
      throws Exception{
        Context c = ZMQ.context(1);
        sendSocket = c.socket(ZMQ.PUB);
        sendSocket.setIdentity("chadsend".getBytes());

        receiveSocket = c.socket(ZMQ.SUB);
        receiveSocket.subscribe("".getBytes());
        receiveSocket.setIdentity("chadrecv".getBytes());

        this.host = host;
        this.sendPort = sendPort;
        this.receivePort = receivePort;
    }

    public void connect() throws IOException{
        sendSocket.connect("tcp://" + host + ":" + sendPort);
        System.out.println("Connected for send.");
        receiveSocket.connect("tcp://" + host + ":" + receivePort);
        System.out.println("Connected for receive.");
    }

    // FIXME Change NetworkingInterface to an abstract class and implement these methods
    private String translate(int row, int col){
        return "" + BattleBoard.LETTER_NOTATION.charAt(col) + (row + 1);
    }

    private int[] translate(String s){
        String substr = s.substring(1, s.length());
        int[] translation = new int[2];
        translation[0] = BattleBoard.LETTER_NOTATION.indexOf(s.charAt(0));
        translation[1] = Integer.parseInt(substr) - 1;
        return translation;
    }

    public boolean sendHit(int row, int col) throws Exception{
        sendSocket.send(translate(row, col));
        return false;
    }

    public int[] receiveHit() throws Exception{
        String s = receiveSocket.recvStr();
        System.out.println("Received: " + s);
        return translate(s);
    }
}
