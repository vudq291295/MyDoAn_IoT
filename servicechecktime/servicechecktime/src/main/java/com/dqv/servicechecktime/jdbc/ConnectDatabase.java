package com.dqv.servicechecktime.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public  class ConnectDatabase {
	public ConnectDatabase() {
		
	}
	public static Connection getConnectDatabase() {
		Properties props = new Properties();
		FileInputStream in;
		try {
//			in = new FileInputStream("db.properties");
//			props.load(in);
//			in.close();
//			System.out.println(props.getProperty("jdbc.url"));
//			if (driver != null) {
//			    Class.forName(driver) ;
//			}
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/control_equipment";
			String username = "root";
			String password = "admin@123";
			
			Connection con = DriverManager.getConnection(url, username, password);
			return con;
		} catch (Exception e) {
			System.out.println(e.toString());
			// TODO Auto-generated catch block1e
			e.printStackTrace();
			return null;
		}

	}
}
