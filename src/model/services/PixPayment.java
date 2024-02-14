package model.services;

import model.entities.Order;
import model.entities.Shop;
import model.enums.OrderStatus;

public class PixPayment implements OnlinePaymentService {
	
	private static String code;

	@Override
	public void increaseInvoicing(Shop shop, Order order) {
		shop.setInvoicing(shop.getInvoicing() + order.total());
		order.setFinalValue(order.total());
		//Retirar do Repositorio de produtos
	}
} 