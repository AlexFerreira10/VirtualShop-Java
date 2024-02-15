package model.services;

import model.entities.Order;
import model.entities.Shop;

public class PixPayment implements OnlinePaymentService {

	@Override
	public void increaseInvoicing(Shop shop, Order order) {
		shop.setInvoicing(shop.getInvoicing() + order.total());
		order.setFinalValue(order.total());
		//Retirar do Repositorio de produtos
	}
	
	@Override
	public void finalValue(Order order) {
		System.out.println("Final Value: " + String.format("%.2f", order.total()));
	}

	@Override
	public String toString() {
		return "PixPayment";
	}	
} 