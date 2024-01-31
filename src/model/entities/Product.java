package model.entities;

import model.enums.ProductColors;
import model.enums.ProductsSold;

public class Product {

	private ProductsSold name;
	private ProductColors color;
	private Double price;
	private Long id;

	public Product(ProductsSold name, ProductColors color, Double price, Long id) {
		super();
		this.name = name;
		this.color = color;
		this.price = price;
		this.id = id;
	}

	public ProductsSold getName() {
		return name;
	}

	public void setName(ProductsSold name) {
		this.name = name;
	}

	public ProductColors getColor() {
		return color;
	}

	public void setColor(ProductColors color) {
		this.color = color;
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
		return "Product [name=" + name + ", color=" + color + ", price=" + price + ", id=" + id + "]";
	}
}