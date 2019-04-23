package com.dqv.spring.oauth2.business;

import java.util.List;

import com.dqv.spring.oauth2.bo.UserBO;
import com.dqv.spring.oauth2.helper.Response;


public interface UserBusiness {
	
    long count();
    
    Response<UserBO>findUserByUsername(String bo);
    
	Response<List<UserBO>> getAllUser(UserBO bo);
	
	Response<Boolean> insertUser(UserBO bo); 
	
	Response<Boolean> updateUser(UserBO bo);
	
	Response<Boolean> deleteUser(UserBO bo);

}
