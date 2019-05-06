package com.dqv.spring.oauth2.bo;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dqv.spring.oauth2.DTO.ScheduleDTO;

@Entity
@Table(name = "schedule")
public class ScheduleBO {
	public int id;
	public String name;
	public Time timeStart;
	public Time timeEnd;
	public String unitCode;
	public int equipmentID;
	public int scriptID;
	public String day;
	public int status;
	public int statusEquipment;
	

	@Id
    @Column(name = "id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
    @Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
    @Column(name = "time_start")
	public Time getTimeStart() {
		return timeStart;
	}
    
	public void setTimeStart(Time timeStart) {
		this.timeStart = timeStart;
	}
	
    @Column(name = "time_end")
	public Time getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(Time timeEnd) {
		this.timeEnd = timeEnd;
	}
	
    @Column(name = "unit_code")
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	
    @Column(name = "equipment_id")
	public int getEquipmentID() {
		return equipmentID;
	}
	public void setEquipmentID(int equipmentID) {
		this.equipmentID = equipmentID;
	}
	
    @Column(name = "script_id")
	public int getScriptID() {
		return scriptID;
	}
	public void setScriptID(int scriptID) {
		this.scriptID = scriptID;
	}
	
    @Column(name = "day")
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	
    @Column(name = "status")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
    @Column(name = "status_equip")
	public int getStatusEquipment() {
		return statusEquipment;
	}
	public void setStatusEquipment(int statusEquipment) {
		this.statusEquipment = statusEquipment;
	}
	
	public ScheduleDTO toDTO() {
		ScheduleDTO result = new ScheduleDTO();
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
