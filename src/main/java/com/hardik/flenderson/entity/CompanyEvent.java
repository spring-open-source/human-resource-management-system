package com.hardik.flenderson.entity;

import java.io.Serializable;
import java.time.LocalDate;
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
@Table(name = "company_events")
@Data
public class CompanyEvent implements Serializable{
	
	private static final long serialVersionUID = 5881182998107378011L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true, insertable = false, updatable = false)
	private UUID id;
	
	@Column(name = "company_id", nullable = false)
	private UUID companyId;
	
	@Hidden
	@Exclude
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false, insertable = false, updatable = false)
	private Company company;
	
	@Column(name = "heading", nullable = false)
	private String heading;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "image_url", nullable = false)	
	private String imageUrl;
	
	@Column(name = "due_date", nullable = false)
	private LocalDate dueDate;
	
	@Column(name = "created_at", nullable = false, insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
	private LocalDateTime updatedAt;

}
