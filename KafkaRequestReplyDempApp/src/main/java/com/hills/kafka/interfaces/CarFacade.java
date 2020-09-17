package com.hills.kafka.interfaces;

import java.util.concurrent.CompletableFuture;

import com.hills.kafka.models.Car;

public interface CarFacade {

  Car getCar(String vin);

  CompletableFuture<Car> getCarAsync(String vin);

}