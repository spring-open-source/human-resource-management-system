package com.hardik.flenderson.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.flenderson.entity.AttendanceRecord;

@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Integer>{
	
	List<AttendanceRecord> findByMonthAndYear(Integer month, Integer year);

}
