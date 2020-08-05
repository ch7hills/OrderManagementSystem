package com.order.model.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name="orderdata")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Integer customerId;
	private String customerName;
	private Date orderDate;
	@OneToOne
	private Address shippingAddress;
	/*
	 * @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE,
	 * CascadeType.REFRESH })
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
	private List<Product> items;
	private Double totalAmount;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Product> getItems() {
		return items;
	}

	public void setItems(List<Product> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customerId=" + customerId + ", customerName=" + customerName + ", orderDate="
				+ orderDate + ", shippingAddress=" + shippingAddress + ", items=" + items + ", totalAmount="
				+ totalAmount + "]";
	}

	
}
