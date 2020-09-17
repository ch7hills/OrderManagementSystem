package com.hills.kafka.serviceImpl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hills.kafka.interfaces.CarFacade;
import com.hills.kafka.interfaces.CompletableFutureReplyingKafkaOperations;
import com.hills.kafka.models.Car;

@Component
public class CarFacadeImpl implements CarFacade {

	@Autowired
	private CompletableFutureReplyingKafkaOperations<String, String, Car> requestReplyKafkaTemplate;

	@Value("${kafka.topic.car.request}")
	private String requestTopic;

	@Override
	public Car getCar(String vin) {
		try {
			return getCarAsync(vin).get();
		} catch (InterruptedException | ExecutionException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException("Failed to get Car", e);
		}
	}

	@Override
	public CompletableFuture<Car> getCarAsync(String vin) {
		return requestReplyKafkaTemplate.requestReply(requestTopic, vin);
	}

}
