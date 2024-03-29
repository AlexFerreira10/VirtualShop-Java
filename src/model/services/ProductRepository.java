package model.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import application.UI;
import model.entities.Product;
import model.enums.ProductColors;
import model.enums.ProductsSold;
import model.exceptions.DomainException;

public class ProductRepository {

	private List<Product> products = new ArrayList<>();
	private String path;

	public ProductRepository() {
		super();
		this.path = "C:\\Projects\\VirtualShop_Java\\VirtualShop-Java\\data\\Products.CSV";
	}

	public List<Product> read() throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			products.clear();

			String line = reader.readLine();

			while (line != null) {
				String[] fields = line.split(";");
				ProductsSold name = ProductsSold.valueOf(fields[0]);
				ProductColors color = ProductColors.valueOf(fields[1]);
				double price = Double.parseDouble(fields[2]);
				Long id = Long.parseLong(fields[3]);

				products.add(new Product(name, color, price, id));
				line = reader.readLine();
			}
			return products;
		} catch (IOException e) {
			throw new IOException("Error reading file: " + e.getMessage());
		}
	}

	public void save() throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, false))) {
			// Use the for each interface would generate other exception
			// writer.write(x.getName().toString() + ";" + x.getColor().toString() + ";" +
			// x.getPrice().toString() + ";" + x.getId() + "\n");
			for (Product product : products) {
				writer.write(product.getName().toString() + ";" + product.getColor().toString() + ";"
						+ product.getPrice() + ";" + product.getId() + "\n");
			}
		} catch (IOException e) {
			throw new IOException("Error writing file: " + e.getMessage());
		}
	}

	/*
	 * public Set<Product> checkProducts(ProductsSold name, ProductColors color)
	 * throws DomainException { Set<Product> set = products.stream() .filter(x ->
	 * x.getName().equals(name) && x.getColor().equals(color))
	 * .collect(Collectors.toSet()); if(set.size() == 0) { throw new
	 * DomainException("Out of stock products!"); }
	 * 
	 * return set; }
	 */
	public Set<Product> checkProducts(Scanner sc) {
	    Set<Product> set;
	    do {
	        sc.nextLine();
	        ProductsSold productsSold = UI.readProductSold(sc);
	        ProductColors productColors = UI.readProductColors(sc);

	        set = products.stream()
	                      .filter(x -> x.getName().equals(productsSold) && x.getColor().equals(productColors))
	                      .collect(Collectors.toSet());
	        
	        if(set.isEmpty()) {
	            System.out.println("Out of stock products!");
	            UI.defaultConfirmation(sc);
	            UI.clearScreen();
	        }

	    } while (set.isEmpty());

	    return set;
	}

	public Product findByID(long id) throws DomainException {
		return products.stream().filter(x -> x.getId() == id).findFirst()
				.orElseThrow(() -> new DomainException("No product found with ID: " + id));
	}

	public List<Product> findAll() {
		return products;
	}
}

