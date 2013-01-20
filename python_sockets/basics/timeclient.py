#! /usr/bin/env python3

import socket
import sys

if __name__ == "__main__":
    if len(sys.argv) != 4:
        print("Usage: python3 timeclient.py <clientname> <host> <port>")
        exit()

    host = sys.argv[2]
    port = int(sys.argv[3])
    sock = socket.socket()
    print("Connecting to server...")
    sock.connect((host, port))
    print("CONNECTED!")

    # send your name
    sock.send(sys.argv[1].encode())
    # warning: possible data truncation!
    server_msg = sock.recv(1024)
    print("server@" + host + ":" + sys.argv[3] + " : " + server_msg.decode())
