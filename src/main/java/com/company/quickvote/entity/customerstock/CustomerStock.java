package com.company.quickvote.entity.customerstock;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.company.quickvote.entity.company.Company;
import com.company.quickvote.entity.customer.Customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerStock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false, unique = true, name = "ctm_st_id")
	private long id;

	@Column(nullable = false, name = "ctm_st_shares")
	private int shares;

	@CreatedDate
	@Column(nullable = false, updatable = false, name = "ctm_st_created_at")
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(nullable = false, name = "ctm_st_updated_at")
	private LocalDateTime updatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ctm_id", nullable = false)
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cp_id", nullable = false)
	private Company company;

	public CustomerStock(int shares, Customer customer, Company company) {
		this.shares = shares;
		this.customer = customer;
		this.company = company;
	}
}
