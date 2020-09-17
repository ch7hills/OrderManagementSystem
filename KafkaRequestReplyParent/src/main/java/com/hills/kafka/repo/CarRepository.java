package com.hills.kafka.repo;

import com.hills.kafka.models.Car;

public interface CarRepository {

  Car getCar(String vin);

}
