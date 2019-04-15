package com.dqv.spring.oauth2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dqv.spring.oauth2.DTO.ScriptDTO;
import com.dqv.spring.oauth2.bo.EquipmentBO;
import com.dqv.spring.oauth2.bo.RoomBO;
import com.dqv.spring.oauth2.bo.ScriptBO;
import com.dqv.spring.oauth2.business.RoomBusinessImpl;
import com.dqv.spring.oauth2.business.ScriptBusinessImpl;
import com.dqv.spring.oauth2.helper.Response;

@RestController
@RequestMapping("/api/script")
public class ScriptController {

	@Autowired
	private ScriptBusinessImpl scriptBusinessImpl;
	
	@RequestMapping(value = "/getAllScript", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	public ResponseEntity getAllScript() {
		Response<List<ScriptBO>> result = new Response<>();
		result = scriptBusinessImpl.getAllScript();
		return new ResponseEntity(result,HttpStatus.OK);
	}

	@RequestMapping(value = "/getDetailsScript/{idScript}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	public ResponseEntity getDetailsScript(@PathVariable(value="idScript") int idScript) {
		System.out.println(idScript);
		Response<ScriptDTO> result = new Response<>();
		result = scriptBusinessImpl.getDetailsScript(idScript);
		return new ResponseEntity(result,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/insertScript", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" },
			consumes="application/json;charset=UTF-8")
	@ResponseBody 
	public ResponseEntity insertScript(@RequestBody String bo) {
		System.out.println(bo); 
//		System.out.println(encoding);
//		System.out.println(encoding2);
		Response<Boolean> result = new Response<>();
//		result = scriptBusinessImpl.insertScript(bo);
		if(!result.error) {
			return new ResponseEntity(result,HttpStatus.OK);
		}
		else {
			return new ResponseEntity(result,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}
