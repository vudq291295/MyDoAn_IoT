package com.dqv.spring.oauth2.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.dqv.spring.oauth2.DAO.UnitDAO;
import com.dqv.spring.oauth2.bo.UnitBO;
import com.dqv.spring.oauth2.helper.Response;

@Service("unitBusinessImpl")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UnitBusinessImpl implements UnitBusiness{
    @Autowired
    private UnitDAO unitDAO;

	
	@Override
	public Response<List<UnitBO>> getAllUnit(UnitBO bo) {
		Response<List<UnitBO>> result = new Response<>();
		List<UnitBO> temp = new ArrayList<UnitBO>();
		temp = unitDAO.getAllUnit(bo);
		result.error = false;
		result.data = temp;
		return result;
	}

	@Override
	public Response<Boolean> insertUnit(UnitBO bo) {
		Response<Boolean> result = new Response<>();
		if(unitDAO.insertUnit(bo)) {
	        result.error = false;
	        result.message = "Thành công";

		}
		else {
        	result.error = true;
        	result.message = "Đã xảy ra lỗi trong quá trình xóa";
		}
		return result;
	}

	@Override
	public Response<Boolean> updateUnit(UnitBO bo) {
		Response<Boolean> result = new Response<>();
		if(unitDAO.updateUnit(bo)) {
	        result.error = false;
	        result.message = "Thành công";

		}
		else {
        	result.error = true;
        	result.message = "Đã xảy ra lỗi trong quá trình xóa";
		}
		return result;
	}

	@Override
	public Response<Boolean> deleteUnit(UnitBO bo) {
		Response<Boolean> result = new Response<>();
		if(unitDAO.deleteUnit(bo)) {
	        result.error = false;
	        result.message = "Thành công";

		}
		else {
        	result.error = true;
        	result.message = "Đã xảy ra lỗi trong quá trình xóa";
		}
		return result;
	}

}
