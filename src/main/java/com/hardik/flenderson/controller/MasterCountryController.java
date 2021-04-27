package com.hardik.flenderson.controller;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hardik.flenderson.dto.MasterCountryDto;
import com.hardik.flenderson.service.MasterCountryService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.DELETE,
		RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.OPTIONS, RequestMethod.PATCH, RequestMethod.POST,
		RequestMethod.PUT, RequestMethod.TRACE })
public class MasterCountryController {

	private final MasterCountryService masterCountryService;

	@GetMapping("v1/countries")
	@Cacheable(value = "countries")
	public List<MasterCountryDto> getAllCountries() {
		return masterCountryService.getAllCountries();
	}

}
