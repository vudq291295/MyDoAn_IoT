package com.dqv.spring.oauth2.DTO;

public class EquipmentDTO {
    public int id;
    public String name;
    public String nameRoom;
    public int portOutput;
    public int roomId;
    public int status;
    public int chanel;
    
    
    
	public String getNameRoom() {
		return nameRoom;
	}
	public void setNameRoom(String nameRoom) {
		this.nameRoom = nameRoom;
	}
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
	public int getPortOutput() {
		return portOutput;
	}
	public void setPortOutput(int portOutput) {
		this.portOutput = portOutput;
	}
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getChanel() {
		return chanel;
	}
	public void setChanel(int chanel) {
		this.chanel = chanel;
	}
    
    
}
