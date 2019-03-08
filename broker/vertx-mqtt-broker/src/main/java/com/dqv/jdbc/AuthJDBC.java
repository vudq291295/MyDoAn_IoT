package com.dqv.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.wso2.carbon.identity.oauth2.stub.dto.OAuth2TokenValidationResponseDTO;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;


public class AuthJDBC {
    private static Logger logger = LoggerFactory.getLogger(AuthJDBC.class);

	Connection con;
	public AuthJDBC() {
		con = ConnectDatabase.getConnectDatabase();
	}
	public boolean checkAuth(String username, String pwd) {
		String QUERY = "select count(*) from user where username = '"+username+"' and password ='"+pwd+"'";
		int count=0;
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(QUERY);	
			
			while(rs.next()){
		        count=rs.getInt(1);
			}
			if(count >0)
			{
				return true;
			}
			else {
				return false;
			}				
		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		}

	}
	public OAuth2TokenValidationResponseDTO validation(String username, String pwd) {
		String QUERY = "select * from user where username = '"+username+"' and password ='"+pwd+"'";
		int count=0;
		OAuth2TokenValidationResponseDTO result = new OAuth2TokenValidationResponseDTO();
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(QUERY);	
			while(rs.next()){
				logger.info(rs.getString("username"));
				result.setAuthorizedUser(rs.getString("username"));
			}
			result.setExpiryTime(java.lang.Long.MAX_VALUE);
			result.setErrorMsg("");
			return result;
		} catch (SQLException e) {
			logger.info(e.toString());
			e.printStackTrace();
			result.setExpiryTime(java.lang.Long.MAX_VALUE);
			result.setErrorMsg(e.toString());
			return result;

		}

	}

}
