package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import model.entities.Product;
import model.exceptions.DomainException;
import model.services.ProductRepository;

public class Program {

	public static void main(String[] args){
		Locale.setDefault(Locale.US);
		try {
			ProductRepository repository = new ProductRepository();
			List<Product> list = new ArrayList<>();
			list = repository.read();
			
			list.forEach(System.out::println);
			
			System.out.println();
			
			Product product = repository.findByID(27);
			System.out.println(product);
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		} catch (DomainException e) {
			System.out.println(e.getMessage());
		}
	}
}
