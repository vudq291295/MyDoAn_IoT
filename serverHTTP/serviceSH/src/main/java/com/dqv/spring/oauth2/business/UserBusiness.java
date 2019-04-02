package com.dqv.spring.oauth2.business;

import java.util.List;

import com.dqv.spring.oauth2.DTO.UserDTO;
import com.dqv.spring.oauth2.bo.UserBO;


public interface UserBusiness {
	
    long count();
    
    UserBO findUserByUsername(String bo);
}
