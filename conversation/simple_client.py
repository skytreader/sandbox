#! /usr/bin/env python3

import socket
import sys

"""
A simple (quick hack) client for the conversation protocol.
"""

CLIENT_COMMANDS = ("LOGIN", "TCE", "DATEWEATHER", "LOGOUT")

class HackBuffer(object):
    
    def __init__(self, socksource):
        self.byte_buffer = []
        self.socksource = socksource

    def get_packet(self):
        recv = self.socksource.recv(1024)
        print dir(recv)
        print type(recv)

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print "Usage: python3 " + sys.argv[0] + " <bindhost> <bindport>"
        exit(1)

    host = sys.argv[1]
    port = int(sys.argv[2])

    server_socket = socket.socket()
    print("Connecting to " + host + ":" + sys.argv[2])
    server_socket.connect((host, port))
    print("Connection established")

    reply_buffer = HackBuffer(server_socket)

    server_socket.send("Hello")
    print("Sent 'Hello'")
    reply_buffer.get_packet()
    
    server_socket.close()
