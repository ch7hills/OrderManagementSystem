package com.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order.model.entity.Address;

public interface AddressRepository extends JpaRepository<Address,Integer>{

}
