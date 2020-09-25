package com.hills.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hills.model.Room;
import com.hills.model.TopicRecovery;
import com.hills.repositories.TopicRecoveryRepository;

@Service
public class ProducerService {
	
	@Autowired
	TopicRecoveryRepository recoveryRepo;
	public void updateRecovery(Room room){
		
		Optional<TopicRecovery> recoveryOpt = recoveryRepo.findByMessageAndType(room.toString(),"producer");
		int attempts = recoveryOpt.isPresent()?recoveryOpt.get().getAttempts():1;
		TopicRecovery recovery = recoveryOpt.isPresent()?recoveryOpt.get(): new TopicRecovery();
		recovery.setAttempts(attempts);
		recovery.setType("producer");
		recovery.setMessage(String.valueOf(room));
		recoveryRepo.save(recovery);
		
	}
}
