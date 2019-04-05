package com.dqv.spring.oauth2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dqv.spring.oauth2.bo.RoomBO;
import com.dqv.spring.oauth2.business.RoomBusinessImpl;

@RestController
@RequestMapping("/api/room")
public class RoomController {
	@Autowired
	private RoomBusinessImpl roomBusinessImpl;
	
	@RequestMapping(value = "/getAllRoom", method = RequestMethod.GET)
	public ResponseEntity<List<RoomBO>> getAllRoom() {
		List<RoomBO> result = roomBusinessImpl.getAllRoom();
		return new ResponseEntity<List<RoomBO>>(result,HttpStatus.OK);
	}

	@RequestMapping(value = "/insertRoom", method = RequestMethod.POST)
	@ResponseBody 
	public ResponseEntity insertRoom(RoomBO bo) {
		if(roomBusinessImpl.insertRoom(bo)) {
			return new ResponseEntity(true,HttpStatus.OK);
		}
		else {
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/updateRoom", method = RequestMethod.POST)
	@ResponseBody 
	public ResponseEntity updateRoom(RoomBO bo) {
		if(roomBusinessImpl.updateRoom(bo)) {
			return new ResponseEntity(true,HttpStatus.OK);
		}
		else {
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/deleteRoom", method = RequestMethod.POST)
	@ResponseBody 
	public ResponseEntity deleteRoom(RoomBO bo) {
		if(roomBusinessImpl.deleteRoom(bo)) {
			return new ResponseEntity(true,HttpStatus.OK);
		}
		else {
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
