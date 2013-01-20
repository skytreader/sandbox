#! /usr/bin/env python3

import socketserver
import sys
import time

class TimeServer(socketserver.BaseRequestHandler):
    """
    Implements a server that politely tells clients it's
    current time past epoch. Can greet you properly as
    long as your name is <= 1024 bytes.
    """

    def handle(self):
        self.data = self.request.recv(1024).decode()
        print("Got a request from " + self.data)
        self.request.sendall(("Hi " + self.data + " my current time is " + str(time.time()) + " seconds since epoch.").encode())

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python3 timeserver.py [host] [port]")
        exit()
    
    print("Waiting for connections...")
    host = sys.argv[1]
    port = int(sys.argv[2])

    server = socketserver.TCPServer((host, port), TimeServer)
    server.serve_forever()
