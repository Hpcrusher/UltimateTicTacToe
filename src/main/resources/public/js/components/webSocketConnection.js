/**
 * Created by liebl on 21.05.2017.
 */

define(['stomp', 'sockjs'], function (Stomp, SockJS) {

    var stompClient = null;
    var connected = false;

    function _connect(socketName, subscribeTo, callback, finalExecution) {
        var socket = new SockJS(socketName);
        stompClient = Stomp.over(socket);
        stompClient.debug = function () {};
        stompClient.connect({}, function (frame) {
            connected = true;
            console.log('Connected: ' + frame);
            stompClient.subscribe(subscribeTo, function (greeting) {
                callback(JSON.parse(greeting.body).content);
            });
            finalExecution();
        });
    }

    function _disconnect() {
        if (stompClient !== null) {
            stompClient.disconnect();
            stompClient = null;
        }
        connected = false;
        console.log("Disconnected");
    }

    function _sendMessage(destination, data) {
        stompClient.send(destination, {}, JSON.stringify(data));
    }

    return {
        connect: _connect,
        disconnect: _disconnect,
        sendMessage: _sendMessage
    };

});