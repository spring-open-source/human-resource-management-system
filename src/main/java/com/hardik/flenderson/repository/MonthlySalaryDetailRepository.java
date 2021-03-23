package com.hardik.flenderson.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.flenderson.entity.MonthlySalaryDetail;

@Repository
public interface MonthlySalaryDetailRepository extends JpaRepository<MonthlySalaryDetail, Integer> {

	Optional<MonthlySalaryDetail> findByEmployeeId(UUID employeeId);

}
