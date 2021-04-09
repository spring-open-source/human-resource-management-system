package com.hardik.flenderson.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hardik.flenderson.dto.MasterCountryDto;
import com.hardik.flenderson.service.MasterCountryService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class MasterCountryController {
	
	private final MasterCountryService masterCountryService;
	
	@GetMapping("v1/countries")
	public List<MasterCountryDto> getAllCountries(){
		return masterCountryService.getAllCountries();
	}

}
