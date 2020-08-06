package com.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.order.model.entity.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{
	@Query(value ="update product set order_id =:orderId where id in (:ids)", nativeQuery = true)
	public int updateOrderId(@Param("orderId") int orderId, @Param("ids") List<Long> ids);
}
