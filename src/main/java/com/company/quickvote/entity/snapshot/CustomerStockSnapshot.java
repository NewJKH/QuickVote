package com.company.quickvote.entity.snapshot;

import java.time.LocalDateTime;

import com.company.quickvote.entity.campaign.Campaign;
import com.company.quickvote.entity.customer.Customer;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerStockSnapshot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Campaign campaign;

	@ManyToOne(fetch = FetchType.LAZY)
	private Customer customer;

	private int stockCount;

	private LocalDateTime snapshotAt;

	public CustomerStockSnapshot(Campaign campaign, Customer customer, int shares, LocalDateTime now) {
		this.campaign = campaign;
		this.customer = customer;
		this.stockCount = shares;
		this.snapshotAt = now;
	}
}
