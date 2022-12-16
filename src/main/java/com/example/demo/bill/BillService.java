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
		return repo.findAll(Sort.by("createdAt").descending());
	}
	
	public List<Bill> listBillsByCustomer(int customer) {
		return repo.listBillsByCustomer(customer);
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
	
	// Thống kê
	public Integer statisticByYear(int year) {
		return repo.statisticByYear(year);
	}
	
	public Integer statisticByQuarter(int year, int quarter) {
		int minMonth = (quarter - 1) * 3 + 1;
		int maxMonth = quarter * 3; 
		return repo.statisticByQuarter(year, minMonth, maxMonth);
	}
	
	public Integer statisticByMonth(int year, int month) {
		return repo.statisticByMonth(year, month);
	}
}
