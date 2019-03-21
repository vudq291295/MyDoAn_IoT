package com.dqv.spring.oauth2.provider;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dqv.spring.oauth2.bo.UserBO;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class MyUserPrincipal implements UserDetails{
	
    private UserBO user;
    
    public MyUserPrincipal(UserBO user) {
        this.user = user;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassWord();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUserName();
	}
	
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//        for (final Privilege privilege : user.getPrivileges()) {
//            authorities.add(new SimpleGrantedAuthority(privilege.getName()));
//        }
//        return authorities;
//    }

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
    public UserBO getUser() {
        return user;
    }

}
