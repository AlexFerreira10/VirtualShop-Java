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
			products.forEach(System.out :: println);
			
			System.out.println("Client data: ");
			System.out.print("Enter your name: ");
			String name = sc.nextLine();
			System.out.print("Enter your cpf: ");
			long cpf = sc.nextLong();
			System.out.print("Enter your birth date (dd/MM/yyyy): ");
			sc.nextLine();
			LocalDate birthDate = LocalDate.parse(sc.nextLine(), dtf);
			Client client = new Client(name, cpf, birthDate);
			
			boolean confirmOrder = false;
			int option = 0;
			do {
				
				UI.mainMenu();
				option = sc.nextInt();
				switch(option) {
				case 1:
					//Edit client data
					break;
				case 2:
					//print Order
					break;
				case 3:
					UI.orderData();
					sc.nextLine();
					ProductsSold nameProduct = ProductsSold.valueOf(sc.nextLine().toUpperCase());
					System.out.print("Enter with the color: ");
					ProductColors color = ProductColors.valueOf(sc.nextLine().toUpperCase());
					System.out.print("Enter with the quantify: ");
					int quantify = sc.nextInt();
					Instant purchaseTime = Instant.now();
					
					Set<Product> checkProducts = repository.checkProducts(nameProduct, color);
					if(quantify > checkProducts.size()) {
						System.out.println("We not have this quantify! - Available products: " + checkProducts.size());
						products.forEach(System.out :: println);
					}
					else {
						order = new Order(1, OrderStatus.PENDING_PAYMENT, purchaseTime, client);
						order.addItem(new OrderItem(quantify,checkProducts.stream().findFirst().orElse(null).getPrice(), checkProducts.stream().findFirst().orElse(null)));
					}
					
					order.getOrderItem().forEach(System.out :: println);
					break;
				case 4:
					//Remove order item
					break;
				case 5:
					//Make a pament
					break;
				case 6:
					//print a product
					break;
				}
			} while(confirmOrder || option == 7);
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
