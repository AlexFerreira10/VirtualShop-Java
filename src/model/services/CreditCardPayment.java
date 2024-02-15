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
		shop.setInvoicing(shop.getInvoicing() + calculatorInterest(order));
		order.setFinalValue(calculatorInterest(order));
	}
	
	public void finalValue(Order order) {
		System.out.println("Final Value: " + String.format("%.2f", calculatorInterest(order)) 
		+ " - Installment Value: " + String.format("%.2f", calculatorInterest(order) / quantityOfInstallments));
	}
	
	private double calculatorInterest(Order order) {
		return order.total() * Math.pow(1 + interest, quantityOfInstallments); 
	}

	@Override
	public String toString() {
		return "CreditCardPayment [quantityOfInstallments=" + quantityOfInstallments + "]";
	}
	
	
}
