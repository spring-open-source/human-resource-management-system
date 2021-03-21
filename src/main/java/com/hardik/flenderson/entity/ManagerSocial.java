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
@Table(name = "manager_socials")
@Data
public class ManagerSocial implements Serializable{
	
	private static final long serialVersionUID = 1250497986848987752L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true, insertable = false, updatable = false)
	private UUID id;
	
	@Column(name = "manager_id", nullable = false)
	private UUID managerId;

	@Hidden
	@Exclude
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "manager_id", nullable = false, insertable = false, updatable = false)
	private Manager manager;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "url", nullable = false, unique = true)
	private String url;
	
	@Column(name = "created_at", nullable = false, insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
	private LocalDateTime updatedAt;

}
