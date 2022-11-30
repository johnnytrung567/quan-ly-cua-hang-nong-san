package com.example.demo.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository repo;
	
	public List<Category> listAll() {
		return repo.findAll();
	}
	
	public Category get(long id) {
		return repo.findById(id).get();
	}
}
