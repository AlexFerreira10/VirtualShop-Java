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

			boolean validInput = false;
			String cpf = null;
			do {
				System.out.print("Enter your cpf (only numbers): ");
				cpf = sc.nextLine();

				if (cpf.length() != 11) {
					System.out.println("Invalid CPF: CPF must have 11 digits");
				} else {
					validInput = true;
				}
			} while (!validInput);

			validInput = false;
			LocalDate birthDate = null;

			do {
				System.out.print("Enter your birth date (dd/MM/yyyy): ");
				String birthDateInput = sc.nextLine();

				try {
					birthDate = LocalDate.parse(birthDateInput, dtf);
					validInput = true;
				} catch (DateTimeParseException e) {
					System.out.println("Invalid date format. Please enter the date in the format dd/MM/yyyy.");
				}
			} while (!validInput);

			Instant purchaseTime = Instant.now();
			order = new Order(1, OrderStatus.PENDING_PAYMENT, purchaseTime, new Client(name, cpf, birthDate));
			boolean confirmOrder = false;
			int option = 0;
			do {

				UI.mainMenu();
				option = sc.nextInt();
				switch(option) {
				case 1://Edit client data

					break;
				case 2://print order
					order.invoice();
					break;
				case 3://add product
					UI.orderData();
					sc.nextLine();
					ProductsSold nameProduct = ProductsSold.valueOf(sc.nextLine().toUpperCase());
					System.out.print("Enter with the color: ");
					ProductColors color = ProductColors.valueOf(sc.nextLine().toUpperCase());
					System.out.print("Enter with the quantify: ");
					int quantify = sc.nextInt();

					Set<Product> checkProducts = repository.checkProducts(nameProduct, color);
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
					UI.orderData();
					sc.nextLine();
					nameProduct = ProductsSold.valueOf(sc.nextLine().toUpperCase());
					System.out.print("Enter with the color: ");
					color = ProductColors.valueOf(sc.nextLine().toUpperCase());
					System.out.print("Enter with the quantity that you want to remove: ");
					int quantityToRemove = sc.nextInt();

					OrderItem orderItem = order.getOrderItem().stream()
							.filter(x -> x.getProduct().getName().equals(nameProduct) && x.getProduct().getColor().equals(color))
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
					option = 7;
					break;
				}
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
