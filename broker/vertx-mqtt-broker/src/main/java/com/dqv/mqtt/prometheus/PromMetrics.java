package com.dqv.mqtt.prometheus;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;

/**
 * Created by giovanni on 07/07/17.
 */
public class PromMetrics {
    public static final Counter mqtt_publish_total =
            Counter.build().name("mqtt_publish_total").help("Published messages.")
                    .register();

    public static final Counter mqtt_publish =
            Counter.build().name("mqtt_publish").help("Published messages by topic or qos.")
                    .labelNames("qos","topic")
                    .register();

    public static final Counter mqtt_subscribe_total =
            Counter.build().name("mqtt_subscribe_total").help("Total mqtt subscribe messages.")
                    .register();

    public static final Counter mqtt_subscribe =
            Counter.build().name("mqtt_subscribe").help("Total mqtt subscribe messages.")
                    .labelNames("qos", "topic_filter")
                    .register();


    public static final Counter mqtt_unsubscribe_total =
            Counter.build().name("mqtt_unsubscribe_total").help("Total mqtt unsubscribe messages.")
                    .register();

    public static final Counter mqtt_unsubscribe =
            Counter.build().name("mqtt_unsubscribe").help("Total mqtt unsubscribe messages.")
                    .labelNames("topic_filter")
                    .register();


    public static final Counter mqtt_connect_total =
            Counter.build().name("mqtt_connect_total").help("Total mqtt connect messages.")
                    .register();

    public static final Counter mqtt_disconnect_total =
            Counter.build().name("mqtt_disconnect_total").help("Total mqtt disconnect messages.")
                    .register();

    public static final Counter mqtt_sessions_total =
            Counter.build().name("mqtt_sessions_total").help("Total mqtt sessions object created.")
                    .register();

    public static final Gauge mqtt_sessions =
            Gauge.build().name("mqtt_sessions").help("Active mqtt sessions.")
                    .register();
}
