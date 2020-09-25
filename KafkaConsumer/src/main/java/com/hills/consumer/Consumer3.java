package com.hills.consumer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.hills.Service.RoomNotFoundException;
import com.hills.Service.RoomService;
import com.hills.models.Room;
import com.hills.models.TopicRecovery;
import com.hills.repositories.TopicRecoveryRepository;

@Service
public class Consumer3 {
	@Autowired
	RoomService roomService;

	@Autowired
	TopicRecoveryRepository recoveryRepo;

	@Value("${spring.topic}")
	private String topic;

	@Value("${spring.kafka.group-one}")
	private String groupOne;
	
	@Value("${spring.kafka.group-two}")
	private String groupTwo;

	/*
	 * @KafkaListener(topics="${topic}",groupId ="${kafka-group-two}",
	 * containerFactory = "ConcurrentKafkaListenerContainerFactory") public void
	 * consume(String message) { System.out.println("Cosumer 3:"+message); }
	 */
	// @KafkaListener(topics="${topic}",groupId ="${kafka-group-two}",
	// containerFactory = "GroupTwoConsumer")
	@KafkaListener(topics = "${spring.topic}", groupId = "${spring.kafka.group-one}", containerFactory = "GroupOneConsumer")
	public void consume(Room room) throws RoomNotFoundException {
		Optional<TopicRecovery> recoveryOpt = recoveryRepo.findByMessageAndType(String.valueOf(room), "consumer");
		int attempts = recoveryOpt.isPresent() ? recoveryOpt.get().getAttempts() : 1;
		if (attempts < 4)
			//throw new RecoverableDataAccessException("Custom Exception in Consumer 3");
		room = roomService.createRoom(room);
		System.out.println("consumer 3:" + room);

	}
	/*
	 * @KafkaListener(topicPartitions = @TopicPartition(topic = "${topic}",
	 * partitions = { "3" }), groupId="${kafka-group-one}",containerFactory =
	 * "kafkaListenerContainerFactory") public void listenToPartition(@Payload
	 * String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
	 * System.out.println("Cosumer 3: Received Message: " + message +
	 * " from partition: " + partition); }
	 */
}
