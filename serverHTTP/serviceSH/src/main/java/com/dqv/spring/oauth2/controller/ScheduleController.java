package com.dqv.spring.oauth2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dqv.spring.oauth2.DTO.ScheduleDTO;
import com.dqv.spring.oauth2.bo.RoomBO;
import com.dqv.spring.oauth2.bo.ScheduleBO;
import com.dqv.spring.oauth2.business.ScheduleBusinessImpl;
import com.dqv.spring.oauth2.helper.Response;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
	@Autowired
	private ScheduleBusinessImpl scheduleBusinessImpl;
	
	@RequestMapping(value = "/getAllScheduleEquip", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public ResponseEntity getAllScheduleEquip(@RequestBody ScheduleBO bo) {
		if(bo!=null) {
			System.out.println(bo.getName());
		}
		Response<List<ScheduleDTO>> result = new Response<>();
		result = scheduleBusinessImpl.getAllScheduleEquip(bo);
		return new ResponseEntity(result,HttpStatus.OK);
	}

	@RequestMapping(value = "/insertSchedule", method = RequestMethod.POST, produces = { "application/json;application/x-www-form-urlencoded;charset=UTF-8" })
	@ResponseBody 
	public ResponseEntity insertSchedule(@RequestBody ScheduleDTO bo) {
		System.out.println("name "+bo.getName());
		Response<Boolean> result = new Response<>();
		result = scheduleBusinessImpl.insertSchedule(bo);
		if(!result.error) {
			return new ResponseEntity(result,HttpStatus.OK);
		}
		else {
			return new ResponseEntity(result,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/updateSchedule", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody 
	public ResponseEntity updateSchedule(@RequestBody ScheduleDTO bo) {
		Response<Boolean> result = new Response<>();
		result = scheduleBusinessImpl.updateSchedule(bo);
		if(!result.error) {
			return new ResponseEntity(result,HttpStatus.OK);
		}
		else {
			return new ResponseEntity(result,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/deleteSchedule", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody 
	public ResponseEntity deleteSchedule(@RequestBody ScheduleDTO bo) {
		Response<Boolean> result = new Response<>();
		result = scheduleBusinessImpl.deleteSchedule(bo);
		if(!result.error) {
			return new ResponseEntity(result,HttpStatus.OK);
		}
		else {
			return new ResponseEntity(result,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
