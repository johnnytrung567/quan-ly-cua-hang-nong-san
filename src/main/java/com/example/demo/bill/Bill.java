package com.example.demo.bill;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

import com.example.demo.user.User;

@Entity
public class Bill {
	private Long id;
	
	@NotEmpty(message = "Vui lòng nhập họ tên người nhận")
	private String receiver;
	
	@NotEmpty(message = "Vui lòng nhập số điện thoại người nhận")
	private String phone;
	
	@NotEmpty(message = "Vui lòng nhập địa chỉ người nhận")
	private String address;
	
	private int total;
	
	private User customer;
	
	private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
	
	public Bill() {
		super();
	}

	public Bill(Long id, @NotEmpty(message = "Vui lòng nhập họ tên người nhận") String receiver,
			@NotEmpty(message = "Vui lòng nhập số điện thoại người nhận") String phone,
			@NotEmpty(message = "Vui lòng nhập địa chỉ người nhận") String address, int total, User customer, Timestamp createdAt) {
		super();
		this.id = id;
		this.receiver = receiver;
		this.phone = phone;
		this.address = address;
		this.total = total;
		this.customer = customer;
		this.createdAt = createdAt;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
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

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@OneToOne
	@JoinColumn(name = "customer_id")
	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

}
