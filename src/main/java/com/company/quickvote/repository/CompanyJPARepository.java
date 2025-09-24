package com.company.quickvote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.quickvote.entity.company.Company;

public interface CompanyJPARepository extends JpaRepository<Company,Long>
{
}
