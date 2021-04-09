package com.hardik.flenderson.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hardik.flenderson.entity.AdminSupportTicket;
import com.hardik.flenderson.request.AdminSupportTicketCreationRequest;
import com.hardik.flenderson.service.AdminSupportTicketService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class AdminController {

	private final AdminSupportTicketService adminSupportTicketService;

	@GetMapping("v1/support-tickets")
	public List<AdminSupportTicket> retreiveSupportTicketsHandler() {
		return adminSupportTicketService.retreive();
	}

	@PostMapping("v1/support-ticket")
	public void supportTicketCreationHandler(
			@RequestBody(required = true) final AdminSupportTicketCreationRequest adminSupportTicketCreationRequest) {
		adminSupportTicketService.create(adminSupportTicketCreationRequest);
	}

	@PutMapping("v1/support-ticket/{ticketId}")
	public void closeSupportTicketHandler(@PathVariable(name = "ticketId", required = true) final UUID ticketId) {
		adminSupportTicketService.close(ticketId);
	}

}
