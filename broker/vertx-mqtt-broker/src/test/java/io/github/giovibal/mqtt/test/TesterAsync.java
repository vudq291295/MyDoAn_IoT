package io.github.giovibal.mqtt.test;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.*;

/**
 * Created by giovanni on 08/04/2014.
 */
public class TesterAsync {
    static final String serverURL = "tcp://localhost:1883";
    static final String serverURLSubscribers = serverURL;
    static final String serverURLPublishers = serverURL;


    static boolean logEnabled=false;

    public static void main(String[] args) throws Exception {

//        stats("Qos Tests");
        test2(10, 30000, 0, 0);
//        test2(30, 100, 1, 0);
//        test2(30, 100, 2, 0);

        System.exit(0);
    }

    private static void log(String msg) {
        if(logEnabled) {
            System.out.println(msg);
        }
    }
    private static void stats(String msg) {
        System.out.println(msg);
    }


    private static String generateRandomTopic(String pattern) {
        String rnd = UUID.randomUUID().toString();
        String topic = pattern;
        while(topic.contains("RND"))
            topic = topic.replaceFirst("RND", rnd);
        return topic;
    }

    public static void test2(int numClients, int numMessagesToPublishPerClient, int qos, long sleepMilliSeconds) throws Exception {
        stats("");
        stats("------------------------------------------------------------------");
        stats("Test clients: "+ numClients +", num msg: "+ numMessagesToPublishPerClient +", qos: "+ qos +" sleep: "+sleepMilliSeconds +" millis.");
        stats("------------------------------------------------------------------");
//        String topic = "test/untopic/a";
        String topic = generateRandomTopic("test/RND/RND/a");
        String topicFilter = "test/+/+/a";

        long t1,t2,t3;
        t1=System.currentTimeMillis();

        TesterAsync cSubs = new TesterAsync(numClients, "SUBS", serverURLSubscribers);
        cSubs.connect();
        cSubs.subscribe(topicFilter);

        TesterAsync cPubs = new TesterAsync(numClients, "PUBS", serverURLPublishers);
        cPubs.connect();

        boolean retain = true;
        cPubs.publish(numMessagesToPublishPerClient, topic, qos, retain);
        cPubs.disconnect();

        if(sleepMilliSeconds>0) {
            log("Sleep for " + sleepMilliSeconds + " millis ...");
            Thread.sleep(sleepMilliSeconds);
        }

        cSubs.unsubcribe(topic);
        cSubs.disconnect();

        cPubs.publishStats();
        cSubs.subscribeStats();

        t2=System.currentTimeMillis();
        t3=t2-t1;


        stats("Time elapsed: " + t3 + " millis.");
        stats("Messages sent: " + cPubs.getMessaggiSpeditiInMedia() + " messages.");
        stats("Messages received: " + cSubs.getMessaggiArrivatiInMedia() + " messages.");
        float seconds = (t3/1000);
        if(seconds > 0) {
            float throughputPub = cPubs.getMessaggiSpeditiInMedia() / (seconds);
            float throughputSub = cSubs.getMessaggiArrivatiInMedia() / (seconds);
            float throughput = throughputPub + throughputSub;
            stats("Throughput Published: " + throughputPub + " msg/sec.");
            stats("Throughput Received: " + throughputSub + " msg/sec.");
            stats("Throughput Total: " + throughput + " msg/sec.");
        }
        stats("------------------------------------------------------------------");
    }



    private List<IMqttAsyncClient> clients = new ArrayList<>();
    private List<MQTTClientHandler> clientHandlers = new ArrayList<>();

    public TesterAsync(int numClients, String clientIDPrefix, String serverURL) throws MqttException {
        for(int i=1; i<=numClients; i++) {
            String clientID = clientIDPrefix+"_" + i;

            MqttAsyncClient client = new MqttAsyncClient(serverURL, clientID, new MemoryPersistence());
            MQTTClientHandler h = new MQTTClientHandler(clientID);
            client.setCallback(h);

            clients.add(client);
            clientHandlers.add(h);
        }
    }
    public void connect() throws MqttException {
        log("connect ...");
        for(IMqttAsyncClient client : clients) {
            MqttConnectOptions o = new MqttConnectOptions();
//            if(this.serverURL.startsWith("ssl")) {
//                try {
//                        SSLSocketFactory sslSocketFactory = SslUtil.getSocketFactory(
//                                "C:\\Sviluppo\\Certificati-SSL\\CA\\rootCA.pem",
////                                "C:\\Sviluppo\\Certificati-SSL\\device1\\device1_CA1.crt",
//                                "C:\\Sviluppo\\Certificati-SSL\\device1\\device1.crt",
//                                "C:\\Sviluppo\\Certificati-SSL\\device1\\device1.key",
//                                "");
//                        o.setSocketFactory(sslSocketFactory);
//                } catch(Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            o.setCleanSession(true);
//            try {
//                o.setWill("$SYS/config", new String("{\"retain\":false}").getBytes("UTF-8"), 0, false);
//            } catch (Throwable e) { e.printStackTrace(); }
            client.connect(o).waitForCompletion();
        }
    }

    public void disconnect() throws MqttException {
        log("disconnet ...");
        for(IMqttAsyncClient client : clients) {
            client.disconnect();
        }
    }

    public void subscribe(String topic) throws MqttException {
        log("subscribe topic: " + topic + " ...");
        for (IMqttAsyncClient client : clients) {
            client.subscribe(topic, 2);
        }
    }
    public void unsubcribe(String topic) throws MqttException {
        log("unsubscribe topic: " + topic + " ...");
        for (IMqttAsyncClient client : clients) {
            client.unsubscribe(topic);
        }
    }

    public void publish(String topic) throws Exception {
        log("publih ...");
        MqttMessage m;
        for(IMqttAsyncClient client : clients) {

            m = new MqttMessage();
            m.setQos(2);
            m.setRetained(true);
            m.setPayload("prova qos=2 retained=true".getBytes("UTF-8"));
            log(client.getClientId() + " publish >> sending qos=2 retained=true");
            client.publish(topic, m);

            m = new MqttMessage();
            m.setQos(1);
            m.setRetained(true);
            m.setPayload("prova qos=1 retained=true".getBytes("UTF-8"));
            log(client.getClientId() + " publish >> sending qos=1 retained=true");
            client.publish(topic, m);

            m = new MqttMessage();
            m.setQos(0);
            m.setRetained(true);
            m.setPayload("prova qos=0 retained=true".getBytes("UTF-8"));
            log(client.getClientId() + " publish >> sending qos=0 retained=true");
            client.publish(topic, m);

            m = new MqttMessage();
            m.setQos(2);
            m.setRetained(false);
            m.setPayload("prova qos=2 retained=false".getBytes("UTF-8"));
            log(client.getClientId() + " publish >> sending qos=2 retained=false");
            client.publish(topic, m);

            m = new MqttMessage();
            m.setQos(1);
            m.setRetained(false);
            m.setPayload("prova qos=1 retained=false".getBytes("UTF-8"));
            log(client.getClientId() + " publish >> sending qos=1 retained=false");
            client.publish(topic, m);

            m = new MqttMessage();
            m.setQos(0);
            m.setRetained(false);
            m.setPayload("prova qos=0 retained=false".getBytes("UTF-8"));
            log(client.getClientId() + " publish >> sending qos=0 retained=false");
            client.publish(topic, m);
        }
    }
    public void publish(int numMessages, String topic, int qos, boolean retained) throws Exception {
        log("publih ...");
        MqttMessage m;
        for(IMqttAsyncClient client : clients) {
            for(int i=0; i<numMessages; i++) {
                String msg = "msg "+i+" qos="+qos+" retained="+ retained;
                m = new MqttMessage();
                m.setQos(qos);
                m.setRetained(retained);
                m.setPayload(msg.getBytes("UTF-8"));
                log(client.getClientId() + " publish >> sending qos=" + qos + " retained=" + retained);
                client.publish(topic, m);
            }
        }
    }

    public void stats() {
        log("-------------------------------S-T-A-T-S-------------------------------------------");
        for(MQTTClientHandler h : clientHandlers) {
            log("Client: " + h.clientID + " messaggi arrivati: " + h.messaggiArrivati + " messaggi spediti: " + h.messaggiSpediti);
        }
        log("-------------------------------S-T-A-T-S-------------------------------------------");
    }
    public void publishStats() {
        log("-------------------------------S-T-A-T-S-------------------------------------------");
        for(MQTTClientHandler h : clientHandlers) {
            log("Client: " + h.clientID + " messaggi spediti: " + h.messaggiSpediti);
        }
        log("-------------------------------S-T-A-T-S-------------------------------------------");
    }
    public void subscribeStats() {
        log("-------------------------------S-T-A-T-S-------------------------------------------");
        for(MQTTClientHandler h : clientHandlers) {
            log("Client: " + h.clientID + " messaggi arrivati: " + h.messaggiArrivati);
        }
        log("-------------------------------S-T-A-T-S-------------------------------------------");
    }
    public Map<String, Integer> getMessaggiArrivatiPerClient() {
        Map<String, Integer> ret = new HashMap<>();
        for(MQTTClientHandler h : clientHandlers) {
            ret.put(h.clientID,h.messaggiArrivati);
        }
        return ret;
    }
    public int getMessaggiArrivatiInMedia() {
        int sum=0;
        for(MQTTClientHandler h : clientHandlers) {
            sum += h.messaggiArrivati;
        }
        return sum;
    }
    public int getMessaggiSpeditiInMedia() {
        int sum=0;
        for(MQTTClientHandler h : clientHandlers) {
            sum += h.messaggiSpediti;
        }
        return sum;
    }


    static class MQTTClientHandler implements MqttCallback {

        String clientID;
        int messaggiArrivati;
        int messaggiSpediti;

        MQTTClientHandler(String clientID) {
            this.clientID = clientID;
            this.messaggiArrivati = 0;
            this.messaggiSpediti = 0;
        }

        @Override
        public void connectionLost(Throwable throwable) {
            log(clientID + " connectionLost " + throwable.getMessage());
        }

        @Override
        public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
            messaggiArrivati++;
            log(clientID + " messageArrived <== " + topic + " real qos: " + mqttMessage.getQos() + " ==> " + new String(mqttMessage.getPayload(), "UTF-8") + " " + messaggiArrivati);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            messaggiSpediti++;
            log(clientID + " deliveryComplete ==> " + iMqttDeliveryToken.getMessageId() + " " + iMqttDeliveryToken.getClient().getClientId());
        }

    }


}
