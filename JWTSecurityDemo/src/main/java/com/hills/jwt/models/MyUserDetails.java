package com.hills.jwt.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.hills.jwt.entity.User;
import com.hills.jwt.repository.UserRepository;

public class MyUserDetails implements UserDetails{
	
	private static final long serialVersionUID = 2734147261105192963L;
	
	private String userName;
	private String password;
	private Boolean isActive;
	private List<GrantedAuthority> authorities = new ArrayList<>();
	
	public MyUserDetails(User user) {
		this.userName =user.getUserName();
		this.password =user.getPassword();
		this.authorities = Arrays.stream(user.getRoles().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		this.isActive =user.getIsActive();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return this.isActive;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return this.isActive;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return this.isActive;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.isActive;
	}

}
