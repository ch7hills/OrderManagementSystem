package com.hills.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hills.model.TopicRecovery;

@Repository
public interface TopicRecoveryRepository extends JpaRepository<TopicRecovery,Long>{

	Optional<TopicRecovery> findByMessage(String string);

	Optional<TopicRecovery> findByMessageAndType(String room, String type);
	
}
