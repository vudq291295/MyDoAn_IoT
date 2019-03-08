package com.dqv.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public  class ConnectDatabase {
    private static Logger logger = LoggerFactory.getLogger(ConnectDatabase.class);
	public ConnectDatabase() {
		
	}
	public static Connection getConnectDatabase() {
		Properties props = new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream("db.properties");
			props.load(in);
			in.close();
			logger.info(props.getProperty("jdbc.url"));
			String driver = props.getProperty("jdbc.driver");
			if (driver != null) {
			    Class.forName(driver) ;
			}
			
			String url = props.getProperty("jdbc.url");
			String username = props.getProperty("jdbc.username");
			String password = props.getProperty("jdbc.password");
			
			Connection con = DriverManager.getConnection(url, username, password);
			return con;
		} catch (IOException | ClassNotFoundException | SQLException e) {
			logger.info(e.toString());
			// TODO Auto-generated catch block1e
			e.printStackTrace();
			return null;
		}

	}
}
