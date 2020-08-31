package com.hills.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hills.model.Room;

@RestController
public class ProducerController {
	@Value("${topic}")
	private String topic;

	@Autowired
	KafkaTemplate<String, String> template;

	@GetMapping("/send")
	public String sendData() {
		Map<String, String> input = new HashMap<>();
		for (int i = 1; i < 10; i++) {
			try {
				ListenableFuture<SendResult<String, String>> result = template.send(topic, "key", "Number->" + i);
				result.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

					@Override
					public void onSuccess(SendResult<String, String> result) {
						// TODO Auto-generated method stub
						successHandler(result);
					}

					@Override
					public void onFailure(Throwable ex) {
						// TODO Auto-generated method stub
						failureHandler(ex);
					}

				});
				System.out.println(i);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return "success";
	}

	public void successHandler(SendResult<String, String> result) {
		System.out.println("Success Records: " + result.getProducerRecord());
		System.out.println("Get Records: " + result.toString());
	}

	public void failureHandler(Throwable ex) {
		System.out.println("Exception occoure: " + ex.getMessage());
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateRecord(@RequestBody Room room) {
		if (room.getId() != 0) {
			return new ResponseEntity<>("Please enter valid id", HttpStatus.BAD_REQUEST);
		}
		try {
			ListenableFuture<SendResult<String, String>> result = template.send(topic, "room", "Room->" + room.toString());
			result.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

				@Override
				public void onSuccess(SendResult<String, String> result) {
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
}
