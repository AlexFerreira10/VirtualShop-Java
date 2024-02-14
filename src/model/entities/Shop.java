package model.entities;

import java.util.ArrayList;
import java.util.List;

public class Shop {
	
	private String name;
	private String cnpj;
	private Double invoicing;
	
	private List<Order> orders = new ArrayList<>();

	public Shop() {
		super();
		this.name = "Fashion";
		this.cnpj = "12.345.678/0001-00";
		this.invoicing = 0.0;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCnpj() {
		return cnpj;
	}

	public Double getInvoicing() {
		return invoicing;
	}

	public void setInvoicing(Double invoicing) {
		this.invoicing = invoicing;
	}

	public void addOrders(Order order) {
		orders.add(order);
	}
	
	public void removeOrders(Order order) {
		orders.remove(order);
	}
}
