package com.dqv.spring.oauth2.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.dqv.spring.oauth2.DAO.ScheduleDAO;
import com.dqv.spring.oauth2.DTO.ScheduleDTO;
import com.dqv.spring.oauth2.bo.RoomBO;
import com.dqv.spring.oauth2.bo.ScheduleBO;
import com.dqv.spring.oauth2.helper.Response;

@Service("scheduleBusinessImpl")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ScheduleBusinessImpl implements ScheduleBusiness{
    @Autowired
    private ScheduleDAO scheduleDAO;

	
	@Override
	public Response<List<ScheduleDTO>> getAllScheduleEquip(ScheduleBO bo) {
		Response<List<ScheduleDTO>> result = new Response<>();
		List<ScheduleDTO> temp = new ArrayList<ScheduleDTO>();
		temp = scheduleDAO.getAllScheduleEquip(bo);
		result.error = false;
		result.data = temp;
		return result;
	}

	@Override
	public Response<List<ScheduleDTO>> getAllScheduleScrpit(ScheduleBO bo) {
		Response<List<ScheduleDTO>> result = new Response<>();
		List<ScheduleDTO> temp = new ArrayList<ScheduleDTO>();
		temp = scheduleDAO.getAllScheduleScrpit(bo);
		result.error = false;
		result.data = temp;
		return result;
	}

	@Override
	public Response<Boolean> insertSchedule(ScheduleDTO bo) {
		Response<Boolean> result = new Response<>();
		if(scheduleDAO.insertSchedule(bo)) {
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
	public Response<Boolean> updateSchedule(ScheduleDTO bo) {
		Response<Boolean> result = new Response<>();
		if(scheduleDAO.updateSchedule(bo)) {
	        result.error = false;
	        result.message = "Thành công";

		}
		else {
        	result.error = true;
        	result.message = "Đã xảy ra lỗi trong quá trình cập nhật";
		}
		return result;
	}

	@Override
	public Response<Boolean> deleteSchedule(ScheduleDTO bo) {
		Response<Boolean> result = new Response<>();
		if(scheduleDAO.deleteSchedule(bo)) {
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
