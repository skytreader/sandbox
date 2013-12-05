import sys

from gevent import socket

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print "Usage: python " + sys.argv[0] + " <bindhost> <bindport>"
        exit(1)

    host = sys.argv[1]
    port = int(sys.argv[2])

    server_socket = 
