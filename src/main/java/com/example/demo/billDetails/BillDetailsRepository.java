package com.example.demo.billDetails;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface BillDetailsRepository extends JpaRepository<BillDetails, Long> {
	@Query(value = "SELECT * FROM Bill_details b where b.bill_id = :bill", nativeQuery = true)
	List<BillDetails> listBillDetailsByBill(@Param("bill") int bill);
}
