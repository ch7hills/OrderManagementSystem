package com.order.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.exceptions.CustomException;
import com.order.model.Response;
import com.order.model.Status;
import com.order.model.entity.Order;
import com.order.service.OrderService;
@RestController
@RequestMapping("/orderService")
public class OrderController {
	@Autowired
	OrderService orderService;

	@GetMapping("/getALLOrders")
	public ResponseEntity<Object> getAllOrders() {
		Response response = new Response(new Date(), Status.SUCCESS, orderService.getAllOrders());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/getOrderById/{orderId}")
	public ResponseEntity getOrderById(@PathVariable("orderId") Integer orderId) {
		Response response = new Response(new Date(), Status.SUCCESS, orderService.getOrderById(orderId));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/processOrder")
	public ResponseEntity<Object> processOrder(@RequestBody Order order) throws CustomException {
		Response response = new Response(new Date(), Status.SUCCESS, orderService.processOrder(order));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/testOrder")
	public ResponseEntity<Object> testOrder() throws CustomException {
		//orderService.testOrder());
		Response response = new Response(new Date(), Status.SUCCESS, orderService.testOrder());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getProductsByOrderId/{orderId}")
	public ResponseEntity getProductsByOrderId(@PathVariable("orderId") Integer orderId) {
		Response response = new Response(new Date(), Status.SUCCESS, orderService.getProductsByOrderId(orderId));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
