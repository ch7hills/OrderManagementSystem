package com.hills.model;

public class Room {
	long id;
	Long number;
	String name;
	String info;
	
	public Room(long number, String name, String info) {
		this.id=number;
		this.number=number;
		this.name=name;
		this.info=info;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	@Override
	public String toString() {
		return "Room [id=" + id + ", number=" + number + ", name=" + name + ", info=" + info + "]";
	}
}
