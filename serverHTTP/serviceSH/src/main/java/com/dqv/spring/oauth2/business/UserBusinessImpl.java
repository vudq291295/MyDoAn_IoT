package com.dqv.spring.oauth2.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.dqv.spring.oauth2.DAO.UserDAO;
import com.dqv.spring.oauth2.DTO.UserDTO;
import com.dqv.spring.oauth2.bo.UserBO;


@Service("userBusinessImpl")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserBusinessImpl implements UserBusiness{
    @Autowired
    private UserDAO userDAO;

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<UserDTO> getAllUser() {
		// TODO Auto-generated method stub
		List<UserBO> lstUserBO = userDAO.getAllUsers();
		
//		return convertListModeltoDTO(lstUserBO);
		return null;
	}

	@Override
	public UserDTO getUserByUP(UserDTO dto) {
		UserDTO obj = new UserDTO();
		UserBO temp = new UserBO();
//		temp = userDAO.getUserByUP(dto.toModel());
		obj.setUserName(temp.getUserName());
		// TODO Auto-generated method stub
		return obj;
	}

	@Override
	public UserBO findUserByUsername(String bo) {
		System.out.println("userBusinessImpl "+bo );
		UserBO temp = new UserBO();
		temp = userDAO.findUserByUsername(bo);
		return temp;
	}

}
