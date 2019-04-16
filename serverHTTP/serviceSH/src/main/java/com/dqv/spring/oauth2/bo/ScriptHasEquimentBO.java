package com.dqv.spring.oauth2.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dqv.spring.oauth2.DTO.ScriptDTO;
import com.dqv.spring.oauth2.DTO.ScriptDetailDTO;

@Entity
@Table(name = "script_has_equipment")
public class ScriptHasEquimentBO implements Serializable{
    private static final long serialVersionUID = 1L;
	public int scripID;
    public int equipmentID;
    public int status;
    
    @Id
    @Column(name = "script_id")
	public int getScripID() {
		return scripID;
	}
	public void setScripID(int scripID) {
		this.scripID = scripID;
	}
	
    @Id
    @Column(name = "equipment_id")
	public int getEquipmentID() {
		return equipmentID;
	}
	public void setEquipmentID(int equipmentID) {
		this.equipmentID = equipmentID;
	}
	
    @Column(name = "status")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
    
    public ScriptDetailDTO toDTO() {
    	ScriptDetailDTO result = new ScriptDetailDTO();
    	result.scripID = this.scripID;
    	result.equipmentID = this.equipmentID;
    	result.status = this.status;
    	return result;
    }

    
    
}
