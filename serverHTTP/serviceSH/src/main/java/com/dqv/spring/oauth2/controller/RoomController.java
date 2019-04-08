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

import com.dqv.spring.oauth2.bo.RoomBO;
import com.dqv.spring.oauth2.business.RoomBusinessImpl;
import com.dqv.spring.oauth2.helper.Response;

@RestController
@RequestMapping("/api/room")
public class RoomController {
	@Autowired
	private RoomBusinessImpl roomBusinessImpl;
	
	@RequestMapping(value = "/getAllRoom", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	public ResponseEntity getAllRoom() {
		Response<List<RoomBO>> result = new Response<>();
		result = roomBusinessImpl.getAllRoom();
		return new ResponseEntity(result,HttpStatus.OK);
	}

	@RequestMapping(value = "/insertRoom", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody 
	public ResponseEntity insertRoom(@RequestBody RoomBO bo) {
		System.out.println("name "+bo.getName());
		Response<Boolean> result = new Response<>();
		result = roomBusinessImpl.insertRoom(bo);
		if(!result.error) {
			return new ResponseEntity(result,HttpStatus.OK);
		}
		else {
			return new ResponseEntity(result,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/updateRoom", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody 
	public ResponseEntity updateRoom(@RequestBody RoomBO bo) {
		Response<Boolean> result = new Response<>();
		result = roomBusinessImpl.updateRoom(bo);
		if(!result.error) {
			return new ResponseEntity(result,HttpStatus.OK);
		}
		else {
			return new ResponseEntity(result,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/deleteRoom", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody 
	public ResponseEntity deleteRoom(@RequestBody RoomBO bo) {
		Response<Boolean> result = new Response<>();
		result = roomBusinessImpl.deleteRoom(bo);
		if(!result.error) {
			return new ResponseEntity(result,HttpStatus.OK);
		}
		else {
			return new ResponseEntity(result,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
