package com.example.Project_2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Project_2.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
      Product findByName(String name);
      List<Product> findAllByType(String type);
      void deleteAllByType(String type);
      List<Product> findByNameAndPlace(String name,String place);
}
