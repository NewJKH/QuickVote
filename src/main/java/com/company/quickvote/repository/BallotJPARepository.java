package com.company.quickvote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.quickvote.dto.response.BallotResponse;
import com.company.quickvote.entity.ballot.Ballot;

public interface BallotJPARepository extends JpaRepository<Ballot,Long> {
	boolean existsByCustomerIdAndCampaignId(Long id, Long campaignId);

	List<BallotResponse> findByCustomer_Id(long customerId);
}
