package com.order.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.order.model.entity.Product;
import com.order.service.ProductService;

@RestController
@RequestMapping("/productService")
public class ProductController {

	@Autowired
	ProductService productService;

	@GetMapping("/getAvailableProducts")
	public ResponseEntity<Object> getAllProducts() {
		Response response = new Response(new Date(), Status.SUCCESS, productService.getAllProducts());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/getItemsById/{productId}")
	public ResponseEntity<Object> getProductById(@PathVariable("productId") Integer productId) {
		Response response = new Response(new Date(), Status.SUCCESS, productService.getProductById(productId));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/processOrder")
	public ResponseEntity<Object> processOrder(@RequestBody Order order) throws CustomException {
		if (productService.processOrder(order)) {
			Response response = new Response(new Date(), Status.SUCCESS, order);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			throw new CustomException("Something Went Wrong");
		}
	}

	@GetMapping("/loadProducts")
	public ResponseEntity<Object> loadProducts() {
		Response response = new Response(new Date(), Status.SUCCESS, productService.loadProducts());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
