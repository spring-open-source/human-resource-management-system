package com.hardik.flenderson.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hardik.flenderson.dto.MasterCountryDto;
import com.hardik.flenderson.repository.MasterCountryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MasterCountryService {

	private final MasterCountryRepository masterCountryRepository;

	public List<MasterCountryDto> getAllCountries() {
		return masterCountryRepository.findAll().parallelStream()
				.map(masterCountry -> MasterCountryDto.builder().id(masterCountry.getId()).code(masterCountry.getCode())
						.name(masterCountry.getName())
						.flagUrl("https://www.countryflags.io/" + masterCountry.getCode() + "/flat/64.png").build())
				.collect(Collectors.toList());
	}

}
