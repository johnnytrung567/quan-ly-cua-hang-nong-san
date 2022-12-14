package com.example.demo.cart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface CartRepository extends JpaRepository<Cart, Long> {
	@Query(value = "SELECT * FROM Cart c where c.customer_id = :customer", nativeQuery = true)
	List<Cart> listCartItemsByCustomer(@Param("customer") int customer);
}
