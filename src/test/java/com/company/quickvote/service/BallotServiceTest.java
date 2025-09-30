package com.company.quickvote.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.company.quickvote.dto.request.BallotCreateRequest;
import com.company.quickvote.dto.response.BallotCreateResponse;
import com.company.quickvote.entity.ballot.Ballot;
import com.company.quickvote.entity.ballot.VoteChoice;
import com.company.quickvote.entity.ballot.VoteType;
import com.company.quickvote.entity.campaign.Campaign;
import com.company.quickvote.entity.campaign.Status;
import com.company.quickvote.entity.company.Company;
import com.company.quickvote.entity.customer.Customer;
import com.company.quickvote.entity.customerstock.CustomerStock;
import com.company.quickvote.global.exception.BusinessException;
import com.company.quickvote.global.security.auth.Auth;
import com.company.quickvote.global.security.auth.Role;
import com.company.quickvote.repository.BallotJPARepository;
import com.company.quickvote.repository.CampaignJPARepository;
import com.company.quickvote.repository.StockJPARepository;

@ExtendWith(MockitoExtension.class)
class BallotServiceTest {

	@Mock
	BallotJPARepository ballotJPARepository;
	@Mock
	CampaignJPARepository campaignJPARepository;
	@Mock
	StockJPARepository stockJPARepository;
	@InjectMocks
	BallotService ballotService;

	private Auth auth;
	private Campaign campaign;
	private Customer customer;
	private Company company;
	private CustomerStock stock;

	@BeforeEach
	void setUp() {
		customer = new Customer("홍길동", "test@example.com","test123");
		ReflectionTestUtils.setField(customer,"id",1L);

		company = mock(Company.class);
		ReflectionTestUtils.setField(company,"id",1L);

		campaign = Campaign.builder()
			.id(1L)
			.title("테스트 캠페인")
			.company(company)
			.customer(customer)
			.status(Status.OPEN)
			.startAt(LocalDateTime.now().minusDays(1))
			.endAt(LocalDateTime.now().plusDays(1))
			.build();

		stock = new CustomerStock(10, customer, company);

		auth = new Auth(1L, "user@test.com",Role.USER);
	}

	@Test
	void 정상적인_직접투표_성공() {
		// given
		BallotCreateRequest request = new BallotCreateRequest(
			campaign.getId(),
			5,
			VoteType.DIRECT,
			VoteChoice.YES,
			null
		);

		when(campaignJPARepository.findById(campaign.getId())).thenReturn(Optional.of(campaign));
		when(stockJPARepository.findByCustomerIdAndCompanyId(auth.id(), company.getId())).thenReturn(Optional.of(stock));
		when(ballotJPARepository.existsByCustomerIdAndCampaignId(auth.id(), campaign.getId())).thenReturn(false);

		Ballot savedBallot = Ballot.builder()
			.id(1L)
			.shares(5)
			.voteType(VoteType.DIRECT)
			.voteChoice(VoteChoice.YES)
			.customer(customer)
			.campaign(campaign)
			.build();

		when(ballotJPARepository.save(any(Ballot.class))).thenReturn(savedBallot);

		// when
		BallotCreateResponse response = ballotService.save(request, auth);

		// then
		assertThat(response).isNotNull();
		assertThat(response.getBallotId()).isEqualTo(1L);
		verify(ballotJPARepository, times(1)).save(any(Ballot.class));
	}

	@Test
	void 정상적인_위임투표_성공() {
		// given
		BallotCreateRequest request = new BallotCreateRequest(
			campaign.getId(),
			10,
			VoteType.DELEGATE,
			null,
			2L // 위임 대상 ID
		);

		when(campaignJPARepository.findById(campaign.getId())).thenReturn(Optional.of(campaign));
		when(stockJPARepository.findByCustomerIdAndCompanyId(auth.id(), company.getId())).thenReturn(Optional.of(stock));
		when(ballotJPARepository.existsByCustomerIdAndCampaignId(auth.id(), campaign.getId())).thenReturn(false);

		Ballot savedBallot = Ballot.builder()
			.id(2L)
			.shares(10)
			.voteType(VoteType.DELEGATE)
			.delegateToId(2L)
			.customer(customer)
			.campaign(campaign)
			.build();

		when(ballotJPARepository.save(any(Ballot.class))).thenReturn(savedBallot);

		// when
		BallotCreateResponse response = ballotService.save(request, auth);

		// then
		assertThat(response.getBallotId()).isEqualTo(2L);
		verify(ballotJPARepository, times(1)).save(any(Ballot.class));
	}

	@Test
	void 보유주식보다_많이투표하면_실패() {
		// given
		BallotCreateRequest request = new BallotCreateRequest(
			campaign.getId(),
			20, // 보유수량 초과
			VoteType.DIRECT,
			VoteChoice.YES,
			null
		);

		when(campaignJPARepository.findById(campaign.getId())).thenReturn(Optional.of(campaign));
		when(stockJPARepository.findByCustomerIdAndCompanyId(auth.id(), company.getId())).thenReturn(Optional.of(stock));

		// when & then
		assertThatThrownBy(() -> ballotService.save(request, auth))
			.isInstanceOf(BusinessException.class)
			.hasMessageContaining("보유 주식 수를 초과");
	}

	@Test
	void 중복투표시_실패() {
		// given
		BallotCreateRequest request = new BallotCreateRequest(
			campaign.getId(),
			5,
			VoteType.DIRECT,
			VoteChoice.YES,
			null
		);

		when(campaignJPARepository.findById(campaign.getId())).thenReturn(Optional.of(campaign));
		when(stockJPARepository.findByCustomerIdAndCompanyId(auth.id(), company.getId())).thenReturn(Optional.of(stock));
		when(ballotJPARepository.existsByCustomerIdAndCampaignId(auth.id(), campaign.getId())).thenReturn(true);

		// when & then
		assertThatThrownBy(() -> ballotService.save(request, auth))
			.isInstanceOf(BusinessException.class)
			.hasMessageContaining("이미 해당 캠페인에 투표");
	}
}