package model.entities;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
		double total = orderItem.stream().mapToDouble(x -> x.subTotal()).sum();
		this.finalValue = total;
		return total;
	}

	public void invoice() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime purchaseTimeLocal = LocalDateTime.ofInstant(purchaseTime, ZoneId.systemDefault());
	    StringBuilder sb = new StringBuilder();
	    
	    sb.append("--------------------------------------------------- Invoice -------------------------------------------------------------\n");
	    sb.append("| Code: ").append(code).append(" - Purchase Time: ").append(purchaseTimeLocal.format(dtf)).append("\n");
	    sb.append("| Client: ").append(client).append("\n");
	    orderItem.forEach(x -> sb.append("| ").append(x).append("\n"));
	    sb.append("| Final Value: ").append(String.format("%.2f", finalValue)).append("\n");
	    sb.append("-----------------------------------------------------------------------------------------------------------------------\n");
	    System.out.println(sb);
	}
	
	public void formOfPayment(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}

	@Override
	public String toString() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime purchaseTimeLocal = LocalDateTime.ofInstant(purchaseTime, ZoneId.systemDefault());
		
	    StringBuilder sb = new StringBuilder();
	    sb.append("--------------------------------------------------- Order -------------------------------------------------------------\n");
	    sb.append("| Code: ").append(code).append(" - Status: ").append(status).append(" - Purchase Time: ").append(purchaseTimeLocal.format(dtf)).append("\n");
	    sb.append("| Client: ").append(client).append("\n");
	    orderItem.forEach(x -> sb.append("| ").append(x).append("\n"));
	    sb.append("| Total Value: ").append(String.format("%.2f", total())).append("\n");
	    sb.append("-----------------------------------------------------------------------------------------------------------------------\n");
	   
	    return sb.toString();
	}
}
