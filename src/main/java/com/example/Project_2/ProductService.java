package com.example.Project_2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
   
	@Autowired
	ProductRepository repository;
	
	
	public List<Product> findAllProducts() {
		return repository.findAll();
	}


	public Product getByName(String name) {
		
		return repository.findByName(name);
	}
	
	public List<Product> getAllProductByType(String type){
		return repository.findAllByType(type);
	}
	

	public Product addProducts(Product p) {
		// TODO Auto-generated method stub
		Product product=repository.save(p);
		return product;
	}
	
	


	public void deleteById(int id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
		
	}


	public void updateProducts(Product p, String type) {
		// TODO Auto-generated method stub
			List<Product> products=repository.findAllByType(type);
			for(Product p1:products) {
				p1.setType(p.getType());
				repository.save(p1);
			}
			
	}


	public void deleteAllByType(String type) {
		List<Product>list=repository.findAllByType(type);
		for(Product p:list) {
			repository.delete(p);
		}
		
	}


	public void addAllProducts(List<Product> pt) {
		// TODO Auto-generated method stub
		repository.saveAll(pt);
	}


	public List<Product> getByNameAndPlace(String name, String place) {
		// TODO Auto-generated method stub
		return repository.findByNameAndPlace(name, place);
		
	}
}
