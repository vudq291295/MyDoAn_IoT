package com.dqv.spring.oauth2.DTO;


public class ScriptDetailDTO {
	public int scripID;
    public int equipmentID;
    public int status;
    public int equipmentName;
    public int equipmentPort;
    public int roomID;

    public ScriptDetailDTO() {
    	
    }

	public int getScripID() {
		return scripID;
	}

	public void setScripID(int scripID) {
		this.scripID = scripID;
	}

	public int getEquipmentID() {
		return equipmentID;
	}

	public void setEquipmentID(int equipmentID) {
		this.equipmentID = equipmentID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(int equipmentName) {
		this.equipmentName = equipmentName;
	}

	public int getEquipmentPort() {
		return equipmentPort;
	}

	public void setEquipmentPort(int equipmentPort) {
		this.equipmentPort = equipmentPort;
	}

	public int getRoomID() {
		return roomID;
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}
    
    
}

