from twisted.internet import reactor, protocol

"""
Straight outta http://moshez.files.wordpress.com/2011/03/is-easy.pdf
"""

class Echo(protocol.Protocol):
    def dataReceived(self, data):
        print "RECV: " + str(data)
        self.transport.write(data)

if __name__ == "__main__":
    factory = protocol.Factory()
    factory.protocol = Echo
    reactor.listenTCP(16981, factory)

    reactor.run()
