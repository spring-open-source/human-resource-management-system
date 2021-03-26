package com.hardik.flenderson.service;

import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.hardik.flenderson.entity.AdminSupportTicket;
import com.hardik.flenderson.enums.ExceptionMessage;
import com.hardik.flenderson.exception.InvalidSupportTicketIdException;
import com.hardik.flenderson.mailing.dto.ContactUsConfirmationDto;
import com.hardik.flenderson.mailing.event.ContactUsEvent;
import com.hardik.flenderson.repository.AdminSupportTicketRepository;
import com.hardik.flenderson.request.AdminSupportTicketCreationRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminSupportTicketService {

	private final AdminSupportTicketRepository adminSupportTicketRepository;

	private final ApplicationEventPublisher applicationEventPublisher;

	public void create(AdminSupportTicketCreationRequest adminSupportTicketCreationRequest) {
		final var adminSupportTicket = new AdminSupportTicket();
		adminSupportTicket.setDescription(adminSupportTicketCreationRequest.getDescription());
		adminSupportTicket.setIsActive(true);
		adminSupportTicket.setRaisedByEmail(adminSupportTicketCreationRequest.getRaisedByEmail());
		adminSupportTicket.setRaisedByName(adminSupportTicketCreationRequest.getRaisedByName());
		adminSupportTicket.setRaisedByAccountType(adminSupportTicketCreationRequest.getRaisedByAccountType());
		adminSupportTicket.setTicketIssue(adminSupportTicketCreationRequest.getTicketIssue());
		adminSupportTicketRepository.save(adminSupportTicket);
		applicationEventPublisher.publishEvent(new ContactUsEvent(ContactUsConfirmationDto.builder()
				.email(adminSupportTicketCreationRequest.getRaisedByEmail())
				.message(adminSupportTicket.getDescription()).name(adminSupportTicket.getRaisedByName()).build()));
	}

	public void close(UUID ticketId) {
		final var adminSupportTicket = adminSupportTicketRepository.findById(ticketId).orElseThrow(
				() -> new InvalidSupportTicketIdException(ExceptionMessage.INVALID_SUPPORT_TICKET_ID.getMessage()));
		adminSupportTicket.setIsActive(false);
		adminSupportTicketRepository.save(adminSupportTicket);
	}

}
