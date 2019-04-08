package com.dqv.spring.oauth2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dqv.spring.oauth2.DTO.EquipmentDTO;
import com.dqv.spring.oauth2.bo.EquipmentBO;
import com.dqv.spring.oauth2.bo.RoomBO;
import com.dqv.spring.oauth2.business.EquipmentBusinessImpl;
import com.dqv.spring.oauth2.helper.Response;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

	@Autowired
	public EquipmentBusinessImpl equipmentBusinessImpl;
	
	@RequestMapping(value = "/getAllEpuipment", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	public ResponseEntity getAllEpuipment() {
		Response<List<EquipmentDTO>> result = new Response<>();
		result = equipmentBusinessImpl.getAllEpuipment();
		return new ResponseEntity(result,HttpStatus.OK);
	}

	@RequestMapping(value = "/getEpuipmentByRoom/{idRoom}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	public ResponseEntity getEpuipmentByRoom(@PathVariable(value="idRoom") int idRoom) {
		Response<List<EquipmentDTO>> result = new Response<>();
		result = equipmentBusinessImpl.getEpuipmentByRoom(idRoom);
		return new ResponseEntity(result,HttpStatus.OK);
	}

	@RequestMapping(value = "/insertEpuipment", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody 
	public ResponseEntity insertEpuipment(@RequestBody EquipmentBO bo) {
		Response<Boolean> result = new Response<>();
		result = equipmentBusinessImpl.insertEpuipment(bo);
		if(!result.error) {
			return new ResponseEntity(result,HttpStatus.OK);
		}
		else {
			return new ResponseEntity(result,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/updateEpuipment", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody 
	public ResponseEntity updateEpuipment(@RequestBody EquipmentBO bo) {
		Response<Boolean> result = new Response<>();
		result = equipmentBusinessImpl.updateEpuipment(bo);
		if(!result.error) {
			return new ResponseEntity(result,HttpStatus.OK);
		}
		else {
			return new ResponseEntity(result,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/deleteEpuipment", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody 
	public ResponseEntity deleteEpuipment(@RequestBody EquipmentBO bo) {
		Response<Boolean> result = new Response<>();
		result = equipmentBusinessImpl.deleteEpuipment(bo);
		if(!result.error) {
			return new ResponseEntity(result,HttpStatus.OK);
		}
		else {
			return new ResponseEntity(result,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
