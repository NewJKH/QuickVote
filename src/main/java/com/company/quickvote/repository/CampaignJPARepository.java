package com.company.quickvote.repository;

import org.springframework.data.repository.CrudRepository;

import com.company.quickvote.entity.campaign.Campaign;

public interface CampaignJPARepository extends CrudRepository<Campaign, Long> {
}
