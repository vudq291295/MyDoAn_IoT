package com.base.vudq.rest;

import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.base.vudq.business.UserBusinessImpl;
import com.base.vudq.dto.UserDTO;

import vudq.service.base.dto.ResponseModelImpl;

public class UserRestImpl implements UserRest {
	
    @Autowired
    private UserBusinessImpl userBusinessImpl;
    
	@Override
	public Response getAllUser() {
        List<UserDTO> ls = userBusinessImpl.getAllUser();
		// TODO Auto-generated method stub
		return Response.ok(ls).build();
	}

	@Override
	public Response getUserByUP(UserDTO dto) {
		// TODO Auto-generated method stub
		UserDTO result = userBusinessImpl.getUserByUP(dto);
		ResponseModelImpl<UserDTO> result2 = new ResponseModelImpl<UserDTO>();
		if(result!=null) {
			result2.setSuccess(true);
			result2.setMessage("");
			result2.setData(result);
			return Response.ok(result2).build();
		}
		else {
			result2.setSuccess(false);
			result2.setMessage("Sai tên đăng nhập hoặc mật khẩu");
			result2.setData(result);
			return Response.ok(result2).build();
		}
	}

}
