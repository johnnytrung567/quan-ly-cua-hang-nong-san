package com.example.demo.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ProductRepository extends JpaRepository<Product, Long> {
	@Query(value = "SELECT * FROM Product p where p.category_id = :category", nativeQuery = true)
	List<Product> listProductsByCat(@Param("category") int category);
}
