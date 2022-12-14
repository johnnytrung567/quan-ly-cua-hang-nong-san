package com.example.demo.billDetail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BillDetailService {

	@Autowired
	private BillDetailRepository repo;
	
	public List<BillDetail> listAll() {
		return repo.findAll();
	}

	public void save(BillDetail product) {
		repo.save(product);
	}
	
	public BillDetail get(long id) {
		return repo.findById(id).get();
	}
	
	public void delete(long id) {
		repo.deleteById(id);
	}
}
