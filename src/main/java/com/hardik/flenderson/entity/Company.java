package com.hardik.flenderson.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Table(name = "companies")
@Data
public class Company implements Serializable {

	private static final long serialVersionUID = -4790337164519643048L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true, insertable = false, updatable = false)
	private UUID id;
	
	@Column(name = "company_code", nullable = false, unique = true)
	private String companyCode;
	
	@Column(name = "name", nullable = false, unique = true)
	private String name;
	
	@Column(name = "logo_url", nullable = true)
	private String logoUrl;	

	@Column(name = "created_at", nullable = false, insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
	private LocalDateTime updatedAt;

	@Hidden
	@Exclude
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	private Set<CompanyDocument> companyDocuments;
	
	@Hidden
	@Exclude
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	private Set<Employee> employees;
	
	@Hidden
	@Exclude
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	private Set<CompanyEvent> companyEvents;
	
	@Hidden
	@Exclude
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	private Set<EmployeeIssue> employeeIssues;
}
