package com.order.exceptions;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.order.model.Response;
import com.order.model.Status;

@ControllerAdvice
public class ExceptionController {
	
	public static final Logger log= LoggerFactory.getLogger(ExceptionController.class);
	
	@ExceptionHandler(value = ItemNotfoundException.class)
	public ResponseEntity<Object> exception(ItemNotfoundException exception) {
		log.error("ItemNotfoundException Exception :{}",exception);
		Response response = new Response(new Date(), Status.FAILED, "Item not found");
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = OrderNotfoundException.class)
	public ResponseEntity<Object> exception(OrderNotfoundException exception) {
		log.error("OrderNotfoundException Exception :{}",exception);
		Response response = new Response(new Date(), Status.FAILED, "Order not found");
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = CustomException.class)
	public ResponseEntity<Object> exception(CustomException exception) {
		log.error("CustomException Exception :{}",exception);
		Response response = new Response(new Date(), Status.FAILED, "Something went wrong");
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
