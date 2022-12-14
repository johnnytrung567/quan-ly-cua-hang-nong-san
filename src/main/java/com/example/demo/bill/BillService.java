package com.example.demo.bill;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BillService {

	@Autowired
	private BillRepository repo;
	
	public List<Bill> listAll() {
		return repo.findAll(Sort.by("id").descending());
	}

	public Bill save(Bill product) {
		return repo.save(product);
	}
	
	public Bill get(long id) {
		return repo.findById(id).get();
	}
	
	public void delete(long id) {
		repo.deleteById(id);
	}
}
