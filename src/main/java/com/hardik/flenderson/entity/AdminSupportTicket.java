package com.hardik.flenderson.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "admin_support_tickets")
@Data
public class AdminSupportTicket implements Serializable {

	private static final long serialVersionUID = 281456864071216741L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true, insertable = false, updatable = false)
	private UUID id;
	
	@Column(name = "is_active", nullable = false)
	private Boolean isActive;

	@Column(name = "raised_by_name", nullable = false)
	private String raisedByName;
	
	@Column(name = "raised_by_email", nullable = false)
	private String raisedByEmail;
	
	@Column(name = "raised_by_account_type", nullable = false)
	private String raisedByAccountType;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "ticket_issue", nullable = false)
	private String ticketIssue;
	
	@Column(name = "created_at", nullable = false, insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
	private LocalDateTime updatedAt;

}
