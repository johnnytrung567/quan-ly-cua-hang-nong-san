package com.example.demo.cart;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.example.demo.product.Product;
import com.example.demo.user.User;

@Entity
public class Cart {
	private Long id;
	private int quantity;
	private User customer;
	private Product product;

	public Cart() {
		super();
	}

	public Cart(Long id, int quantity, User user, Product product) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.customer = user;
		this.product = product;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@OneToOne
	@JoinColumn(name = "customer_id")
	public User getUser() {
		return customer;
	}

	public void setUser(User user) {
		this.customer = user;
	}

	@OneToOne
	@JoinColumn(name = "product_id")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
