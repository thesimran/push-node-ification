var net = require('net');

var sockets = [];
var client = new Object(); // {"address" : ipAddress}

var server  = net.createServer(function(socket) {
	sockets.push(socket)
	
	socket.on('data', function(d) {
		for(var i=0; i < sockets.length; i++) {
			//TODO instantiate new cleint object and put into sockets array
			sockets[i].write(d);
		}
	});
	
	socket.on('end', function() {
		var index = sockets.indexOf(socket);
		delete sockets[index];
	});
});

server.listen(8000);

console.log('TCP Server started on port 8000');	
