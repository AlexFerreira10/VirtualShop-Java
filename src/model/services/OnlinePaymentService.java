package model.services;

import model.entities.Order;
import model.entities.Shop;
import model.enums.OrderStatus;

public interface OnlinePaymentService {

	public default void changeStatusOrder(Order order) {
		order.setStatus(OrderStatus.SHIPPED);
	};
	
	public void increaseInvoicing(Shop shop, Order order); 
	
	public void finalValue(Order order);
}
