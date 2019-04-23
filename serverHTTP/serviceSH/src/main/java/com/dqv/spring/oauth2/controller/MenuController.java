package com.dqv.spring.oauth2.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dqv.spring.oauth2.bo.MenuBO;
import com.dqv.spring.oauth2.bo.UserBO;
import com.dqv.spring.oauth2.business.MenuBusiness;
import com.dqv.spring.oauth2.helper.Response;
import com.dqv.spring.oauth2.provider.MyUserPrincipal;;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
		@Autowired
		private MenuBusiness menuBusinessImpl;
		
		@RequestMapping(value = "/getAllMenu", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
		public ResponseEntity getAllMenu() {
			Response<List<MenuBO>> result = new Response<>();
			result = menuBusinessImpl.getAllMenu();
			return new ResponseEntity(result,HttpStatus.OK);
		}


}
