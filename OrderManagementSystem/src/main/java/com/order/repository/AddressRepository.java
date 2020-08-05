package com.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.order.model.entity.Address;
@Repository
public interface AddressRepository extends JpaRepository<Address,Integer>{

}
