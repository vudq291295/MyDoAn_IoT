package com.dqv.spring.oauth2.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.dqv.spring.oauth2.DAO.UserDAO;
import com.dqv.spring.oauth2.DTO.UserDTO;
import com.dqv.spring.oauth2.bo.RoomBO;
import com.dqv.spring.oauth2.bo.UserBO;
import com.dqv.spring.oauth2.helper.Response;


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
	public Response<UserBO> findUserByUsername(String bo) {
		Response<UserBO> result = new Response<>();
		System.out.println("userBusinessImpl "+bo );
		UserBO temp = new UserBO();
		temp = userDAO.findUserByUsername(bo);
		result.setError(false);
		result.setData(temp);
		return result;
	}

	@Override
	public Response<List<UserBO>> getAllUser(UserBO bo) {
		Response<List<UserBO>> result = new Response<>();
		List<UserBO> temp = new ArrayList<UserBO>();
		temp = userDAO.getAllUser(bo);
		result.error = false;
		result.data = temp;
		return result;
	}

	@Override
	public Response<Boolean> insertUser(UserBO bo) {
		Response<Boolean> result = new Response<>();
		if(userDAO.insertUser(bo)) {
	        result.error = false;
	        result.message = "Thành công";

		}
		else {
        	result.error = true;
        	result.message = "Đã xảy ra lỗi trong quá trình thêm mới";
		}
		return result;	
	}

	@Override
	public Response<Boolean> updateUser(UserBO bo) {
		Response<Boolean> result = new Response<>();
		if(userDAO.updateUser(bo)) {
	        result.error = false;
	        result.message = "Thành công";

		}
		else {
        	result.error = true;
        	result.message = "Đã xảy ra lỗi trong quá trình thêm mới";
		}
		return result;	
	}

	@Override
	public Response<Boolean> deleteUser(UserBO bo) {
		Response<Boolean> result = new Response<>();
		if(userDAO.deleteUser(bo)) {
	        result.error = false;
	        result.message = "Thành công";

		}
		else {
        	result.error = true;
        	result.message = "Đã xảy ra lỗi trong quá trình thêm mới";
		}
		return result;	
	}

}
