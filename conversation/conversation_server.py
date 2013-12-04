import random
import uuid

from twisted.internet import reactor, protocol

class Conversation(protocol.Protocol):
    ACK = "ACK"
    
    def login(self):
        clientid = " " + str(uuid.uuid1()) if self.factory.give_client_id else ""
        
        return chr(2) + Conversation.ACK + clientid + chr(3)

    def name(self, clientid, name):
        return chr(2) + " ".join(("ACK", clientid, name)) + chr(3)

    def dateweather(self, clientid):
        pass
    
    def get_command(self, data):
        parse = data.split(" ")

        if len(parse) == 1:
            return data
        else:
            return parse[1]

    def dataReceived(self, data):
        command = get_command(data)

        if command == "LOGIN":
            self.transport.write(self.login())
