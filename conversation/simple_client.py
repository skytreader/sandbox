#! /usr/bin/env python3

import socket
import sys

"""
A simple (quick hack) client for the conversation protocol. This totally assumes
the happy path.
"""

CLIENT_COMMANDS = ("LOGIN", "TCE", "DATEWEATHER", "LOGOUT")

class MalformedGrammarException(Exception):
    
    def __init__(self, value):
        self.value = value

    def __str__(self):
        return "Unexpected character in grammar: " + str(self.value)

class HackBuffer(object):
    """
    Totes inefficient.
    """
    
    def __init__(self, socksource):
        self.socksource = socksource

    def get_packet(self):
        recv = self.socksource.recv(1).decode()
        byte_buffer = []

        if recv != chr(2):
            raise MalformedGrammarException(recv)

        while recv != chr(3):
            byte_buffer.append(recv)
            recv = self.socksource.recv(1).decode()
        
        byte_buffer.append(recv)

        return "".join(byte_buffer)

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python3 " + sys.argv[0] + " <bindhost> <bindport>")
        exit(1)

    host = sys.argv[1]
    port = int(sys.argv[2])

    server_socket = socket.socket()
    print("Connecting to " + host + ":" + sys.argv[2])
    server_socket.connect((host, port))
    print("Connection established")

    reply_buffer = HackBuffer(server_socket)
    clientid = None

    for command in CLIENT_COMMANDS:
        for_sending = None
        if clientid:
            for_sending = chr(2) + " ".join([clientid, command] + chr(3))
        else:
            for_sending = chr(2) + command + chr(3)
        
        print("SEND: " + for_sending)
        server_socket.send(for_sending.encode())

        response = reply_buffer.get_packet()
        
        print("RECV: " + response)

        if command == "DATEWEATHER":
            # Isa pang get_packet()
            response = reply_buffer.get_packet()

            print("RECV: " + response)
    
    server_socket.close()
