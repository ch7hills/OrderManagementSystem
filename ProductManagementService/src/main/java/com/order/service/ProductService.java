package com.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.order.exceptions.CustomException;
import com.order.exceptions.ItemNotfoundException;
import com.order.exceptions.OrderNotfoundException;
import com.order.model.entity.Order;
import com.order.model.entity.Product;
import com.order.model.entity.ProductsStrore;
import com.order.repository.OrderRepository;
import com.order.repository.ProductRepository;
import com.order.repository.ProductStroreRepository;

@Service
@Transactional
public class ProductService {
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	ProductStroreRepository ProductStroreRepo;
	
	
	public List<ProductsStrore> getAllProducts(){
		return ProductStroreRepo.findAll();
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
			Optional<ProductsStrore> availableProudctOpt = ProductStroreRepo.findById(prd.getId());
			if(!availableProudctOpt.isPresent()) {
				throw new ItemNotfoundException("Product not found:"+prd.getId());
			}
			if(prd.getQuantity()==null && !(prd.getQuantity() > 0)) {
				throw new CustomException("Invalid qunatity for Product:"+prd.getId());
			}
			if(prd.getCost() ==null &&  !(prd.getCost() > 0)) {
				throw new CustomException("Invalid cost for Product:"+prd.getId());
			}
			ProductsStrore availableProudct = availableProudctOpt.get();
			if(prd.getQuantity()<=availableProudct.getQuantity()) {
				
				synchronized(this){//synchronized block  
					availableProudct.setQuantity(availableProudct.getQuantity()-prd.getQuantity());
					ProductStroreRepo.save(availableProudct);
					total = total+(availableProudct.getCost()*prd.getQuantity());
				}
			}else {
				throw new ItemNotfoundException("Product not available:"+prd.getId());
			}
		}
		order.setTotalAmount(total);
		return true;
	}
	
	public List<ProductsStrore> loadProducts() {
		List<ProductsStrore> products = new ArrayList<>();
		for(int i=1;i<=100;i++) {
			ProductsStrore p= new ProductsStrore();
			//p.setId(i);
			p.setCode(i);
			p.setName("Product"+i);
			p.setCost(i+10);
			p.setQuantity(1000);
			products.add(p);
		}
		return ProductStroreRepo.saveAll(products);
	}

	public  List<Product> getProductsByOrderId(Integer orderId) {
		Optional<Order> orderOpt = orderRepo.findById(orderId);
		if(orderOpt.isPresent()) {
			return orderOpt.get().getItems();
		}else {
			throw new OrderNotfoundException("Order not found:"+orderId);
		}
	}
	
}
