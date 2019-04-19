package com.dqv.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class EquipmentJDBC {
    private static Logger logger = LoggerFactory.getLogger(EquipmentJDBC.class);
    
	Connection con;
	public EquipmentJDBC(Connection con) {
		this.con = con;
	}
	
	public boolean updateStuatusEquipment(String chanel, String port,String value,String username) {
		String QUERY = "select * from room where chanel = "+chanel;

		int count=0;
		int roomID =0;
		int equipmentID =0;

	   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	   LocalDateTime now = LocalDateTime.now();  
	   String currentTIme = dtf.format(now);
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(QUERY);	
			
			while(rs.next()){
				roomID = rs.getInt("id");
			}
//        	logger.info("VUDQ info:" + roomID);
			if(roomID > 0) {
				String QUERYSELECTEQUPIT = "select * from equipment WHERE room_id="+roomID+" and port_output="+port;
				rs = stmt.executeQuery(QUERYSELECTEQUPIT);	
				while(rs.next()){
					equipmentID = rs.getInt("id");
				}
				if(equipmentID>0) {
//		        	logger.info("VUDQ info:" + username);
					String QUERY2 = "UPDATE equipment SET status="+value+" WHERE room_id="+roomID+" and port_output="+port;
					String QUERY3 = "INSERT into history_control(id_equipment,time,status,username) VALUES ("+equipmentID+",'"+currentTIme+"',"+value+",'"+username+"')";
					stmt.executeUpdate(QUERY2);
//					logger.info("querry 2 xong : " +QUERY3);
					stmt.executeUpdate(QUERY3);
					return true;
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		}

	}
	

	
}
