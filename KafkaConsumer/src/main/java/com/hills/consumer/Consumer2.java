package com.hills.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.hills.Service.RoomNotFoundException;
import com.hills.Service.RoomService;
import com.hills.models.Room;

@Service
public class Consumer2 {
	@Autowired
	RoomService roomService;
	
	@Value("${spring.kafka.group-one}")
	private String groupOne;
	
	@Value("${spring.kafka.group-two}")
	private String groupTwo;
	
	/*
	 * @KafkaListener(topics="${topic}",groupId ="${kafka-group-two}",
	 * containerFactory = "ConcurrentKafkaListenerContainerFactory") public void
	 * consume(String message) { System.out.println("Cosumer 2:"+message); }
	 */
	 @KafkaListener(topics="${spring.topic}",groupId ="${spring.kafka.group-one}", containerFactory = "GroupOneConsumer") 
	 public void consume(Room room) throws RoomNotFoundException {
		 room = roomService.createRoom(room);
		 System.out.println("Cosumer 2:"+room); 
	 }
	/*
	@KafkaListener(topicPartitions = @TopicPartition(topic = "${topic}", partitions = { "2"}), groupId="${kafka-group-two}",containerFactory = "kafkaListenerContainerFactory")
	public void listenToPartition(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
		System.out.println("Cosumer 2: Received Message: " + message + " from partition: " + partition);
	}
	*/

}
