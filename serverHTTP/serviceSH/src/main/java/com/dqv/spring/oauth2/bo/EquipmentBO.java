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
    public int status;
    public int congsuat;
    public int dienthedinhmuc;
    public int dienapdinhmuc;
    public String ghi_chu;

    @Column(name = "status")
    public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

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

    @Column(name = "congsuat")
	public int getCongsuat() {
		return congsuat;
	}

	public void setCongsuat(int congsuat) {
		this.congsuat = congsuat;
	}

	@Column(name = "dienthedinhmuc")
	public int getDienthedinhmuc() {
		return dienthedinhmuc;
	}

	public void setDienthedinhmuc(int dienthedinhmuc) {
		this.dienthedinhmuc = dienthedinhmuc;
	}

	@Column(name = "dienapdinhmuc")
	public int getDienapdinhmuc() {
		return dienapdinhmuc;
	}

	public void setDienapdinhmuc(int dienapdinhmuc) {
		this.dienapdinhmuc = dienapdinhmuc;
	}

	@Column(name = "ghi_chu")
	public String getGhi_chu() {
		return ghi_chu;
	}

	public void setGhi_chu(String ghi_chu) {
		this.ghi_chu = ghi_chu;
	}  
	
	
}
