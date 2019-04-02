package com.dqv.spring.oauth2.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "equipment")
public class EquipmentBO {
    private static final long serialVersionUID = 1L;
    public int id;
    public String name;
    public int portOutput;
    public int roomId;
    
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
	
	@Column(name = "port_output")
    public int getPortOutput() {
		return portOutput;
	}

	public void setPortOutput(int portOutput) {
		this.portOutput = portOutput;
	}

    @Column(name = "room_id")
	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}  
}
