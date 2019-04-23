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

import com.dqv.spring.oauth2.bo.UserBO;
import com.dqv.spring.oauth2.business.UserBusinessImpl;
import com.dqv.spring.oauth2.helper.Response;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserBusinessImpl userBusinessImpl;

	@RequestMapping(value = "/getAllUser", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public ResponseEntity getAllUser(@RequestBody UserBO bo) {
		Response<List<UserBO>> result = new Response<>();
		result = userBusinessImpl.getAllUser(bo);
		return new ResponseEntity(result,HttpStatus.OK);
	}

	@RequestMapping(value = "/insertUser", method = RequestMethod.POST, produces = { "application/json;application/x-www-form-urlencoded;charset=UTF-8" })
	@ResponseBody 
	public ResponseEntity insertUser(@RequestBody UserBO bo) {
		Response<Boolean> result = new Response<>();
		result = userBusinessImpl.insertUser(bo);
		if(!result.error) {
			return new ResponseEntity(result,HttpStatus.OK);
		}
		else {
			return new ResponseEntity(result,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody 
	public ResponseEntity updateUser(@RequestBody UserBO bo) {
		Response<Boolean> result = new Response<>();
		result = userBusinessImpl.updateUser(bo);
		if(!result.error) {
			return new ResponseEntity(result,HttpStatus.OK);
		}
		else {
			return new ResponseEntity(result,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody 
	public ResponseEntity deleteUser(@RequestBody UserBO bo) {
		Response<Boolean> result = new Response<>();
		result = userBusinessImpl.deleteUser(bo);
		if(!result.error) {
			return new ResponseEntity(result,HttpStatus.OK);
		}
		else {
			return new ResponseEntity(result,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
