package com.dqv.spring.oauth2.business;

import java.util.List;

import com.dqv.spring.oauth2.DTO.ScheduleDTO;
import com.dqv.spring.oauth2.bo.ScheduleBO;
import com.dqv.spring.oauth2.helper.Response;

public interface ScheduleBusiness {

	public Response<List<ScheduleDTO>>getAllScheduleEquip(ScheduleBO bo);
	public Response<List<ScheduleDTO>>getAllScheduleScrpit(ScheduleBO bo);

	
	public Response<Boolean>insertSchedule(ScheduleDTO bo);
	public Response<Boolean>updateSchedule(ScheduleDTO bo);
	public Response<Boolean>deleteSchedule(ScheduleDTO bo);

}
