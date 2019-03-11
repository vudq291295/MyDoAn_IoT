package com.base.vudq.business;

import java.util.List;

import com.base.vudq.bo.UserBO;
import com.base.vudq.dto.UserDTO;

public interface UserBusiness {
	
    long count();
    
    List<UserDTO> getAllUser();

    UserDTO getUserByUP(UserDTO dto);

}
