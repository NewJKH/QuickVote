package com.company.quickvote.entity.campaign;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.company.quickvote.entity.company.Company;
import com.company.quickvote.entity.customer.Customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Campaign {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cam_id")
	private long id;

	@Column(nullable = false, name = "cam_title")
	private String title;
	@Column(name = "cam_desc")
	private String description;

	@Column(nullable = false,name = "cam_start_at")
	private LocalDateTime startAt;
	@Column(nullable = false,name = "cam_end_at")
	private LocalDateTime endAt;
	@CreatedDate
	@Column(nullable = false,name = "cam_created_at")
	private LocalDateTime createdAt;
	@LastModifiedDate
	@Column(nullable = false,name = "cam_updated_at")
	private LocalDateTime updatedAt;

	@Setter
	@Enumerated(EnumType.STRING)
	@Column(nullable = false,name = "cam_status")
	private Status status;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ctm_id", nullable = false)
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cp_id", nullable = false)
	private Company company;

	public Campaign(String title, String description, LocalDateTime startDate, LocalDateTime endDate, Status status, Company company) {
		this.title = title;
		this.description = description;
		this.status = status;
		this.startAt = startDate;
		this.endAt = endDate;
		this.company = company;
	}
}
