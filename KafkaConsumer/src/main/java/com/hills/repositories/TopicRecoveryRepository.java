package com.hills.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hills.models.TopicRecovery;
//import com.sun.el.stream.Optional;

@Repository
public interface TopicRecoveryRepository extends JpaRepository<TopicRecovery,Long>{

	Optional<TopicRecovery> findByMessage(String string);
	Optional<TopicRecovery> findByMessageAndType(String room, String type);
	
}
