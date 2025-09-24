package com.company.quickvote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.quickvote.entity.customerstock.CustomerStock;

public interface StockJPARepository extends JpaRepository<CustomerStock, Long> {
}
