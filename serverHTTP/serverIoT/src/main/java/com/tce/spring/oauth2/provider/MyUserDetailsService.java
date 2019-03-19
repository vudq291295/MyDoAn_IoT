package com.tce.spring.oauth2.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tce.spring.oauth2.DAO.UserDAO;
import com.tce.spring.oauth2.bo.UserBO;
import com.tce.spring.oauth2.business.UserBusinessImpl;


@Service
public class MyUserDetailsService implements UserDetailsService{
    @Autowired
    private UserBusinessImpl userBusinessImpl;

	@Override
	public UserDetails loadUserByUsername(String username) {
		// TODO Auto-generated method stub
        System.out.println("loadUserByUsername");
		UserBO user = userBusinessImpl.findUserByUsername(username);
//		UserBO user = new UserBO();
//		user.setUserName(username);
//		user.setPassWord("admin");
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new MyUserPrincipal(user);

	}
}
