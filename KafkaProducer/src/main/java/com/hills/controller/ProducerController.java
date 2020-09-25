package com.hills.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hills.model.Room;
import com.hills.repositories.RoomRepository;
import com.hills.repositories.TopicRecoveryRepository;
import com.hills.services.ProducerService;

@RestController
public class ProducerController {
	@Value("${spring.topic}")
	private String topic;

	@Autowired
	KafkaTemplate<String, Room> template;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	RoomRepository roomRepo;
	
	@Autowired
	TopicRecoveryRepository recoveryRepo;
	
	@Autowired
	ProducerService producerService;

	@GetMapping("/send")
	public String sendData() {
		System.out.println("################ start #################");
		Room latest = roomRepo.findTopByOrderByIdDesc();
		if (latest == null) {
			latest = new Room(11L, 11L, "Room11");
		}
		
		for (long i = latest.getId()+1; i < latest.getId() + 11; i++) {
			// for (long i = 223; i < 246; i++) {
			Room room = new Room(i+1,i+1, "Room"+i);
			try {
					
					System.out.println("Loop variable:"+i);
					Optional<Room> roomOpt = roomRepo.findById(i);
					room = roomOpt.isPresent()?roomOpt.get():room;
					//System.out.println("roomOpt:"+roomOpt.isPresent()+" room:"+roomOpt.get());
					System.out.println("Room--Room:"+room);
					// Room room = new Room(i,i, "Room"+i);
					// template.send(topic, room);
					ListenableFuture<SendResult<String, Room>> result = template.send(topic, room);
					// result.addCallback(callback);
					result.addCallback(new ListenableFutureCallback<SendResult<String, Room>>() {

						@Override
						public void onSuccess(SendResult<String, Room> result) {
							// System.out.println("Success:"+result.toString());
							// TODO Auto-generated method stub
							successHandler(result);
						}

						@Override
						public void onFailure(Throwable ex) {
							// TODO Auto-generated method stub
							failureHandler(ex);
						}

					});

					
					System.out.println("Result:"+result.get().getProducerRecord().value());
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception:" + e.getMessage());
			}
		}
		System.out.println("################ end #################");
		return "success";
	}

	public void successHandler(SendResult<String, Room> result) {
		System.out.println("successHandler " + result.toString());
		// System.out.println("-----------------------@@@----------------------------");
	}

	public void failureHandler(Throwable ex) {
		producerService.updateRecovery(null);
		System.out.println("failureHandler: " + ex.getMessage());
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateRecord(@RequestBody Room room) {
		if (room.getId() == 0) {
			return new ResponseEntity<>("Please enter valid id", HttpStatus.BAD_REQUEST);
		}
		try {
			ListenableFuture<SendResult<String, Room>> result = template.send(topic, null, room);
			result.addCallback(new ListenableFutureCallback<SendResult<String, Room>>() {

				@Override
				public void onSuccess(SendResult<String, Room> result) {
					// TODO Auto-generated method stub
					successHandler(result);
				}

				@Override
				public void onFailure(Throwable ex) {
					// TODO Auto-generated method stub
					failureHandler(ex);
				}

			});
			System.out.println(room);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return new ResponseEntity<Room>(room, HttpStatus.OK);
	}

	@PostMapping("/sendMessage")
	public String sendMessage(@RequestBody Room room) {
		try {
			// Room room = new Room(, "Room:"+message, "Room"+message);
			ListenableFuture<SendResult<String, Room>> result = template.send(topic, room);
			result.addCallback(new ListenableFutureCallback<SendResult<String, Room>>() {

				@Override
				public void onSuccess(SendResult<String, Room> result) {
					// TODO Auto-generated method stub
					successHandler(result);
				}

				@Override
				public void onFailure(Throwable ex) {
					// TODO Auto-generated method stub
					failureHandler(ex);
				}

			});
			System.out.println("Producer-retry-msg:" + room);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "success";

	}
}
