package com.example.demo.billDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.example.demo.bill.Bill;
import com.example.demo.product.Product;

@Entity
public class BillDetails {
	private Long id;
	
	private int quantity;
	
	private Product product;
	
	private Bill bill;
	
	public BillDetails() {
		super();
	}

	public BillDetails(Long id, int quantity, Product product, Bill bill) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.product = product;
		this.bill = bill;
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
	@JoinColumn(name = "product_id")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne
	@JoinColumn(name = "bill_id")
	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
}
