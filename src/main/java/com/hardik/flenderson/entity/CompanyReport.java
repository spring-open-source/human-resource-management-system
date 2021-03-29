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
@Table(name = "company_reports")
@Data
public class CompanyReport implements Serializable {

	private static final long serialVersionUID = 8728494676392276903L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true, insertable = false, updatable = false)
	private UUID id;
	
	@Column(name = "company_id", nullable = true)
	private UUID companyId;

	@Hidden
	@Exclude
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true, insertable = false, updatable = false)
	private Company company;
	
	@Column(name = "report_type", nullable = false)
	private Integer reportType;
	
	@Column(name = "key", nullable = false)
	private String s3Key;
	
	@Column(name = "month", nullable = false)
	private Integer month;
	
	@Column(name = "year", nullable = false)
	private Integer year;
	
	@Column(name = "created_at", nullable = false, insertable = false, updatable = false)
	private LocalDateTime createdAt;

}
