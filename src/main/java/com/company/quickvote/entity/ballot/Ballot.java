package com.company.quickvote.entity.ballot;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
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

}
