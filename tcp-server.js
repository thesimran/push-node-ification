var net = require('net');

var server  = net.createServer(function(socket) {
	
	console.log("Client connected!");
	socket.write('hello\n');
	socket.write('world\n');
});

server.listen(8000);

console.log('TCP Server opened socket on port 8000');	
