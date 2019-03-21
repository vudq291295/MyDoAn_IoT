package com.dqv.spring.oauth2.business;

import java.util.List;

import com.dqv.spring.oauth2.DTO.UserDTO;
import com.dqv.spring.oauth2.bo.UserBO;


public interface UserBusiness {
	
    long count();
    
    List<UserDTO> getAllUser();

    UserDTO getUserByUP(UserDTO dto);

    UserBO findUserByUsername(String bo);
}
