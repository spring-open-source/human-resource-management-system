package com.hardik.flenderson.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.flenderson.entity.RejectedEmployeeCompanyMapping;

@Repository
public interface RejectedEmployeeCompanyMappingRepository
		extends JpaRepository<RejectedEmployeeCompanyMapping, Integer> {

	Optional<RejectedEmployeeCompanyMapping> findByEmployeeIdAndCompanyId(UUID employeeId, UUID companyId);
	
	List<RejectedEmployeeCompanyMapping> findByCompanyId(UUID companyId);

}
