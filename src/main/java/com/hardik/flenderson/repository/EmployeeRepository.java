package com.hardik.flenderson.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.flenderson.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID>{
	
	Boolean existsByEmailIdIgnoreCase(String email);
	
	Optional<Employee> findByEmailIdIgnoreCase(String emailId);
	
	List<Employee> findByCompanyStatus(Integer companyStatus);

}
