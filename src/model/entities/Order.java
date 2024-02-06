package model.entities;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import model.enums.OrderStatus;

public class Order {
	
	private Integer code;
	private OrderStatus status;
	private Instant purchaseTime;
	
	private Client client;
	
	private List<OrderItem> orderItem = new ArrayList<>();

	public Order(Integer code, OrderStatus status, Instant purchaseTime, Client client) {
		super();
		this.code = code;
		this.status = status;
		this.purchaseTime = purchaseTime;
		this.client = client;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Instant getPurchaseTime() {
		return purchaseTime;
	}

	public void setPurchaseTime(Instant purchaseTime) {
		this.purchaseTime = purchaseTime;
	}

	public OrderStatus getStatus() {
		return status;
	}
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<OrderItem> getOrderItem() {
		return orderItem;
	}

	public void addItem(OrderItem item) {
		orderItem.add(item);
	}
	
	public void removeItem(OrderItem item) {
		orderItem.remove(item);
	}
	
	public double total() {
		return orderItem.stream().mapToDouble(x -> x.subTotal()).sum();
	}

	public void invoice() {
		System.out.println("Order [code=" + code + ", status=" + status + ", purchaseTime=" + purchaseTime + ", client=" + client + "]");
		orderItem.forEach(System.out::println);
		System.out.println("Total value:" + String.format("%.2f", total()));
	}
}
