package com.hardik.flenderson.mailing.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.hardik.flenderson.mailing.EmailService;
import com.hardik.flenderson.mailing.event.RemovalFromCompanyEvent;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RemovalFromCompanyListener {

	private final EmailService emailService;

	@EventListener
	public void listenToRemovalFromCompanyEvent(RemovalFromCompanyEvent removalFromCompanyEvent) {

	}

}
