/**
 * Created by liebl on 21.05.2017.
 */

define(['stomp', 'sockjs'], function (Stomp, SockJS) {

    var stompClient = null;
    var connected = false;

    function _connect(headers, socketName, subscribe) {
        var socket = new SockJS(socketName);
        stompClient = Stomp.over(socket);
        // stompClient.debug = function () {};
        stompClient.connect(headers, function (frame) {
            connected = true;
            subscribe(stompClient);
        });
    }

    function _disconnect() {
        if (stompClient !== null) {
            stompClient.disconnect();
            stompClient = null;
        }
        connected = false;
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