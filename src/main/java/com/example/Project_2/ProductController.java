package com.example.Project_2;

import java.util.List;
import java.util.Optional;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
	 
	@Autowired
	ProductService service;
	
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts(){
		List<Product>ltList=service.findAllProducts();
		if(ltList.size()<=0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		else {
			return ResponseEntity.of(Optional.of(ltList));
		}
	}
	
	@GetMapping("/products/name")
	public ResponseEntity<Product> getProductsById(@RequestParam("name") String name) {
		Product pdt=service.getByName(name);
		if(pdt==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		else {
			return ResponseEntity.of(Optional.of(pdt));
		}
	}
	
	@GetMapping("/products/type/{tp}")
	public ResponseEntity<List<Product>> getAllProductByType(@PathVariable("tp") String tp){
		List<Product> lt=service.getAllProductByType(tp);
		if(lt.size()<=0) {
			return ResponseEntity.status(HttpStatusCode.valueOf(405)).build();
		}
		else {
			return ResponseEntity.of(Optional.of(lt));
		}
	}
	
	@GetMapping("/nameandplace")
	public List<Product> getProductByNameAndPlace(@RequestParam("name") String name,@RequestParam("place") String place) {
		 return this.service.getByNameAndPlace(name, place);
	}
	
	@PostMapping("/products")
	public ResponseEntity<Product> addProduct(@RequestBody Product p) {
		Product p1=null;
		try {
			p1=this.service.addProducts(p);
			return ResponseEntity.status(HttpStatus.CREATED).body(p1);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			
		}
	}
	
	@PostMapping("/addAllProducts")
	public void addProduct(@RequestBody List<Product> pt) {
		this.service.addAllProducts(pt);
	}
	
	@PutMapping("/products/{type}")
	public void updateProducts(@RequestBody Product p, @PathVariable("type") String type) {
		service.updateProducts(p,type);
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Void> deleteProducts(@PathVariable("id") int id) {
		try {
			this.service.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping("/products/type/{type}")
	public void deleteAllByType(@PathVariable("type") String type) {
		try {
			service.deleteAllByType(type);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
