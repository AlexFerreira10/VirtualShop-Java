package model.entities;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import model.enums.OrderStatus;
import model.services.OnlinePaymentService;

public class Order {
	
	private Integer code;
	private OrderStatus status;
	private Instant purchaseTime;
	
	private Client client;
	
	private List<OrderItem> orderItem = new ArrayList<>();
	private OnlinePaymentService onlinePaymentService;
	
	private Double finalValue;
	
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

	public Instant getPurchaseTime() {
		return purchaseTime;
	}

	public void setPurchaseTime(Instant purchaseTime) {
		this.purchaseTime = purchaseTime;
	}

	public OrderStatus getStatus() {
		return status;
	}
	
	public void setStatus(OrderStatus status) {
		this.status = status;
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

	public void setFinalValue(Double finalValue) {
		this.finalValue = finalValue;
	}

	public void addItem(OrderItem item) {
		orderItem.add(item);
	}
	
	public void removeItem(OrderItem item) {
		orderItem.remove(item);
	}
	
	public OnlinePaymentService getOnlinePaymentService() {
		return onlinePaymentService;
	}

	public double total() {
		return orderItem.stream().mapToDouble(x -> x.subTotal()).sum();
	}

	public void invoice() {
		System.out.println("--------- Invoice -----------");
		System.out.println("Order [code=" + code + ", status=" + status + ", purchaseTime=" + purchaseTime + ", client=" + client
				+ ", orderItem=" + orderItem + ", onlinePaymentService=" + onlinePaymentService + "]");
		orderItem.forEach(System.out::println);
		System.out.println("Total value:" + String.format("%.2f", finalValue));
	}
	
	public void formOfPayment(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}

	@Override
	public String toString() {
		return "Order [code=" + code + ", status=" + status + ", purchaseTime=" + purchaseTime + ", client=" + client
				+ ", orderItem=" + orderItem + ", total()=" + total() + "]";
	}
	
	
}
