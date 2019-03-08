package io.github.giovibal.mqtt.integration;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.dqv.mqtt.*;

import io.github.giovibal.mqtt.test.Tester;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public class AuthorizationTests {
	private static final String AUTHENTICATOR_ADR = "mqtt_authenticator";
	private Vertx vertx;
	private NetServer netServer;
	private JsonObject config;
	private UserAuthVerticleStub auth;
	
	@Before
	public void setUp(TestContext context) {
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.INFO);
		
		vertx = Vertx.vertx();
		config = new JsonObject();
		config.put("tcp_port", 1883);
		config.put("socket_idle_timeout", 60);
		config.put("retain_support", false);
		config.put("authenticator_address", AUTHENTICATOR_ADR);
		netServer = startTcpBroker(config);
		
		auth = new UserAuthVerticleStub(AUTHENTICATOR_ADR);
		vertx.deployVerticle(auth, context.asyncAssertSuccess());
	}

	@After
	public void tearDown(TestContext context) {
		netServer.close();
		vertx.close(context.asyncAssertSuccess());
	}

    @Test
    public void testBackwardCompatibility(TestContext context) {
    	try {
	        String serverURL = "tcp://localhost:"+config.getInteger("tcp_port");
	        Tester c = new Tester(1, "Paho", serverURL);

	        System.out.println("Check if can connect without password checks");
	        auth.setLoginRequired(false);
	        auth.setDoPublishChecks(false);
	        auth.setDoSubscribeChecks(false);
	        try {
	        	c.connect();
	        } finally {
	        	c.disconnect();
	        }
	        
	        System.out.println("Check if connect fails without valid password");
	        auth.setLoginRequired(true);
	        try {
	        	c.connect();
		        context.fail("Credentials not checked");
	        } catch(Throwable e) {
	        	// Expected exception
	        } finally {	   
				try {c.disconnect();} catch (Exception ex){}	        	
	        }

	        System.out.println("Check if connect succeeds with valid password");
	        MqttConnectOptions o = new MqttConnectOptions();
	        o.setUserName("user1");
	        o.setPassword("secret".toCharArray());
	        auth.setUserPass("user1", "secret");
	        c.connect(o);
	        
	        try {
	        	c.publish("my/special/topic");			// Any publish succeeds
	        	c.subscribe("my/other/topic");			// Any subscribe succeeds
	        } finally{
	        	c.disconnect();
	        }
	        
    	} catch(Throwable e) {
    		System.out.println("Exception:"+e.getMessage());
    		context.fail(e.getMessage());
        }
    }

    @Test
    public void testSubscribeAuthentication(TestContext context) throws MqttException {
    	Tester c = null;
    	try {
	        String serverURL = "tcp://localhost:"+config.getInteger("tcp_port");
	        c = new Tester(1, "Paho", serverURL);

	        auth.setLoginRequired(true);
	        auth.setDoSubscribeChecks(true);
	        auth.setDoPublishChecks(false);

	        MqttConnectOptions o = new MqttConnectOptions();
	        o.setUserName("user1");
	        o.setPassword("secret".toCharArray());
	        auth.setUserPass("user1", "secret");
	        c.connect(o);
	        
	        System.out.println("Check that subscribe to allowed topic works");
	        String topic = "valid/topic";
	        auth.setAllowedSubTopic(topic);
	        c.subscribe(topic);
	        int count = (int) c.getMessaggiArrivatiPerClient().values().toArray()[0];
	        c.publish(topic);
	        count = (int) c.getMessaggiArrivatiPerClient().values().toArray()[0] - count;
	        c.unsubcribe(topic);	        
	        context.assertNotEquals(count, 0, "Publish to valid topic not received");
	        
	        System.out.println("Check that subscribe to illegal topic throws exception and does not accept messages");
	        String illegalTopic = "illegal/topic";
	        try {
	        	c.subscribe(illegalTopic);
	        	context.fail("Illegal subscribe topic accepted");
	        } catch (Throwable e) {
	        	// Expected exception
	        }
	        count = (int) c.getMessaggiArrivatiPerClient().values().toArray()[0];
	        c.publish(illegalTopic);
	        count = (int) c.getMessaggiArrivatiPerClient().values().toArray()[0] - count;
	        c.unsubcribe(illegalTopic);	        
	        context.assertEquals(count, 0, "Publish to illegal topic succeeded");
	        
    	} catch (Throwable e) {
    		context.fail(e.getMessage());
    	} finally {
			try {c.disconnect();} catch (Exception ex){}	        	
    	}
    }
    
    @Test
    public void testPublishAuthentication(TestContext context) throws MqttException {
    	Tester c = null;
    	try {
	        String serverURL = "tcp://localhost:"+config.getInteger("tcp_port");
	        c = new Tester(1, "Paho", serverURL);

	        auth.setLoginRequired(true);
	        auth.setDoSubscribeChecks(false);
	        auth.setDoPublishChecks(true);

	        MqttConnectOptions o = new MqttConnectOptions();
	        o.setUserName("user1");
	        o.setPassword("secret".toCharArray());
	        auth.setUserPass("user1", "secret");
	        c.connect(o);
	        
	        System.out.println("Check that publish to allowed topic works");
	        String topic = "valid/topic";
	        auth.setAllowedPubTopic(topic);
	        c.subscribe(topic);
	        int count = (int) c.getMessaggiArrivatiPerClient().values().toArray()[0];
	        c.publish(topic);
	        count = (int) c.getMessaggiArrivatiPerClient().values().toArray()[0] - count;
	        c.unsubcribe(topic);	        
	        context.assertNotEquals(count, 0, "Publish to valid topic not received");
	        
	        System.out.println("Check that publish to illegal topic does not send messages");
	        String illegalTopic = "illegal/topic";
	        c.subscribe(illegalTopic);
	        count = (int) c.getMessaggiArrivatiPerClient().values().toArray()[0];
        	c.publish(illegalTopic);			// Illegal publish fails silently
	        count = (int) c.getMessaggiArrivatiPerClient().values().toArray()[0] - count;
	        c.unsubcribe(illegalTopic);	        
	        context.assertEquals(count, 0, "Publish to illegal topic sent message");
	        
    	} catch (Throwable e) {
    		context.fail(e.getMessage());
    	} finally {
			try {c.disconnect();} catch (Exception ex){}	        	
    	}
    }
    
    private NetServer startTcpBroker(JsonObject conf) {

		ConfigParser c = new ConfigParser(conf);

		NetServerOptions opt = new NetServerOptions().setTcpKeepAlive(true)
				.setIdleTimeout(conf.getInteger("socket_idle_timeout")).setPort(conf.getInteger("tcp_port"));

		NetServer netServer = vertx.createNetServer(opt);
		netServer.connectHandler(netSocket -> {
			Map<String, MQTTSession> sessions = new HashMap<>();
			MQTTNetSocket mqttNetSocket = new MQTTNetSocket(vertx, c, netSocket, sessions);
			mqttNetSocket.start();
		}).listen();
		return netServer;
	}
}
