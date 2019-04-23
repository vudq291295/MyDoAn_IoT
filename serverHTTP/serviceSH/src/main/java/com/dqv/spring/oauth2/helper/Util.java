package com.dqv.spring.oauth2.helper;

import org.springframework.security.core.context.SecurityContextHolder;

import com.dqv.spring.oauth2.bo.UserBO;
import com.dqv.spring.oauth2.provider.MyUserPrincipal;

public class Util {
	public static UserBO getCurrentUser() {
		UserBO result = new UserBO();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof MyUserPrincipal) {
			result = ((MyUserPrincipal)principal).getUser();
		}
		return result;
	}
}
