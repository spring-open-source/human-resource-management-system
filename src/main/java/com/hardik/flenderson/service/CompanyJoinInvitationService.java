package com.hardik.flenderson.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.hardik.flenderson.entity.CompanyJoinInvitation;
import com.hardik.flenderson.enums.ExceptionMessage;
import com.hardik.flenderson.exception.CompanyJoinInvitationAlreadySentException;
import com.hardik.flenderson.exception.InvalidManagerIdException;
import com.hardik.flenderson.mailing.dto.CompanyJoinInvitationDto;
import com.hardik.flenderson.mailing.event.CompanyJoiningInvitationEvent;
import com.hardik.flenderson.repository.CompanyJoinInvitationRepository;
import com.hardik.flenderson.repository.ManagerRepository;
import com.hardik.flenderson.request.CompanyJoinInvitationCreationRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompanyJoinInvitationService {

	private final CompanyJoinInvitationRepository companyJoinInvitationRepository;

	private final ManagerRepository managerRepository;

	private final ApplicationEventPublisher applicationEventPublisher;

	public void sendInvite(CompanyJoinInvitationCreationRequest companyJoinInvitationCreationRequest, UUID managerId) {
		final var manager = managerRepository.findById(managerId)
				.orElseThrow(() -> new InvalidManagerIdException(ExceptionMessage.INVALID_MANAGER_ID.getMessage()));
		final var company = manager.getCompany();
		if (companyJoinInvitationRepository
				.existsByEmailIdAndCompanyId(companyJoinInvitationCreationRequest.getEmailId(), company.getId()))
			throw new CompanyJoinInvitationAlreadySentException(ExceptionMessage.COMPANY_JOIN_INVITATION_ALREADY_SENT
					.getMessage().replace("--email--", companyJoinInvitationCreationRequest.getEmailId()));

		final var companyJoinInvitation = new CompanyJoinInvitation();
		companyJoinInvitation.setCompanyId(company.getId());
		companyJoinInvitation.setEmailId(companyJoinInvitationCreationRequest.getEmailId());
		companyJoinInvitation.setName(companyJoinInvitationCreationRequest.getName());
		companyJoinInvitation.setSentAt(LocalDateTime.now());
		companyJoinInvitationRepository.save(companyJoinInvitation);
		applicationEventPublisher.publishEvent(new CompanyJoiningInvitationEvent(
				CompanyJoinInvitationDto.builder().companyCode(company.getCompanyCode()).companyName(company.getName())
						.email(companyJoinInvitationCreationRequest.getEmailId())
						.employeeName(companyJoinInvitationCreationRequest.getName())
						.managerName(manager.getFirstName() + " " + manager.getLastName()).build()));
	}

}
