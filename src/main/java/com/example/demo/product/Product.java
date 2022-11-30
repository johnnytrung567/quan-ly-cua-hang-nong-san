package com.example.demo.product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.example.demo.category.Category;

@Entity
public class Product {
	private Long id;
	private String title;
	private String description;
	private String image;
	private int price;
	private int quantity;
	private Category category;

	public Product() {
		super();
	}

	public Product(Long id, String title, String description, String image, int price, int quantity,
			Category category) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.image = image;
		this.price = price;
		this.quantity = quantity;
		this.category = category;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@OneToOne
	@JoinColumn(name = "category_id")
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
