package com.company.quickvote.entity.customer;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ctm_id")
	private long id;

	@Column(name = "ctm_name")
	private String name;

	@Column(nullable = false,name = "ctm_email")
	private String email;

	@Column(nullable = false,name = "ctm_pwd")
	private String pwd;

	@CreatedDate
	@Column(nullable = false, name = "ctm_created_at")
	private LocalDateTime createAt;

	@LastModifiedDate
	@Column(nullable = false, name = "ctm_updated_at")
	private LocalDateTime updatedAt;

	public Customer(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.pwd = password;
	}
}
