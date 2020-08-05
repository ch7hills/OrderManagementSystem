package com.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.order.exceptions.CustomException;
import com.order.exceptions.ItemNotfoundException;
import com.order.model.entity.Order;
import com.order.model.entity.Product;
import com.order.repository.ProductRepository;

@Service
@Transactional
public class ProductService {
	@Autowired
	ProductRepository productRepo;
	/*
	@Autowired
	OrderRepository orderRepo;
	*/
	
	public List<Product> getAllProducts(){
		return productRepo.findAll();
	}
	
	public Product getProductById(Integer productId){
		Optional<Product> productOpt = productRepo.findById(productId);
		if(productOpt.isPresent()) {
			return productOpt.get();
		}else {
			throw new ItemNotfoundException("Product not found:"+productId);
		}
	}
	/*
	public List<Product> getByOrderId(Integer orderId) {
		Optional<Order> order = orderRepo.findById(orderId);
		if(order.isPresent()) {
			return order.get().getOrderItems();
		}else {
			throw new OrderNotfoundException("Order not found:"+orderId);
		}
	}*/
	
	public Boolean processOrder(Order order) throws CustomException{
		double total=0;
		for(Product prd:order.getItems()) {
			Optional<Product> availableProudctOpt = productRepo.findById(prd.getId());
			if(!availableProudctOpt.isPresent()) {
				throw new ItemNotfoundException("Product not found:"+prd.getId());
			}
			if(prd.getQuantity()==null && !(prd.getQuantity() > 0)) {
				throw new CustomException("Invalid qunatity for Product:"+prd.getId());
			}
			if(prd.getCost() ==null &&  !(prd.getCost() > 0)) {
				throw new CustomException("Invalid cost for Product:"+prd.getId());
			}
			Product availableProudct = availableProudctOpt.get();
			if(prd.getQuantity()<=availableProudct.getQuantity()) {
				
				synchronized(this){//synchronized block  
					availableProudct.setQuantity(availableProudct.getQuantity()-prd.getQuantity());
					productRepo.save(availableProudct);
					total = total+availableProudct.getCost();
				}
			}else {
				throw new ItemNotfoundException("Product not available:"+prd.getId());
			}
		}
		order.setTotalAmount(total);
		return true;
	}
	
	public List<Product> loadProducts() {
		List<Product> products = new ArrayList<>();
		for(int i=1;i<=100;i++) {
			Product p= new Product();
			//p.setId(i);
			p.setCode(i);
			p.setName("Product"+i);
			p.setCost(i+10);
			p.setQuantity(1000);
			products.add(p);
		}
		return productRepo.saveAll(products);
	}
	
}
