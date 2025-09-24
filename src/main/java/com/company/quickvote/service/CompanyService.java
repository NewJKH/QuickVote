package com.company.quickvote.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.quickvote.dto.response.CompanyResponse;
import com.company.quickvote.entity.company.Company;
import com.company.quickvote.repository.CompanyJPARepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {
	private final CompanyJPARepository companyJPARepository;

	@Transactional(readOnly = true)
	public List<CompanyResponse> findAll(){
		return companyJPARepository.findAll().stream()
			.map(company -> new CompanyResponse(company.getId(),company.getName(),company.getCode()))
			.toList();
	}

	@Transactional(readOnly = true)
	public CompanyResponse findById(long id){
		Company company = companyJPARepository.findById(id)
			.orElseThrow(()-> new IllegalArgumentException(" 일치하는 기업이 존재하지 않습니다. "));

		return new CompanyResponse(
			company.getId(),
			company.getName(),
			company.getCode()
		);
	}
}
