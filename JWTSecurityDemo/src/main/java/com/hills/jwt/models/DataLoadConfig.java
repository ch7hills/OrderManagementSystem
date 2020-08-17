package com.hills.jwt.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.hills.jwt.config.CustomPasswordEncoder;
import com.hills.jwt.entity.User;
import com.hills.jwt.repository.UserRepository;
@Component
public class DataLoadConfig implements CommandLineRunner{
	@Autowired
	UserRepository userRepo;
	@Autowired
	CustomPasswordEncoder encoder;
	@Override
	public void run(String... args) throws Exception {
		User user1 =new User();
		user1.setIsActive(true);
		user1.setPassword(encoder.encode("test"));
		user1.setRoles("ROLE_USER,ROLE_ADMIN");
		user1.setUserName("test");
		userRepo.save(user1);
	}

}
