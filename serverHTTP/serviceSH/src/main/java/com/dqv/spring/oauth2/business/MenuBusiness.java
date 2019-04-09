package com.dqv.spring.oauth2.business;

import java.util.List;

import com.dqv.spring.oauth2.bo.MenuBO;
import com.dqv.spring.oauth2.helper.Response;

public interface MenuBusiness {
	Response<List<MenuBO>> getAllMenu();
}
