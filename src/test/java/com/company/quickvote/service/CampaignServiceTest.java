package com.company.quickvote.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.company.quickvote.dto.request.CampaignCreateRequest;
import com.company.quickvote.dto.response.CampaignResponse;
import com.company.quickvote.entity.campaign.Campaign;
import com.company.quickvote.entity.company.Company;
import com.company.quickvote.entity.customer.Customer;
import com.company.quickvote.global.security.auth.Auth;
import com.company.quickvote.global.security.auth.Role;
import com.company.quickvote.repository.CampaignJPARepository;
import com.company.quickvote.repository.CompanyJPARepository;
import com.company.quickvote.repository.CustomerJPARepository;

@ExtendWith(MockitoExtension.class)
class CampaignServiceTest {

	@Mock
	CampaignJPARepository campaignJPARepository;

	@Mock
	CompanyJPARepository companyJPARepository;

	@Mock
	CustomerJPARepository customerJPARepository;

	@InjectMocks
	CampaignService campaignService;

	private Customer customer;
	private Company company;
	private Auth auth;

	@BeforeEach
	public void setup(){
		customer = new Customer(1L,"이름","emial@email.com","pwd",LocalDateTime.now(),LocalDateTime.now());

		company = new Company(1L,"테스트용 기업","FF3333","A", LocalDateTime.now());

		auth = new Auth(1L,"이름", Role.USER);
	}

	@Test
	void 정상적인_캠페인_생성_성공() {
		//given
		CampaignCreateRequest request = new CampaignCreateRequest(1L,"제목","설명",LocalDateTime.now(),LocalDateTime.now());

		when(customerJPARepository.findById(customer.getId())).thenReturn(Optional.of(customer));
		when(companyJPARepository.findById(company.getId())).thenReturn(Optional.of(company));

		Campaign campaign = Campaign.builder()
			.title("제목")
			.description("설명")
			.startAt(LocalDateTime.now())
			.endAt(LocalDateTime.now())
			.build();
		ReflectionTestUtils.setField(campaign, "id", 1L);
		when(campaignJPARepository.save(any(Campaign.class)))
			.thenAnswer(invocation -> {
				Campaign arg = invocation.getArgument(0);
				ReflectionTestUtils.setField(arg, "id", 1L);
				return arg;
			});

		//when
		CampaignResponse response = campaignService.save(auth,request);

		//then
		assertThat(response.getCampaignId()).isEqualTo(1L);
		verify(campaignJPARepository, times(1)).save(any(Campaign.class));
	}

}