package com.order.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.order.exceptions.CustomException;
import com.order.exceptions.OrderNotfoundException;
import com.order.model.Response;
import com.order.model.Status;
import com.order.model.entity.Address;
import com.order.model.entity.Order;
import com.order.model.entity.Product;
import com.order.repository.AddressRepository;
import com.order.repository.CustomerRepository;
import com.order.repository.OrderRepository;
import com.order.repository.ProductRepository;

@Service
@Transactional
public class OrderService {
	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private AddressRepository addressRepo;
	@Autowired
	CustomerRepository custRepo;

	@Autowired
	private OrderItemsFeignClient orderItemsFeignClient;

	public static final Logger log = LoggerFactory.getLogger(OrderService.class);

	public List<Order> getAllOrders() {
		return orderRepo.findAll();
	}

	public Order getOrderById(Integer orderId) {
		Optional<Order> orderOpt = orderRepo.findById(orderId);
		log.info("orderId: {},orderOpt:{}", orderId, orderOpt);
		System.out.println("orderId:" + orderId);
		System.out.println("orderOpt:" + orderOpt);
		if (orderOpt.isPresent()) {
			return orderOpt.get();
		} else {
			throw new OrderNotfoundException("Order not found:" + orderId);
		}
	}

	public Order processOrder(Order order) throws CustomException {
		
		if (null == order.getShippingAddress()) {
			throw new CustomException("Shipping Address should not be Null");
		}
		if (order.getItems().isEmpty()) {
			throw new CustomException("Products should not be empty");
		}
		order = orderRepo.save(order);
		int orderId= order.getId();
		Response prdResponse = orderItemsFeignClient.processOrder(order);
		if (!prdResponse.getStatus().equals(Status.SUCCESS)) {
			throw new CustomException("Something went wrong");
		}
		return order;
	}

	public List<Product> getProductsByOrderId(Integer productId) {
		Response prdResponse = orderItemsFeignClient.getProductsByOrderId(productId);
		List<Product> products = new ArrayList<>();
		if (prdResponse.getStatus().equals(Status.SUCCESS)) {
			products = (List<Product>) prdResponse.getData();
		}
		return products;
	}

	public Order testOrder() throws CustomException {
		Order order = new Order();
		order.setCustomerName("Tester");
		order.setCustomerId(1);
		order.setOrderDate(new Date());
		List<Product> orderItems = new ArrayList<>();
		double total = 0;
		long count = productRepo.count();
		for (long i=count + 1; i <= count+10; i++) {
			Product p = new Product();
			p.setCode(i);
			//p.setId(i);
			p.setName("Product" + i);
			p.setQuantity(1);
			p.setCost(10);
			orderItems.add(p);
			total = total + (1 * 10);

		}
		order.setItems(orderItems);
		Address shippingAddress = new Address();
		//shippingAddress.setId(1);
		shippingAddress.setPincode("12345");
		shippingAddress.setStreetName("100 feet");
		shippingAddress.setVillage("Madhapur");
		shippingAddress.setState("Telangana");
		order.setShippingAddress(shippingAddress);
		order.setTotalAmount(total);
		return processOrder(order);
	}
}
