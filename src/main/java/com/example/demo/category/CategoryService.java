package com.example.demo.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository repo;
	
	public List<Category> listAll() {
		return repo.findAll(Sort.by("id").descending());
	}
	
	public Category get(long id) {
		return repo.findById(id).get();
	}
	
	public void save(Category category) {
		repo.save(category);
	}
	
	public void delete(long id) {
		repo.deleteById(id);
	}
}
