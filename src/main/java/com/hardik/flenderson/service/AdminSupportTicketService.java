package com.hardik.flenderson.service;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.repository.AdminSupportTicketRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminSupportTicketService {
	
	private final AdminSupportTicketRepository adminSupportTicketRepository;

}
