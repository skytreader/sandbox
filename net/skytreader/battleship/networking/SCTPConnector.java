package net.skytreader.battleship.networking;

import java.io.IOException;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import java.nio.ByteBuffer;

import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.SctpChannel;
import com.sun.nio.sctp.SctpServerChannel;

import net.skytreader.battleship.ui.BattleView;
import net.skytreader.battleship.game.BattleBoard;

public class SCTPConnector extends NetworkingInterface{
    
    private SctpChannel sctpChannel;
    private SocketAddress sa;
    
    /**
    Creates an SCTPChannel and binds the channel.
    */
    public SCTPConnector(String host, int inetAddress, int socketAddress)
      throws IOException{
        sa = new InetSocketAddress(host, inetAddress);
        sctpChannel = SctpChannel.open();
        sctpChannel.bind(new InetSocketAddress(socketAddress));
    }
    
    /**
    Connects the SCTP channel.
    */
    public void connect() throws IOException{
        sctpChannel.connect(sa, 1, 1);
    }

    public void sendMessage(byte[] b) throws Exception{
        MessageInfo outgoingMessage = MessageInfo.createOutgoing(null, 0);

        ByteBuffer bb = ByteBuffer.allocate(28);
        bb.put(b);
        bb.flip();

        sctpChannel.send(bb, outgoingMessage);
    }

    public MessageInfo receiveMessage() throws Exception{
        return sctpChannel.receive(ByteBuffer.allocate(28), null, null);
    }

    public boolean sendHit(int row, int col) throws Exception{
        sendMessage(translate(row, col).getBytes());
        receiveMessage();
        return false;
    }

    public int[] receiveHit() throws Exception{
        receiveMessage();
        return null;
    }

}
