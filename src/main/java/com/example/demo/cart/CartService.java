package com.example.demo.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartService {

	@Autowired
	private CartRepository repo;
	
	public List<Cart> listCartItemsByCustomer(int customer) {
		return repo.listCartItemsByCustomer(customer);
	}
	
	public void save(Cart cart) {
		repo.save(cart);
	}
	
	public Cart get(long id) {
		return repo.findById(id).get();
	}
	
	public void delete(long id) {
		repo.deleteById(id);
	}
}
