package com.hills.kafka.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;

import com.hills.kafka.models.Car;
import com.hills.kafka.repo.CarRepository;

public class CarController {

  @Autowired
  private CarRepository repository;

  private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);

  @KafkaListener(topics = "${kafka.topic.car.request}", containerFactory = "requestReplyListenerContainerFactory")
  @SendTo()
  public Car receive(String vin) {
    LOGGER.info("received request for VIN {} ", vin);
    Car car = repository.getCar(vin);
    LOGGER.info("sending reply {} ", car);
    return car;
  }
}
