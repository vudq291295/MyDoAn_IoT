package com.dqv.spring.oauth2.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//@Entity
//@Table(name = "script_has_equipment")
public class ScriptHasEquimentBO {
    public int scripID;
    public int equipmentID;
    public int status;
    
//    @Column(name = "script_id")
	public int getScripID() {
		return scripID;
	}
	public void setScripID(int scripID) {
		this.scripID = scripID;
	}
	
//    @Column(name = "equipment_id")
	public int getEquipmentID() {
		return equipmentID;
	}
	public void setEquipmentID(int equipmentID) {
		this.equipmentID = equipmentID;
	}
	
//    @Column(name = "status")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
    
    
    
}
