package com.dqv.spring.oauth2.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dqv.spring.oauth2.DTO.ScriptDTO;

@Entity
@Table(name = "script")
public class ScriptBO {
    private static final long serialVersionUID = 1L;
    public int id;
    public String name;
    
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

    public ScriptDTO toDTO() {
    	ScriptDTO result = new ScriptDTO();
    	result.id = this.id;
    	result.name = this.name;
    	return result;
    }
}
