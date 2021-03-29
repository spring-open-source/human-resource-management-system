package com.hardik.flenderson.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "attendance_records")
@Data
public class AttendanceRecord implements Serializable{

	private static final long serialVersionUID = -6504573169788459945L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true, insertable = false, updatable = false)
	private Integer id;
	
	@Column(name = "company_id", nullable = false)
	private UUID companyId;
	
	@Column(name = "employee_id", nullable = false)
	private UUID employeeId;
	
	@Column(name = "was_marked", nullable = false)
	private Boolean marked;
	
	@Column(name = "attendance", nullable = false)
	private Boolean present;
	
	@Column(name = "day", nullable = false)
	private Integer day;
	
	@Column(name = "month", nullable = false)
	private Integer month;
	
	@Column(name = "year", nullable = false)
	private Integer year;	

}
