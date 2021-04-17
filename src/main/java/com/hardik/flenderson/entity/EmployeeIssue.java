package com.hardik.flenderson.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Table(name = "employee_issues")
@Data
public class EmployeeIssue implements Serializable {

	private static final long serialVersionUID = 1969506644903058956L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true, insertable = false, updatable = false)
	private UUID id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "employee_id", nullable = false)
	private UUID employeeId;

	@Hidden
	@Exclude
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id", nullable = false, insertable = false, updatable = false)
	private Employee employee;

	@Column(name = "company_id", nullable = false)
	private UUID companyId;

	@Hidden
	@Exclude
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false, insertable = false, updatable = false)
	private Company company;

	@Column(name = "is_active", nullable = false)
	private Boolean isActive;

	@Column(name = "issue_type", nullable = false)
	private String issueType;

	@Column(name = "response", nullable = true)
	private String response;

	@Column(name = "created_at", nullable = false, insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
	private LocalDateTime updatedAt;

}
