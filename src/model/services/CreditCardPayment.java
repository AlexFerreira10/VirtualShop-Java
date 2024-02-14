package model.services;

import model.entities.Order;
import model.entities.Shop;

public class CreditCardPayment implements OnlinePaymentService {
	
	private static double interest = 0.01;
	private Integer quantityOfInstallments;
	
	public CreditCardPayment(Integer quantityOfInstallments) {
		super();
		this.quantityOfInstallments = quantityOfInstallments;
	}

	@Override
	public void increaseInvoicing(Shop shop, Order order) {
		System.out.println("Total Value: " + String.format("%.2f", calculatorInterest(order)) 
		+ " - Installment Value: " + String.format("%.2f", calculatorInterest(order) / quantityOfInstallments));
		
		shop.setInvoicing(shop.getInvoicing() + calculatorInterest(order));
		order.setFinalValue(calculatorInterest(order));
	}
	
	private double calculatorInterest(Order order) {
		return order.total() * Math.pow(1 + interest, quantityOfInstallments); 
	}
}
