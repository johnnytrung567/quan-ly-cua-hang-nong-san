package com.example.demo.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query(value = "SELECT * FROM User u where u.role_id >= :minRole and u.role_id <= :maxRole", nativeQuery = true)
	List<User> listUsers(@Param("minRole") int minRole, @Param("maxRole") int maxRole);
	
	public User findByEmail(String email);
	
	@Query(value = "SELECT * FROM User u where u.email = :email", nativeQuery = true)
	User findByUsername(@Param("email") String email);
}
