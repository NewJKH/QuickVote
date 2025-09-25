package com.company.quickvote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.quickvote.entity.ballot.Ballot;

public interface BallotJPARepository extends JpaRepository<Ballot,Long> {
}
