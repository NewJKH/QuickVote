package com.company.quickvote.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.quickvote.entity.snapshot.CustomerStockSnapshot;

public interface StockSnapshotJPARepository extends JpaRepository<CustomerStockSnapshot, Long> {
	Optional<CustomerStockSnapshot> findByCustomerIdAndCampaignId(long customerId, long campaignId);
}
