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
@Table(name = "salary_records")
@Data
public class SalaryRecord implements Serializable {

	private static final long serialVersionUID = -2565703162946745833L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true, insertable = false, updatable = false)
	private Integer id;

	@Column(name = "compay_id", nullable = false)
	private UUID companyId;

	@Column(name = "employee_id", nullable = false)
	private UUID employeeId;

	@Column(name = "total_amount_payable", nullable = false)
	private Double totalAmountPayable;

	@Column(name = "penalty_for_month", nullable = false)
	private Double penaltyForMonth;

	@Column(name = "bonus_for_month", nullable = false)
	private Double bonusForMonth;

	@Column(name = "paid", nullable = false)
	private Boolean paid;

	@Column(name = "month", nullable = false)
	private Integer month;

	@Column(name = "year", nullable = false)
	private Integer year;

}
