package com.dqv.spring.oauth2.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "room")
public class RoomBO {
    private static final long serialVersionUID = 1L;
    public int id;
    public String name;
    public int chanel;
    
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
	
    @Column(name = "chanel")
	public int getChanel() {
		return chanel;
	}
	public void setChanel(int chanel) {
		this.chanel = chanel;
	}

    
}
