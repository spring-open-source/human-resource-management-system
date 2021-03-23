package com.hardik.flenderson.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.flenderson.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {
	
	Optional<Company> findByNameAndCompanyCode(String name, String companyCode);

}
