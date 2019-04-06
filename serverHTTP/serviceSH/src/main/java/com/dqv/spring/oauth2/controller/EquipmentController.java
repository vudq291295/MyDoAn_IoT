package com.dqv.spring.oauth2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dqv.spring.oauth2.bo.EquipmentBO;
import com.dqv.spring.oauth2.bo.RoomBO;
import com.dqv.spring.oauth2.business.EquipmentBusinessImpl;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

	@Autowired
	public EquipmentBusinessImpl equipmentBusinessImpl;
	
	@RequestMapping(value = "/getAllEpuipment", method = RequestMethod.GET)
	public ResponseEntity<List<EquipmentBO>> getAllEpuipment() {
		List<EquipmentBO> result = equipmentBusinessImpl.getAllEpuipment();
		return new ResponseEntity<List<EquipmentBO>>(result,HttpStatus.OK);
	}

	@RequestMapping(value = "/getEpuipmentByRoom/{idRoom}", method = RequestMethod.GET)
	public ResponseEntity<List<EquipmentBO>> getEpuipmentByRoom(@PathVariable(value="idRoom") int idRoom) {
		List<EquipmentBO> result = equipmentBusinessImpl.getEpuipmentByRoom(idRoom);
		return new ResponseEntity<List<EquipmentBO>>(result,HttpStatus.OK);
	}

	@RequestMapping(value = "/insertEpuipment", method = RequestMethod.POST)
	@ResponseBody 
	public ResponseEntity insertEpuipment(EquipmentBO bo) {
		if(equipmentBusinessImpl.insertEpuipment(bo)) {
			return new ResponseEntity(true,HttpStatus.OK);
		}
		else {
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/updateEpuipment", method = RequestMethod.POST)
	@ResponseBody 
	public ResponseEntity updateEpuipment(EquipmentBO bo) {
		if(equipmentBusinessImpl.updateEpuipment(bo)) {
			return new ResponseEntity(true,HttpStatus.OK);
		}
		else {
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/deleteEpuipment", method = RequestMethod.POST)
	@ResponseBody 
	public ResponseEntity deleteEpuipment(EquipmentBO bo) {
		if(equipmentBusinessImpl.deleteEpuipment(bo)) {
			return new ResponseEntity(true,HttpStatus.OK);
		}
		else {
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
