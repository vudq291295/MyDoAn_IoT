package com.dqv.spring.oauth2.business;

import java.util.List;

import com.dqv.spring.oauth2.DTO.ScriptDTO;
import com.dqv.spring.oauth2.bo.ScriptBO;
import com.dqv.spring.oauth2.helper.Response;

public interface ScriptBusiness {
	Response<List<ScriptBO>> getAllScript(ScriptBO bo);
	
	Response<ScriptDTO> getDetailsScript(int idScript);
	
	Response<Boolean> insertScript(ScriptDTO bo);
	
	Response<Boolean> updateScript(ScriptDTO bo);
	
	Response<Boolean> deleteScript(ScriptDTO bo);

}
