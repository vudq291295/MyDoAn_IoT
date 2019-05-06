package com.dqv.spring.oauth2.bo;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.dqv.spring.oauth2.DTO.EnvironmentDTO;

@Entity
@Table(name = "environment")
public class EnvironmentBO {
	public int id;
	public String type;
	public String unitId;
	public int roomId;
	public String roomName;

	public int value;
	public Date time;
	
	
	
	@Id
    @Column(name = "id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
    @Column(name = "type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
    @Column(name = "unit_id")
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
    @Column(name = "id_room")
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	
    @Column(name = "value")
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	@Column(name = "time", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
    public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	
	public EnvironmentDTO toDTO() {
		EnvironmentDTO result = new EnvironmentDTO();
		result.setId(this.id);
		result.setRoomId(this.roomId);
		result.setTime(this.time);
		result.setType(this.type);
		result.setUnitId(this.unitId);
		result.setValue(this.value);
		return result;
	}
	
}
