package com.dqv.mqtt;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetSocket;
import io.vertx.core.streams.ReadStream;
import io.vertx.core.streams.WriteStream;

/**
 * Created by Giovanni Baleani on 29/06/2015.
 */
public class SocketWrapper {

    private static Logger logger = LoggerFactory.getLogger(SocketWrapper.class);

    private WriteStream<Buffer> w;
    private ReadStream<Buffer> r;

    public SocketWrapper(WriteStream<Buffer> w, ReadStream<Buffer> r) {
        if(w==null)
            throw new IllegalArgumentException("SocketWrapper: write stream cannot be null");
        if(r==null)
            throw new IllegalArgumentException("SocketWrapper: read stream cannot be null");
        this.w = w;
        this.r = r;
    }

    public void sendMessageToClient(Buffer bytes) {
        try {
            w.write(bytes);
            if (w.writeQueueFull()) {
                r.pause();
                w.drainHandler( done -> r.resume() );
            }
        } catch(Throwable e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void stop() {
        // stop writing to socket
        try {
            w.drainHandler(null);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
    }
}
