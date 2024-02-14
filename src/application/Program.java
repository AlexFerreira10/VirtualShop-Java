package application;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

import model.entities.Client;
import model.entities.Order;
import model.entities.OrderItem;
import model.entities.Product;
import model.entities.Shop;
import model.enums.OrderStatus;
import model.enums.ProductColors;
import model.enums.ProductsSold;
import model.exceptions.DomainException;
import model.services.CreditCardPayment;
import model.services.PixPayment;
import model.services.ProductRepository;

public class Program {

	public static void main(String[] args){
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		ProductRepository repository = new ProductRepository();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Order order = null;
		Shop shop = new Shop();

		try {
			List<Product> products = repository.read();

			System.out.println("Client data: ");
			System.out.print("Enter your name: ");
			String name = sc.nextLine();
			String cpf = UI.readCPF(sc);
			LocalDate birthDate = UI.readBirthDate(sc, dtf);

			Instant purchaseTime = Instant.now();
			order = new Order(1, OrderStatus.PENDING_PAYMENT, purchaseTime, new Client(name, cpf, birthDate));
			shop.addOrders(order);
			UI.clearScreen();
			ProductsSold productsSold = null;
			ProductColors productColors = null;

			int option = 0;
			do {
				UI.mainMenu();
				option = sc.nextInt();
				UI.clearScreen();

				switch(option) {
				case 1://Edit client data
					break;
				case 2://print order
					System.out.println(order);
					UI.defaultConfirmation(sc);
					break;
				case 3://add product
					sc.nextLine();
					productsSold = UI.readProductSold(sc);
					productColors = UI.readProductColors(sc);
					System.out.print("Enter with the quantify: ");
					int quantify = sc.nextInt();

					Set<Product> checkProducts = repository.checkProducts(productsSold, productColors);
					if(quantify > checkProducts.size()) {
						//availabe = disponivel
						System.out.println("We not have this quantify! - Available products: " + checkProducts.size());
					}
					else {
						order.addItem(new OrderItem(quantify,checkProducts.stream().findFirst().orElse(null).getPrice(), checkProducts.stream().findFirst().orElse(null)));
						System.out.println(order.getOrderItem());
					}
					UI.defaultConfirmation(sc);
					break;
				case 4: // Remove order item
				    order.invoice();
				    sc.nextLine(); 
				    boolean target = false;
				    int quantityToRemove;
				    OrderItem orderItem = null;

				    do {
				        ProductsSold productsSold2 = UI.readProductSold(sc);
				        ProductColors productColors2 = UI.readProductColors(sc);
				        System.out.print("Enter the quantity that you want to remove: ");
				        quantityToRemove = sc.nextInt();
				        sc.nextLine(); 

				        orderItem = order.getOrderItem().stream()
				                .filter(x -> x.getProduct().getName().equals(productsSold2) && x.getProduct().getColor().equals(productColors2))
				                .findFirst()
				                .orElse(null);

				        if (orderItem != null && orderItem.getQuantify() >= quantityToRemove) {
				            target = true;
				        } else {
				            System.out.println("Order Item not found!");
				            UI.defaultConfirmation(sc);
				            UI.clearScreen();
				        }
				    } while (!target);

				    System.out.println("Are you sure you want to remove this item from the order? (yes/no)");
				    String confirmation = sc.nextLine();
				    if (confirmation.equalsIgnoreCase("yes")) {
				        if (quantityToRemove < orderItem.getQuantify()) {
				            orderItem.setQuantify(orderItem.getQuantify() - quantityToRemove);
				        } else {
				            order.removeItem(orderItem);
				        }
				        System.out.println("Item removed successfully.");
				    } else {
				        System.out.println("Removal cancelled.");
				    }
				    UI.defaultConfirmation(sc);
				    break;
				case 5://Make a payment
					Boolean confirmPayment;
					System.out.println("What paymnet form? ");
					System.out.println("[1] Pix");
					System.out.println("[2] Credit card");
					System.out.print("Option: ");
					option = sc.nextInt();
					
					System.out.println(order);
					UI.defaultConfirmation(sc);
					
					if(option == 1) {
						System.out.println("Confirm(true/false): ");
						confirmPayment = Boolean.valueOf(sc.next().toUpperCase());
						
						if(confirmPayment) {
							order.formOfPayment(new PixPayment());
							order.getOnlinePaymentService().changeStatusOrder(order);
							order.getOnlinePaymentService().increaseInvoicing(shop, order);
						}
					}
					else {
						System.out.print("What installment amount? ");
						int quantityOfInstallments = sc.nextInt();
						order.formOfPayment(new CreditCardPayment(quantityOfInstallments));
						order.getOnlinePaymentService().changeStatusOrder(order);
						order.getOnlinePaymentService().increaseInvoicing(shop, order);
						UI.defaultConfirmation(sc);
					}
					order.invoice();
					UI.defaultConfirmation(sc);
					break;
				case 6:
					products.forEach(System.out :: println);
					UI.defaultConfirmation(sc);
					break;
				case 7:
					option = 7;
					break;
				}
			UI.clearScreen();
			} while(option != 7);
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
		catch (DomainException e) {
			System.out.println(e.getMessage());
		}
		finally{
			sc.close();
		}
	}
}
