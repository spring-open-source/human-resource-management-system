package com.hardik.flenderson.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "employee_daily_attendances")
@Data
public class EmployeeDailyAttendance implements Serializable{

	private static final long serialVersionUID = 6502825560186715138L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true, insertable = false, updatable = false)
	private UUID id;
	
	@Column(name = "date", nullable = false)
	private LocalDate date;
	
	@Column(name = "is_marked", nullable = false)
	private Boolean isMarked;
	
	@Column(name = "is_present", nullable = false)
	private Boolean isPresent;	

}
