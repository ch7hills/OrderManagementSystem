package com.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.order.model.entity.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{

}
