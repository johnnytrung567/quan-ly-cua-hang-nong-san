package com.example.demo.billDetails;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BillDetailsService {

	@Autowired
	private BillDetailsRepository repo;
	
	public List<BillDetails> listAll() {
		return repo.findAll();
	}
	
	public List<BillDetails> listBillDetailsByBill(int bill) {
		return repo.listBillDetailsByBill(bill);
	}

	public void save(BillDetails billDetails) {
		repo.save(billDetails);
	}
	
	public BillDetails get(long id) {
		return repo.findById(id).get();
	}
	
	public void delete(long id) {
		repo.deleteById(id);
	}
}
