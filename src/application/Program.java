package application;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import model.services.ProductRepository;

public class Program {

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		
		ProductRepository repository = new ProductRepository("C:\\Projects\\VirtualShop-Java\\VirtualShop\\data\\Products.CSV");
		List<?> list = repository.read();
		
		list.forEach(System.out::println);
	}

}
