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

import com.dqv.spring.oauth2.bo.UnitBO;
import com.dqv.spring.oauth2.business.UnitBusinessImpl;
import com.dqv.spring.oauth2.helper.Response;

@RestController
@RequestMapping("/api/unit")
public class UnitController {
	@Autowired
	private UnitBusinessImpl unitBusinessImpl;

	@RequestMapping(value = "/getAllUnit", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public ResponseEntity getAllUnit(@RequestBody UnitBO bo) {
		Response<List<UnitBO>> result = new Response<>();
		result = unitBusinessImpl.getAllUnit(bo);
		return new ResponseEntity(result,HttpStatus.OK);
	}

	@RequestMapping(value = "/insertUnit", method = RequestMethod.POST, produces = { "application/json;application/x-www-form-urlencoded;charset=UTF-8" })
	@ResponseBody 
	public ResponseEntity insertUnit(@RequestBody UnitBO bo) {
		System.out.println("name "+bo.getName());
		Response<Boolean> result = new Response<>();
		result = unitBusinessImpl.insertUnit(bo);
		if(!result.error) {
			return new ResponseEntity(result,HttpStatus.OK);
		}
		else {
			return new ResponseEntity(result,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/updateUnit", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody 
	public ResponseEntity updateUnit(@RequestBody UnitBO bo) {
		Response<Boolean> result = new Response<>();
		result = unitBusinessImpl.updateUnit(bo);
		if(!result.error) {
			return new ResponseEntity(result,HttpStatus.OK);
		}
		else {
			return new ResponseEntity(result,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/deleteUnit", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody 
	public ResponseEntity deleteUnit(@RequestBody UnitBO bo) {
		Response<Boolean> result = new Response<>();
		result = unitBusinessImpl.deleteUnit(bo);
		if(!result.error) {
			return new ResponseEntity(result,HttpStatus.OK);
		}
		else {
			return new ResponseEntity(result,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}
