package com.dqv.spring.oauth2.DTO;

import java.sql.Time;

import com.dqv.spring.oauth2.bo.ScheduleBO;

public class ScheduleDTO {
	public int id;
	public String name;
	public Time timeStart;
	public Time timeEnd;
	public String unitCode;
	public int equipmentID;
	public String equipmentName;
	public int scriptID;
	public String scriptName;
	public String day;
	public int status;
	public int statusEquipment;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Time getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(Time timeStart) {
		this.timeStart = timeStart;
	}
	public Time getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(Time timeEnd) {
		this.timeEnd = timeEnd;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public int getEquipmentID() {
		return equipmentID;
	}
	public void setEquipmentID(int equipmentID) {
		this.equipmentID = equipmentID;
	}
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	public int getScriptID() {
		return scriptID;
	}
	public void setScriptID(int scriptID) {
		this.scriptID = scriptID;
	}
	public String getScriptName() {
		return scriptName;
	}
	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getStatusEquipment() {
		return statusEquipment;
	}
	public void setStatusEquipment(int statusEquipment) {
		this.statusEquipment = statusEquipment;
	}
	
	public ScheduleBO toBO() {
		ScheduleBO result = new ScheduleBO();
		result.setId(this.id);
		result.setName(this.name);
		result.setTimeStart(this.timeStart);
		result.setTimeEnd(this.timeEnd);
		result.setDay(this.day);
		result.setUnitCode(this.unitCode);
		result.setEquipmentID(this.equipmentID);
		result.setScriptID(this.scriptID);
		result.setStatus(this.status);
		result.setStatusEquipment(this.statusEquipment);
		return result;

	}

	
}
