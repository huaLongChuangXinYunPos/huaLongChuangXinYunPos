package com.example.hlcloundposproject.entity;

import java.io.Serializable;

public final class Consumer implements Serializable{
	
	private String name;
	private String phone;
	private String address;
	
	private String sex;
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Consumer [name=" + name + ", phone=" + phone + ", address="
				+ address + ", sex=" + sex + "]";
	}
	
}
