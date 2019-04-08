package com.dqv.smarthome.Mqtt;

import android.content.Context;
import android.util.Log;

import com.dqv.smarthome.Application.MyApplication;
import com.dqv.smarthome.Model.UrlModel;
import com.dqv.smarthome.Model.UserModel;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttHelper {

    public MqttAndroidClient mqttAndroidClient;

    final String serverUri = UrlModel.iruMQTT;

    final String subscriptionTopic = "DV1/#";
    private Context mContext;

    public MqttHelper(Context mContext) {
        UserModel currentUser = new UserModel();
        currentUser = MyApplication.getInstance().getPrefManager().getUser();
        String clientId = MqttClient.generateClientId();

        mqttAndroidClient = new MqttAndroidClient(mContext, serverUri, clientId);
        mqttAndroidClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
        connect(currentUser);
    }
    public void setCallback(MqttCallbackExtended callback) {
        mqttAndroidClient.setCallback(callback);
    }

    public void publish(String topic,String mss) {
        try {
            byte[] encodedPayload = new byte[0];
            encodedPayload = mss.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);

            mqttAndroidClient.publish(topic,message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect(){
         try{
             mqttAndroidClient.disconnect();
         }
         catch (Exception ex){

         }
    }
    private void connect(UserModel userModel){
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(userModel.getName());
        mqttConnectOptions.setPassword(userModel.getPasword().toCharArray());

        try {
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Failed to connect to: " + serverUri + exception.toString());
                }
            });


        } catch (MqttException ex){
            ex.printStackTrace();
        }

    }
    private void subscribeToTopic() {
        try {
            mqttAndroidClient.subscribe(subscriptionTopic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.w("Mqtt","Subscribed!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Subscribed fail!");
                }
            });

        } catch (MqttException ex) {
            System.err.println("Exceptionst subscribing");
            ex.printStackTrace();
        }
    }

}
