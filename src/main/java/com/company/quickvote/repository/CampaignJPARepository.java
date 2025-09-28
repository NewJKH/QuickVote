package com.company.quickvote.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.company.quickvote.entity.campaign.Campaign;
import com.company.quickvote.entity.campaign.Status;

public interface CampaignJPARepository extends CrudRepository<Campaign, Long> {
	List<Campaign> findByCompanyIdInAndStatus(List<Long> companyIds, Status status);
}
