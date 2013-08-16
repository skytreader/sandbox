package net.skytreader.battleship.networking;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;
import org.zeromq.ZMQ.Context;

import java.io.IOException;

import net.skytreader.battleship.game.BattleBoard;

public class ZMQConnector extends NetworkingInterface{
    
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
