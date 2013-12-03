#! /usr/bin/env python3

import socket
import sys

"""
A simple (quick hack) client for the conversation protocol.
"""

CLIENT_COMMANDS = ("LOGIN", "TCE", "DATEWEATHER", "LOGOUT")

class HackBuffer(object):
    
    def __init__(self):
        self.byte_buffer = []

    def get_packet(self):
        pass

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print "Usage: python3 " + sys.argv[0] + " <bindhost> <bindport>"
        exit(1)

    host = sys.argv[1]
    port = int(sys.argv[2])

    server_socket = socket.socket()
    print("Connecting to " + host + ":" + sys.argv[2])
    sock.connect((host, port))
    print("Connection established")
