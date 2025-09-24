package com.company.quickvote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.quickvote.entity.customerstock.CustomerStock;

public interface StockJPARepository extends JpaRepository<CustomerStock, Long> {
	List<CustomerStock> findAllByCustomer_Id(long customerId);
}
