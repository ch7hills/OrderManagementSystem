package com.hills.kafka.repo;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.hills.kafka.models.Car;

@Repository
public class CarRepositoryImpl implements CarRepository {

  Map<String, Car> cars = new HashMap<>();

  @PostConstruct
  private void initCars() {
    cars.put("12345678901234567", new Car("12345678901234567", "ABC123"));
    cars.put("76543210987654321", new Car("76543210987654321", "ZYX987"));
  }

  @Override
  public Car getCar(String vin) {
    return cars.get(vin);
  }

}
