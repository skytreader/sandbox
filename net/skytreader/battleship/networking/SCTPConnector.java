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

    public SCTPInterface(int inetAddress, int socketAddress, BattleBoard b){
        board = b;

        SocketAddress sa = new InetSocketAddress(inetAddress);
        sctpChannel = SctpChannel.open();
        sctpChannel.bind(new InetSocketAddress(socketAddress));
        sctpChannel.connect(sa, 1, 1);
    }

}
