package com.hills.jwt.models;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hills.jwt.entity.User;
import com.hills.jwt.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOpt = userRepo.findByUserName( username);
		System.out.println("User details:"+username);
		System.out.println("User details:"+userOpt);
		userOpt.orElseThrow(()->new UsernameNotFoundException("Not found:"+username));
		return userOpt.map(MyUserDetails::new).get();
		/*
		if(userOpt.isPresent()) {
			User user = userOpt.get();
			MyUserDetails myUserDetails = new MyUserDetails(user);
			System.out.println("In Service: :myUserDetails:"+myUserDetails.getUsername());
			return  myUserDetails;
		}else {
			throw new UsernameNotFoundException("User not found:"+username);
		}*/
		
	}

}
