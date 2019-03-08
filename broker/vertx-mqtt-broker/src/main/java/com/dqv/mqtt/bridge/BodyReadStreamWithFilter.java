package com.dqv.mqtt.bridge;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.impl.BodyReadStream;
import io.vertx.core.streams.ReadStream;

import java.util.function.Function;

public class BodyReadStreamWithFilter<T> extends BodyReadStream<T> {

    private ReadStream<Message<T>> _delegate;
    private Function<Message<T>, Boolean> filter;

    public BodyReadStreamWithFilter(ReadStream<Message<T>> delegate,
                                    Function<Message<T>, Boolean> filter) {
        super(delegate);
        this._delegate = delegate;
        this.filter = filter;
    }

    @Override
    public ReadStream<T> handler(Handler<T> handler) {
        if (handler != null) {
            _delegate.handler(message -> {
                boolean filterPass = filter.apply(message);
                if (filterPass) {
                    handler.handle(message.body());
                }
            });
        } else {
            _delegate.handler(null);
        }
        return this;
    }


}
