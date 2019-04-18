package com.dqv.spring.oauth2.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.dqv.spring.oauth2.DAO.EnvironmentDAO;
import com.dqv.spring.oauth2.bo.EnvironmentBO;
import com.dqv.spring.oauth2.helper.Response;

@Service("environmentBusinessImpl")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EnvironmentBusinessImpl implements EnvironmentBusiness{
	
	@Autowired
	EnvironmentDAO environmentDAO;

	@Override
	public Response<List<EnvironmentBO>> getAllEnviroment() {
		Response<List<EnvironmentBO>> result = new Response<>();
		List<EnvironmentBO> temp = new ArrayList<EnvironmentBO>();
		temp = environmentDAO.getAllEnviroment();
		System.out.println(temp.get(0).getTime().getDay());
		result.error = false;
		result.data = temp;
		return result;
	}

}
