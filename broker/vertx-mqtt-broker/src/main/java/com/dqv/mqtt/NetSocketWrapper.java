package com.dqv.mqtt;

import io.vertx.core.net.NetSocket;

/**
 * Created by Giovanni Baleani on 29/06/2015.
 */
public class NetSocketWrapper extends SocketWrapper {

    public NetSocketWrapper(NetSocket netSocket) {
        super(netSocket,netSocket);
    }

}
