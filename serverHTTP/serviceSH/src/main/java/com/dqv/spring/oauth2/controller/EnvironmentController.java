package com.dqv.spring.oauth2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dqv.spring.oauth2.bo.EnvironmentBO;
import com.dqv.spring.oauth2.business.EnvironmentBusinessImpl;
import com.dqv.spring.oauth2.helper.Response;

@RestController
@RequestMapping("/api/environment")
public class EnvironmentController {
	@Autowired
	private EnvironmentBusinessImpl environmentBusinessImpl;
	
	@RequestMapping(value = "/getAllEnviroment", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public ResponseEntity getAllEnviroment() {
		Response<List<EnvironmentBO>> result = new Response<>();
		result = environmentBusinessImpl.getAllEnviroment();
		return new ResponseEntity(result,HttpStatus.OK);
	}
	
	
}
