package com.dqv.mqtt;

import io.vertx.core.http.WebSocketBase;

/**
 * Created by Giovanni Baleani on 29/06/2015.
 */
public class WebSocketWrapper extends SocketWrapper {

    public WebSocketWrapper(WebSocketBase netSocket) {
        super(netSocket, netSocket);
    }
}
