package com.dqv.servicechecktime.thread;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import com.dqv.servicechecktime.jdbc.ConnectDatabase;
import com.dqv.servicechecktime.jdbc.ScheduleJDBC;

import Model.EquipmentModel;

public class ServiceCheckTime implements Runnable{
	Thread t;
	Connection conn;
	String publisherId = UUID.randomUUID().toString();
	MqttClient publisher; 
	Date in2 = new Date();
	LocalDateTime ldt2 = LocalDateTime.ofInstant(in2.toInstant(), ZoneId.systemDefault());
	Date out2 = Date.from(ldt2.atZone(ZoneId.systemDefault()).toInstant());
	int currentMinus = out2.getMinutes();

	public ServiceCheckTime() {
	    t = new Thread(this, "mqtt");
	    System.out.println("New thread: " + t);
	    t.start();
		conn = ConnectDatabase.getConnectDatabase();
		try {
			publisher = new MqttClient("tcp://14.160.26.174:1884",publisherId);
			MqttConnectOptions options = new MqttConnectOptions();
			options.setUserName("servicechecktime");
			options.setPassword("1".toCharArray());
			options.setAutomaticReconnect(true);
			options.setCleanSession(true);
			options.setConnectionTimeout(10);
			publisher.connect(options);
			publisher.subscribe("#");
			publisher.setCallback(new MqttCallback() {
				
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					// TODO Auto-generated method stub
					
				}
				
				public void deliveryComplete(IMqttDeliveryToken token) {
					// TODO Auto-generated method stub
					
				}
				
				public void connectionLost(Throwable cause) {
					// TODO Auto-generated method stub
					
				}
			});
		} catch (MqttException e) {
			System.out.println("MqttException" + e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
    public void stop() {
    	Thread.interrupted();
    	try {
			publisher.disconnect();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		
		try {
			for(;;) {
				Date in = new Date();
				LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
				Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
				Thread.sleep(1000);
				if(out.getMinutes() != currentMinus) {
					currentMinus = out.getMinutes();
					if(publisher.isConnected()) {
						ScheduleJDBC shedule = new ScheduleJDBC(conn);
						List<EquipmentModel> resut = shedule.getEquipSet();
						System.out.println("resut.sixe: "+resut.size());
						if(resut.size()>0) {
							for(int i =0;i<resut.size();i++) {
								String ms = resut.get(i).getStatus()+"";
								MqttMessage msg = new MqttMessage(ms.getBytes());
								String topic = "DV1/"+resut.get(i).getChanel()+"/"+resut.get(i).getPortOutput();
								try {
									publisher.publish(topic, msg);
									System.out.println(topic);
								} catch (MqttPersistenceException e) {
									System.out.println("MqttPersistenceException" + e);
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (MqttException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}				}

//					else {
//						try {
//							publisher.reconnect();
//							ScheduleJDBC shedule2 = new ScheduleJDBC(conn);
//							List<EquipmentModel> resut2 = shedule.getEquipSet();
//							if(resut.size()>0) {
//								for(int i =0;i<resut2.size();i++) {
//									MqttMessage msg = new MqttMessage("1".getBytes());
//									String topic = "DV1/"+resut.get(i).getChanel()+"/"+resut.get(i).getPortOutput();
//									try {
//										publisher.publish(topic, msg);
//										System.out.println(topic);
//									} catch (MqttPersistenceException e) {
//										System.out.println("MqttPersistenceException" + e);
//										// TODO Auto-generated catch block
//										e.printStackTrace();
//									} catch (MqttException e) {
//										// TODO Auto-generated catch block
//										e.printStackTrace();
//									}
//								}
//							}
//						} catch (MqttException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

}
