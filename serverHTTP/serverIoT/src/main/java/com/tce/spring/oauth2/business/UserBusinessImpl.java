package com.tce.spring.oauth2.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.tce.spring.oauth2.DAO.UserDAO;
import com.tce.spring.oauth2.DTO.UserDTO;
import com.tce.spring.oauth2.bo.UserBO;

import vudq.service.base.business.BaseFWBusinessImpl;

@Service("userBusinessImpl")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserBusinessImpl extends BaseFWBusinessImpl<UserDAO,UserDTO,UserBO> implements UserBusiness{
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
		
		return convertListModeltoDTO(lstUserBO);
	}

	@Override
	public UserDTO getUserByUP(UserDTO dto) {
		UserDTO obj = new UserDTO();
		UserBO temp = new UserBO();
		temp = userDAO.getUserByUP(dto.toModel());
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
