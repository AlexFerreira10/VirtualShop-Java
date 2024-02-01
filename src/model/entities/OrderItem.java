package model.entities;

public class OrderItem {
	
	private Integer quantify;
	private Double price;
	
	private Product product;

	public OrderItem(Integer quantify, Double price, Product product) {
		super();
		this.quantify = quantify;
		this.price = price;
		this.product = product;
	}

	public Integer getQuantify() {
		return quantify;
	}

	public void setQuantify(Integer quantify) {
		this.quantify = quantify;
	}

	public Double getPrice() {
		return price;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public double subTotal() {
		return quantify * price;
	}

	@Override
	public String toString() {
		return "OrderItem [quantify=" + quantify + ", price=" + price + ", product=" + product + ", subTotal()="
				+ subTotal() + "]";
	}
	
	
}
