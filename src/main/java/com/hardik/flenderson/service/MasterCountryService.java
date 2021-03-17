package com.hardik.flenderson.service;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.repository.MasterCountryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MasterCountryService {
	
	private final MasterCountryRepository masterCountryRepository;

}
