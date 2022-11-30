package com.example.demo.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query(value = "SELECT * FROM User u where u.role_id >= :minRole and u.role_id <= :maxRole", nativeQuery = true)
	List<User> listUsers(int minRole, int maxRole);
	
//	public User findByID(String email);
}
