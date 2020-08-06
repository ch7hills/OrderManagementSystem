package com.order.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.order.model.entity.Product;
import com.order.model.entity.ProductsStrore;
@Repository
public interface ProductStroreRepository extends JpaRepository<ProductsStrore,Integer>{
}
