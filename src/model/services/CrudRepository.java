package model.services;

import java.util.List;

//Default Repository
public interface CrudRepository<Product,ID> {
	
	//Save a entities in the repository
	public void save(Product product);
	//Delete a entities by your id
	public void delete(Product id);
	//Find a entities by your id
	public Product findById(Product id);
	//Find every entities
	public List<Product> findAll();
}
