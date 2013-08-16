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

    public ZMQConnector(String host, int sendPort, int receivePort) throws Exception{
        Context c = ZMQ.context(1);
        sendSocket = c.socket(ZMQ.NOBLOCK);
        sendSocket.setIdentity("chadsend".getBytes());

        receiveSocket = c.socket(ZMQ.NOBLOCK);
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

    private String translate(int row, int col){
        return "" + BattleBoard.LETTER_NOTATION.charAt(col) + row;
    }

    public boolean sendHit(int row, int col) throws Exception{
        sendSocket.send(translate(row, col));
        return false;
    }

    public void receiveHit() throws Exception{
        String s = sendSocket.recvStr();
        System.out.println("Received: " + s);
    }
}
