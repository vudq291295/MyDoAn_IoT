package com.dqv.spring.oauth2.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.dqv.spring.oauth2.DAO.MenuDAO;
import com.dqv.spring.oauth2.bo.MenuBO;
import com.dqv.spring.oauth2.helper.Response;

@Service("menuBusinessImpl")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MenuBusinessImpl implements MenuBusiness{
    @Autowired
    private MenuDAO menuDAO;

	@Override
	public Response<List<MenuBO>> getAllMenu() {
		Response<List<MenuBO>> result = new Response<>();
		List<MenuBO> temp = new ArrayList<MenuBO>();
		temp = menuDAO.getAllMenu();
		result.error = false;
		result.data = temp;
		return result;
	}

}
