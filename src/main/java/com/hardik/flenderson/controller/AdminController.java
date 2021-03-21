package com.hardik.flenderson.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hardik.flenderson.request.AdminSupportTicketCreationRequest;
import com.hardik.flenderson.service.AdminSupportTicketService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AdminController {
	
	private final AdminSupportTicketService adminSupportTicketService; 

	@PostMapping("v1/support-ticket")
	public void supportTicketCreationHandler(
			@RequestBody(required = true) final AdminSupportTicketCreationRequest adminSupportTicketCreationRequest) {
		adminSupportTicketService.create(adminSupportTicketCreationRequest);
	}

}
