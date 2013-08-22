var http = require("http");
var url = require("url");

function handleReq(request, response){
    console.log("Request received.");
    response.writeHead(200, {"Content-Type": "text/plain"});
    
    // Parse the HTTP request parameters
    var url_parts = url.parse(request.url, true);
    var query = url_parts.query;

    response.write("Hello world is a stupid program and should never be used.\n");
    response.write("Hello " + query["name"]);

    response.end();
    console.log("Response sent.");
}

function start(){
    http.createServer(handleReq).listen(8888);
    console.log("Server started.");
}

exports.start = start
