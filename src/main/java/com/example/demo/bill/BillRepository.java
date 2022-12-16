package com.example.demo.bill;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BillRepository extends JpaRepository<Bill, Long> {
	@Query(value = "SELECT * FROM Bill b where b.customer_id = :customer order by created_at desc", nativeQuery = true)
	List<Bill> listBillsByCustomer(@Param("customer") int customer);

	// Thống kê doanh thu theo năm
	@Query(value = "SELECT SUM(total) from bill WHERE YEAR(created_at) = :year", nativeQuery = true)
	Integer statisticByYear(@Param("year") int year);

	// Thống kê doanh thu theo quý
	@Query(value = "SELECT SUM(total) from bill WHERE YEAR(created_at) = :year AND MONTH(created_at) >= :minMonth AND MONTH(created_at) <= :maxMonth", nativeQuery = true)
	Integer statisticByQuarter(@Param("year") int year, @Param("minMonth") int minMonth, @Param("maxMonth") int maxMonth);

	// Thống kê doanh thu theo tháng
	@Query(value = "SELECT SUM(total) from bill WHERE YEAR(created_at) = :year AND MONTH(created_at) = :month", nativeQuery = true)
	Integer statisticByMonth(@Param("year") int year, @Param("month") int month);
}
