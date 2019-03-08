package com.dqv.mqtt.rest;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.AuthHandler;
import io.vertx.ext.web.handler.BasicAuthHandler;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

import org.dna.mqtt.moquette.proto.messages.AbstractMessage;
import org.dna.mqtt.moquette.proto.messages.PublishMessage;

import com.dqv.mqtt.MQTTSession;
import com.dqv.mqtt.parser.MQTTEncoder;

import java.nio.ByteBuffer;

/**
 * Created by giovanni on 05/01/17.
 */
public class RestApiVerticle extends AbstractVerticle {

    private Logger logger = LoggerFactory.getLogger(RestApiVerticle.class);

    @Override
    public void start() throws Exception {
        JsonObject restServerConf = config().getJsonObject("rest_server", new JsonObject());
        int httpPort = restServerConf.getInteger("port", 2883);

        String mqttAddress = MQTTSession.ADDRESS;

        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        
        long size1mb = 1024*1024 ; //bytes
        
        AuthProvider authProvider = new AuthProvider() {
			
			@Override
			public void authenticate(JsonObject authInfo, Handler<AsyncResult<User>> resultHandler) {
				// TODO Auto-generated method stub
				
			}
		};
		JsonObject authInfo = new JsonObject().put("username", "tim").put("password", "mypassword");

		authProvider.authenticate(authInfo, res -> {
			  if (res.succeeded()) {
				    User user = res.result();
//				    System.out.println("User " + user.principal() + " is now authenticated");
				    logger.info("User " + user.principal() + " is now authenticated");

				  } else {
				    res.cause().printStackTrace();
				    logger.info( res.cause().toString());
				  }

		});
        router.route().handler(BodyHandler.create().setBodyLimit(size1mb));
        router.route().handler(CookieHandler.create());
        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
        
        AuthHandler basicAuthHandler = BasicAuthHandler.create(authProvider);

        //router.route("/test").handler(basicAuthHandler);

        // http://<host:port>/pubsub/publish?channel=&lt;channel1&gt;&qos=0&retained=0
        // qos: MOST_ONE, LEAST_ONE, EXACTLY_ONC
        router.post("/pubsub/publish").handler( req -> {
        	logger.info(req); 
            MultiMap headers = req.request().headers();
            MultiMap params = req.request().params();
            String tenant;
            if(headers.contains("tenant")) {
                tenant = headers.get("tenant");
            } else {
                tenant = params.get("tenant");
            }
            String topic;
            if(params.contains("topic")) {
                topic = req.request().params().get("topic");
            } else if (params.contains("channel")) {
                topic = req.request().params().get("channel");
            } else {
                throw new IllegalArgumentException("parameter 'topic' is required");
            }

            String qos = req.request().params().get("qos");
            String retained = req.request().params().get("retained");

            PublishMessage msg = new PublishMessage();
            msg.setMessageID(1);
            msg.setTopicName(topic);
            if ( qos != null) {
                AbstractMessage.QOSType theqos =
                        AbstractMessage.QOSType.valueOf(qos);
                msg.setQos(theqos);
            }
            if (retained != null)
                msg.setRetainFlag(true);

            try {
                Buffer body = req.getBody();
                byte[] payload = body.getBytes();
                msg.setPayload(ByteBuffer.wrap(payload));
                MQTTEncoder enc = new MQTTEncoder();
                DeliveryOptions opt = new DeliveryOptions()
                        .addHeader(MQTTSession.TENANT_HEADER, tenant);
                vertx.eventBus().publish(mqttAddress, enc.enc(msg), opt);
                req.response().setStatusCode(200);
            } catch (Throwable e) {
                logger.info(e.getMessage(), e);
                req.response().setStatusCode(500);
                req.response().setStatusMessage(e.getMessage());
            }
            req.response().end();
        });

        router.route("/test").handler(req ->{
            req.response().setStatusCode(200)
            .setStatusMessage("abc")
            .putHeader("content-type", "application/json; charset=utf-8").
            end();
        });
        
        router.exceptionHandler(event -> {
            logger.info(event.getMessage(), event.getCause());
        });


        server.requestHandler(router::accept).listen(httpPort, event -> {
            if (event.succeeded()) {
                logger.info("RestApiVerticle http server started on http://<host>:" + server.actualPort());
            } else {
                logger.info("RestApiVerticle http server NOT started !");
            }
        });
        logger.info("RestApiVerticle started" );
    }
}
