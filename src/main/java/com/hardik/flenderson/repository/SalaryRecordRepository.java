package com.hardik.flenderson.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.flenderson.entity.SalaryRecord;

@Repository
public interface SalaryRecordRepository extends JpaRepository<SalaryRecord, Integer> {

	List<SalaryRecord> findByMonthAndYear(Integer month, Integer year);

}
