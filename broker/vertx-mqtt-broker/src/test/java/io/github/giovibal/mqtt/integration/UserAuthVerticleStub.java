package io.github.giovibal.mqtt.integration;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;

public class UserAuthVerticleStub extends AbstractVerticle {
	private static final String TOKEN = "ABCDEF";
	private String evtBusAddress;
	private boolean loginRequired = true;
	private boolean doSubscribeChecks = false;
	private boolean doPublishChecks = false;
	private String allowedPubTopic = null;
	private boolean pubTopicMustMatch = true;
	private String allowedSubTopic = null;
	private boolean subTopicMustMatch = true;
	private String user = "";
	private String password = "";
	@SuppressWarnings("rawtypes")
	private MessageConsumer connectConsumer, pubConsumer, subConsumer;
	
	public UserAuthVerticleStub(String evtBusAddress) {
		super();
		this.evtBusAddress = evtBusAddress;
	}

	public void setLoginRequired(boolean login) {
		this.loginRequired = login;
	}
	public void setDoPublishChecks(boolean check) {
		this.doPublishChecks = check;
	}
	public void setDoSubscribeChecks(boolean check) {
		this.doSubscribeChecks = check;
	}
	public void setAllowedPubTopic(String topic) {
		this.allowedPubTopic = topic;
	}
	public void setPubTopicMustMatch(boolean match) {
		this.pubTopicMustMatch = match;
	}
	public void setAllowedSubTopic(String topic) {
		this.allowedSubTopic = topic;
	}
	public void setSubTopicMustMatch(boolean match) {
		this.subTopicMustMatch = match;
	}
	public void setUserPass(String user, String password) {
		this.user = user;
		this.password = password;
	}
	@Override
	public void start() throws Exception {
		EventBus eb = vertx.eventBus();

		/**
		 * Connect
		 */
		connectConsumer = eb.consumer(evtBusAddress, message -> {
			JsonObject credentials = new JsonObject(message.body().toString());
			JsonObject json = new JsonObject();
			if (loginRequired) {
				json.put("auth_valid", user.equals(credentials.getString("username")) 
										&& password.equals(credentials.getString("password")));
			} else {
				json.put("auth_valid", true);
			}
            json.put("authorized_user", credentials.getString("username"));
            json.put("error_msg", "");
            if (doPublishChecks || doSubscribeChecks) {
            	json.put("token", TOKEN);
            }
			message.reply(json);
		});
		
		/**
		 * Check if allowed to publish
		 */
		pubConsumer = eb.consumer(evtBusAddress+".publish", message -> {
			boolean match = true;
			if (doPublishChecks) {
				JsonObject publish = new JsonObject(message.body().toString());
				String topic = publish.getString("topic");
				if (topic == null) {
					match = false;
				} else {
					if (!topic.equals(allowedPubTopic)) {
						match = false;
					}
				}
				if (!pubTopicMustMatch) {
					match = !match;
				}
			}
			JsonObject reply = new JsonObject();
			reply.put("permitted", match);
			message.reply(reply);
		});
		
		/**
		 * Check if allowed to subscribe
		 */
		subConsumer = eb.consumer(evtBusAddress+".subscribe", message -> {
			JsonObject subscribe = new JsonObject(message.body().toString());
			JsonArray topics = subscribe.getJsonArray("topics");
			JsonArray result = new JsonArray();
			for (int i=0; i < topics.size(); i++) {
				if (doSubscribeChecks) {
					result.add(topics.getString(i).equals(allowedSubTopic));
				} else {
					result.add(true);
				}
			}
			JsonObject reply = new JsonObject();
			reply.put("permitted", result);
			message.reply(reply);
		});
	}
	@Override
	public void stop() throws Exception {
		connectConsumer.unregister();
		pubConsumer.unregister();
		subConsumer.unregister();
	}
}
