package com.company.quickvote.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.company.quickvote.entity.customerstock.CustomerStock;

public interface StockJPARepository extends JpaRepository<CustomerStock, Long> {
	List<CustomerStock> findAllByCustomer_Id(long customerId);

	@Query("SELECT "
		+ "DISTINCT cs.company.id "
		+ "FROM CustomerStock cs "
		+ "WHERE cs.customer.id = :customerId")
	List<Long> findCompanyIdsByCustomerId(@Param("customerId") Long customerId);

	Optional<CustomerStock> findByCustomerIdAndCompanyId(Long id, Long companyId);
}
