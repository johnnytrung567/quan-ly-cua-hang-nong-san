package com.example.demo.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleService {

	@Autowired
	private RoleRepository repo;
	
	public List<Role> listAll() {
		return repo.findAll();
	}
	
	public Role get(long id) {
		return repo.findById(id).get();
	}
}
