package com.hardik.flenderson.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Table(name = "employees")
@Data
public class Employee implements Serializable {

	private static final long serialVersionUID = 4290987865876201277L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true, insertable = false, updatable = false)
	private UUID id;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "middle_name", nullable = true)
	private String middleName;

	@Column(name = "last_name", nullable = true)
	private String lastName;

	@Column(name = "email_id", nullable = false, unique = true)
	private String emailId;

	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "status", nullable = true)
	private String status;

	@Column(name = "image_url", unique = true)
	private String imageUrl;

	@Column(name = "gender", nullable = false)
	private String gender;

	@Column(name = "date_of_birth", nullable = false)
	private LocalDate dateOfBirth;

	@Column(name = "company_id", nullable = false)
	private UUID companyId;

	@Hidden
	@Exclude
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false, insertable = false, updatable = false)
	private Company company;

	@Column(name = "country_id", nullable = false)
	private Integer countryId;

	@Hidden
	@Exclude
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id", nullable = false, insertable = false, updatable = false)
	private MasterCountry masterCountry;

	@Column(name = "company_status", nullable = true)
	private Integer companyStatus;

	@Column(name = "employee_daily_attendance_id", nullable = false)
	private UUID employeeDailyAttendanceId;

	@Hidden
	@Exclude
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "employee_daily_attendance_id", referencedColumnName = "id", insertable = false, updatable = false)
	private EmployeeDailyAttendance employeeDailyAttendance;

	@Column(name = "created_at", nullable = false, insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
	private LocalDateTime updatedAt;

	@Hidden
	@Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
	private Set<EmployeeRole> employeeRoles;

	@Hidden
	@Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
	private Set<MonthlySalaryDetail> monthlySalaryDetails;

	@Hidden
	@Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
	private Set<EmployeeSocial> employeeSocials;

	@Hidden
	@Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
	private Set<EmployeeIssue> employeeIssues;

}
