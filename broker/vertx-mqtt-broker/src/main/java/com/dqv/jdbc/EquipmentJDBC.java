package com.dqv.jdbc;

import java.sql.Connection;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class EquipmentJDBC {
    private static Logger logger = LoggerFactory.getLogger(EquipmentJDBC.class);
    
	Connection con;
	public EquipmentJDBC() {
		con = ConnectDatabase.getConnectDatabase();
	}
	
	

	
}
