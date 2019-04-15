package com.dqv.spring.oauth2.DTO;

import java.util.ArrayList;
import java.util.List;

import com.dqv.spring.oauth2.bo.ScriptBO;

public class ScriptDTO {
    public int id;
    public String name;
    public List<ScriptDetailDTO> details;
    
    public ScriptDTO() {
    	details = new ArrayList<ScriptDetailDTO>();
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

	public List<ScriptDetailDTO> getDetails() {
		return details;
	}

	public void setDetails(List<ScriptDetailDTO> details) {
		this.details = details;
	}
    
	public ScriptBO toBO() {
		ScriptBO bo = new ScriptBO();
		bo.id = this.id;
		bo.name = this.name;
		return bo;
	}
    
}

