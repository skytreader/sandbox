import random
import time
import uuid

from twisted.internet import reactor, protocol

class Conversation(protocol.Protocol):
    ACK = "ACK"
    
    def login(self):
        clientid = " " + str(uuid.uuid1()) if self.factory.give_client_id else ""
        
        return chr(2) + Conversation.ACK + clientid + chr(3)

    def name(self, clientid, name):
        return chr(2) + " ".join((Conversation.ACK, clientid, name)) + chr(3)

    def dateweather(self, clientid):
        iso8601 = time.strftime(time.localtime())
        return chr(2) + " ".join((Conversation.ACK, clientid, iso8601)) +chr(3)
    
    def data_parse(self, data):
        """
        Parse the data and return a map containing its components. Assumes that
        data is well-formed.

        The map returned has the fields "clientid" and "command" .
        """
        # Split upon removing stx and etx chars
        parse = data[1:len(data) - 1].split(" ")

        if len(parse) == 1:
            return {"clientid":None, "command":parse[0]}
        else:
            return {"clientid":parse[0], "command":parse[1]}

    def dataReceived(self, data):
        parsed = self.data_parse(data)
        command = parsed["command"]

        if command == "LOGIN":
            self.transport.write(self.login())
        elif command == "DATEWEATHER":
            self.transport.dateweather(parsed["clientid"])

class ConversationFactory(protocol.Factory):
    
    protocol = Conversation

    def __init__(self, give_client_id):
        self.give_client_id = give_client_id

if __name__ == "__main__":
    factory = ConversationFactory(True)
    reactor.listen(16981, factory)
    reactor.run()
