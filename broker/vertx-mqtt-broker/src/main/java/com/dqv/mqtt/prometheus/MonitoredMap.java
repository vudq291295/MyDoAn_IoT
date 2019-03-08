package com.dqv.mqtt.prometheus;

import java.util.HashMap;
import java.util.Map;

public class MonitoredMap<K,V> extends HashMap<K,V> {
    @Override
    public V put(K k, V v) {
        V ret = super.put(k, v);
        PromMetrics.mqtt_sessions.set(this.size());
        return ret;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        super.putAll(map);
        PromMetrics.mqtt_sessions.set(this.size());
    }

    @Override
    public V remove(Object k) {
        V ret = super.remove(k);
        PromMetrics.mqtt_sessions.set(this.size());
        return ret;
    }
}
