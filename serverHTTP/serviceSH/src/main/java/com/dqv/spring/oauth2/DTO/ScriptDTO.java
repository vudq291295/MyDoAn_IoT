package com.dqv.spring.oauth2.DTO;

import java.util.ArrayList;
import java.util.List;

public class ScriptDTO {
    public int id;
    public String name;
    public List<ScriptDetailDTO> Details;
    
    public ScriptDTO() {
    	Details = new ArrayList<ScriptDetailDTO>();
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
		return Details;
	}

	public void setDetails(List<ScriptDetailDTO> details) {
		Details = details;
	}
    
    
}

