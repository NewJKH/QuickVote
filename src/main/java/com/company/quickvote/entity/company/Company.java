package com.company.quickvote.entity.company;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cp_id")
	private long id;

	@Column(name = "cp_name")
	private String name;

	@Column(nullable = false, name = "cp_code")
	private String code;

	@Column(nullable = false, name = "cp_sector")
	private String sector;

	@Column(nullable = false, name = "cp_joined_at")
	private LocalDateTime joinedAt;
}
