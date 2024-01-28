package model.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.entities.Product;
import model.enums.ProductsSold;

public class ProductRepository {
	
	private String path;
	
	public ProductRepository(String path) {
		super();
		this.path = path;
	}

	public List<Product> read() throws IOException {
	    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {      
	    	
	    	List<Product> products = new ArrayList<>();
	        String line = reader.readLine();
	       
	        while (line != null) {
	            String[] fields = line.split(";");
	            ProductsSold name = ProductsSold.valueOf(fields[0]);
	            double price = Double.parseDouble(fields[1]);
	            Long id = Long.parseLong(fields[2]);

	            products.add(new Product(name, price, id));
	            line = reader.readLine();
	        }
	        return products;
	    } catch (IOException e) {
	        throw new IOException("Error reading file: " + e.getMessage());
	    }
	}

}
