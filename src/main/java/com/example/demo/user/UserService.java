package com.example.demo.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.product.Product;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository repo;
	
	public List<User> listAll() {
		return repo.findAll();
	}
	
	public List<User> listUsers(int minRole, int maxRole) {
		return repo.listUsers(minRole, maxRole);
	}
	
	public void save(User user) {
		repo.save(user);
	}
	
//	public User findByID(String email)  {
//		for (User user : listAll()) {
//			if (user.getEmail().equals(email))  {
//				return user;
//			}
//		}
//		return null;
//	}
	
}
