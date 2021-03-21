package com.hardik.flenderson.service;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.entity.AdminSupportTicket;
import com.hardik.flenderson.repository.AdminSupportTicketRepository;
import com.hardik.flenderson.request.AdminSupportTicketCreationRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminSupportTicketService {

	private final AdminSupportTicketRepository adminSupportTicketRepository;

	public void create(AdminSupportTicketCreationRequest adminSupportTicketCreationRequest) {
		final var adminSupportTicket = new AdminSupportTicket();
		adminSupportTicket.setDescription(adminSupportTicketCreationRequest.getDescription());
		adminSupportTicket.setIsActive(true);
		adminSupportTicket.setRaisedByEmail(adminSupportTicketCreationRequest.getRaisedByEmail());
		adminSupportTicket.setRaisedByName(adminSupportTicketCreationRequest.getRaisedByName());
		adminSupportTicket.setRaisedByAccountType(adminSupportTicketCreationRequest.getRaisedByAccountType());
		adminSupportTicket.setTicketIssue(adminSupportTicketCreationRequest.getTicketIssue());
		adminSupportTicketRepository.save(adminSupportTicket);
	}

}
