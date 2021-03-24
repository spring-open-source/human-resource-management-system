package com.hardik.flenderson.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.flenderson.entity.EmployeeRole;

@Repository
public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, UUID>{

	List<EmployeeRole> findByEmployeeId(UUID employeeId);
}
