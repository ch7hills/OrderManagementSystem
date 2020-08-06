package com.order.model.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	long code;
	String name;
	Integer quantity;
	Integer cost;
	@JsonBackReference
	//@ManyToOne
	@ManyToOne(targetEntity = Order.class)//(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
	Order order;

	public long getId() {
		return id;
	}

	public void setId(long i) {
		this.id = i;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long i) {
		this.code = i;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", code=" + code + ", name=" + name + ", quantity=" + quantity + ", cost=" + cost
				+ ", order=" + order + "]";
	}
	
}
