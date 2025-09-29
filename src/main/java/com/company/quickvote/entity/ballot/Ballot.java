package com.company.quickvote.entity.ballot;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.company.quickvote.entity.campaign.Campaign;
import com.company.quickvote.entity.customer.Customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(
	name = "ballot",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = {"ctm_id", "cam_id"})
	}
)
public class Ballot {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "blt_id")
	private long id;

	@Column(nullable = false, name = "blt_shares")
	private int shares;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, name = "blt_type")
	private VoteType voteType;

	// type = DIRECT 만 가능
	// 직접 투표했다면 찬성/반대/기권
	@Enumerated(EnumType.STRING)
	@Column(name = "blt_choice")
	private VoteChoice voteChoice;

	// type = DELEGATE 만 가능
	// 위임했다면 누구에게 위임했는가
	@Column(name = "blt_delegated_to")
	private long delegateToId;

	@Column(nullable = false, name = "blt_revoked")
	private boolean revoked = false;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ctm_id", nullable = false)
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cam_id", nullable = false)
	private Campaign campaign;

	public Ballot(int shares, VoteType voteType, VoteChoice voteChoice, long delegateId, Campaign campaign) {
		this.shares = shares;
		this.voteType = voteType;
		this.voteChoice = voteChoice;
		this.delegateToId = delegateId;
		this.campaign = campaign;
	}
}
