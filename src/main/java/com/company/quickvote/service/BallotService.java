package com.company.quickvote.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.quickvote.dto.request.BallotCreateRequest;
import com.company.quickvote.dto.response.BallotCreateResponse;
import com.company.quickvote.entity.ballot.Ballot;
import com.company.quickvote.repository.BallotJPARepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BallotService {

	private final BallotJPARepository ballotJPARepository;

	@Transactional
	public BallotCreateResponse save(BallotCreateRequest ballotCreateRequest) {
		Ballot ballot = new Ballot(
			
		);
		return null;
	}

	@Transactional(readOnly = true)
	public void findAllById(){


	}



}
