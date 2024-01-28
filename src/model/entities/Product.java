package model.entities;

import model.enums.ProductsSold;

public class Product {
	
	private ProductsSold name;
	private Double price;
	private Long id;
	
	public Product(ProductsSold name, Double price, Long id) {
		super();
		this.name = name;
		this.price = price;
		this.id = id;
	}

	public ProductsSold getName() {
		return name;
	}

	public void setName(ProductsSold name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", price=" + price + ", id=" + id + "]";
	}
}
