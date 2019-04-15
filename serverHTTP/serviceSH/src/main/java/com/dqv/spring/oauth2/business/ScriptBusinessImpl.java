package com.dqv.spring.oauth2.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.dqv.spring.oauth2.DAO.RoomDAO;
import com.dqv.spring.oauth2.DAO.ScriptDAO;
import com.dqv.spring.oauth2.DTO.ScriptDTO;
import com.dqv.spring.oauth2.bo.RoomBO;
import com.dqv.spring.oauth2.bo.ScriptBO;
import com.dqv.spring.oauth2.helper.Response;

@Service("scriptBusinessImpl")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ScriptBusinessImpl implements ScriptBusiness{
    @Autowired
    private ScriptDAO sctpitDAO;


	@Override
	public Response<List<ScriptBO>> getAllScript() {
		Response<List<ScriptBO>> result = new Response<>();
		List<ScriptBO> temp = new ArrayList<ScriptBO>();
		temp = sctpitDAO.getAllScript();
		result.error = false;
		result.data = temp;
		return result;
	}

	@Override
	public Response<ScriptDTO> getDetailsScript(int idScript) {
		Response<ScriptDTO> result = new Response<>();
		ScriptDTO temp = new ScriptDTO();
		temp = sctpitDAO.getDetailsScript(idScript);
		result.error = false;
		result.data = temp;
		return result;
	}

	@Override
	public Response<Boolean> insertScript(ScriptDTO bo) {
		Response<Boolean> result = new Response<>();
		if(sctpitDAO.insertRoom(bo)) {
	        result.error = false;
	        result.message = "Thành công";

		}
		else {
        	result.error = true;
        	result.message = "Đã xảy ra lỗi trong quá trình thêm mới";
		}
	
		return result;	
	}
	

}
