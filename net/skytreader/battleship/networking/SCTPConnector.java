package net.skytreader.battleship.networking;

import java.io.IOException;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import java.nio.ByteBuffer;

import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.SctpChannel;
import com.sun.nio.sctp.SctpServerChannel;

import net.skytreader.battleship.game.BattleBoard;

public class SCTPConnector implements NetworkingInterface{
    
    private SctpChannel sctpChannel;
    private BattleBoard board;

    public SCTPInterface(String host, int inetAddress, int socketAddress, BattleBoard b){
        board = b;

        SocketAddress sa = new InetSocketAddress(host, inetAddress);
        sctpChannel = SctpChannel.open();
        sctpChannel.bind(new InetSocketAddress(socketAddress));
        sctpChannel.connect(sa, 1, 1);
    }

    private String translate(int row, int col){
        return "" + BattleBoard.LETTER_NOTATION.charAt(col) + row
    }

    private void sendMessage(byte[] b) throws Exception{
        MessageInfo outgoingMessage = MessageInfo.createOutgoing(null, 0);

        ByteBuffer bb = ByteBuffer.allocate(28);
        bb.put(b);
        bb.flip();

        sctpChannel.send(bb, outgoingMessage);
    }

    private MessageInfo receiveMessage() throws Exception{
        return sctpChannel.receive(ByteBuffer.allocate(28), null, null);
    }

    public boolean sendHit(int row, int col) throws Exception{
        sendMessage(translate(row, col).getBytes());
        receiveMessage();
        return false;
    }

    public void receiveHit() throws Exception{
        receiveMessage();
    }

}
