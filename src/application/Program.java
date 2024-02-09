package application;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.time.format.DateTimeParseException;


import model.entities.Client;
import model.entities.Order;
import model.entities.OrderItem;
import model.entities.Product;
import model.enums.OrderStatus;
import model.enums.ProductColors;
import model.enums.ProductsSold;
import model.exceptions.DomainException;
import model.services.ProductRepository;

public class Program {

	public static void main(String[] args){
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		ProductRepository repository = new ProductRepository();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Order order = null;

		try {
			List<Product> products = repository.read();

			System.out.println("Client data: ");
			System.out.print("Enter your name: ");
			String name = sc.nextLine();
			String cpf = UI.readCPF(sc);
			LocalDate birthDate = UI.readBirthDate(sc, dtf);

			Instant purchaseTime = Instant.now();
			order = new Order(1, OrderStatus.PENDING_PAYMENT, purchaseTime, new Client(name, cpf, birthDate));
			UI.clearScreen();

			int option = 0;
			do {
				UI.mainMenu();
				option = sc.nextInt();
				System.out.println("Enter ok for continue... ");
				sc.next();
				UI.clearScreen();

				switch(option) {
				case 1://Edit client data
					break;
				case 2://print order
					order.invoice();
					System.out.println("Enter ok for continue... ");
					sc.next();
					break;
				case 3://add product
					sc.nextLine();
					ProductsSold productsSold = UI.readProductSold(sc);
					ProductColors productColors = UI.readProductColors(sc);
					System.out.print("Enter with the quantify: ");
					int quantify = sc.nextInt();

					Set<Product> checkProducts = repository.checkProducts(productsSold, productColors);
					if(quantify > checkProducts.size()) {
						System.out.println("We not have this quantify! - Available products: " + checkProducts.size());
						products.forEach(System.out :: println);
					}
					else {
						order.addItem(new OrderItem(quantify,checkProducts.stream().findFirst().orElse(null).getPrice(), checkProducts.stream().findFirst().orElse(null)));
					}
					System.out.println(order.getOrderItem());
					break;
				case 4://remove order item
					order.invoice();
					sc.nextLine();
					productsSold = UI.readProductSold(sc);
					productColors = UI.readProductColors(sc);
					System.out.print("Enter with the quantity that you want to remove: ");
					int quantityToRemove = sc.nextInt();

					OrderItem orderItem = order.getOrderItem().stream()
							.filter(x -> x.getProduct().getName().equals(productsSold) && x.getProduct().getColor().equals(productColors))
							.findFirst()
							.orElseThrow(() -> new DomainException("Error: Order item not found"));

					if (quantityToRemove < orderItem.getQuantify()) {
						orderItem.setQuantify(orderItem.getQuantify() - quantityToRemove);
					} else {
						order.removeItem(orderItem);
					}
					break;
				case 5:
					//Make a payment
					break;
				case 6:
					products.forEach(System.out :: println);
					System.out.println("Enter ok for continue... ");
					sc.next();
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
