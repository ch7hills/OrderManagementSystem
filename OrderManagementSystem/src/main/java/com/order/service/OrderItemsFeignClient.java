package com.order.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.order.model.Response;
import com.order.model.entity.Order;
import com.order.model.entity.Product;
@FeignClient(name="OrderItemService",url="localhost:7008")
public interface OrderItemsFeignClient {
	
	@GetMapping("/productService/getItemsById/{productId}")
	public Response getProductById(@PathVariable(name= "productId") Integer productId);
	
	@PostMapping("/productService/processOrder")
	public Response processOrder(Order order);
	
	@GetMapping("/productService/getAvailableProducts")
	public Response getProductById();
	
	@GetMapping("/productService/getProductsByOrderId/{orderId}")
	public  Response getProductsByOrderId(@PathVariable("orderId") Integer orderId);
}