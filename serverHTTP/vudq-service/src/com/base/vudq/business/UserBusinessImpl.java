package com.base.vudq.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.base.vudq.bo.UserBO;
import com.base.vudq.dao.UserDAO;
import com.base.vudq.dto.UserDTO;

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

}
