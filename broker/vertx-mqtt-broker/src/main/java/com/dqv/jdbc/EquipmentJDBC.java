package com.dqv.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class EquipmentJDBC {
    private static Logger logger = LoggerFactory.getLogger(EquipmentJDBC.class);
    
	Connection con;
	public EquipmentJDBC(Connection con) {
		this.con = con;
	}
	
	public boolean updateStuatusEquipment(String chanel, String port,String value) {
		String QUERY = "select * from room where chanel = "+chanel;
		int count=0;
		int roomID =0;
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(QUERY);	
			
			while(rs.next()){
				roomID = rs.getInt("id");
			}
        	logger.info("VUDQ info:" + roomID);
			String QUERY2 = "UPDATE equipment SET status="+value+" WHERE room_id="+roomID+" and port_output="+port;
			stmt.executeUpdate(QUERY2);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		}

	}
	

	
}
