/**
A simple transport layer queue written in node.js .

Supported commands (case insensitive):
    - Queue pop ("POP").
    - Queue push ("PUSH x").
    - Get queue length ("LEN").

@author Chad Estioco
*/

var net = require("net");

var HOST = "127.0.0.1";
var PORT = 16981;
var SUPPORTED_COMMANDS = ["POP", "PUSH", "LEN"]

function NodeMessageQueue(){
    this.queue = new Array();
    this.COMMAND_MAP = new Object();

    this.COMMAND_MAP["POP"] = function(item){
        return this.queue.pop();
    }
    
    this.COMMAND_MAP["PUSH"] = function(item){
        return this.queue.push(item);
    }
    
    this.COMMAND_MAP["LEN"] = function(item){
        return this.queue.length;
    }
}

NodeMessageQueue.prototype = {
    interpret: function(command){
        /**
        Test cases:
            - undefined function
            - multiple spaces
            - spaces in arguments
        */
        command_split = command.split(" ");
        arg = (command_split.length == 1) ? "" : command_split[1];
        return this.COMMAND_MAP[command_split[0].toUpperCase()](arg);
    }
}

function listener(socket){
    console.log("Connected at " + socket.remoteAddress + ":" +
      socket.remotePort);

    var nodemq = new NodeMessageQueue();
}

var nodemq = new NodeMessageQueue();
nodemq.interpret("PUSH Hello");
nodemq.interpret("LEN");
nodemq.interpret("POP");
